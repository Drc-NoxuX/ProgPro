package com.ProgPro.UI;

import com.ProgPro.Dialog;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.GameEvent.GameEventObserver;
import com.ProgPro.Managers.DialogManager;
import com.ProgPro.Managers.RessourceManager;
import com.ProgPro.State.AliveState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bladecoder.ink.runtime.Choice;

import java.util.List;

public class DialogUI implements GameEventObserver {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Label label;
    private Player player;
    private Dialog currentDialog;

    public DialogUI() throws Exception {
        player = RessourceManager.getInstance().getPlayer();
        GameEventBus.getInstance().subscribe(this);
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        label = new Label("", skin);
        table = new Table();
        table.setFillParent(true);
        table.add(label).colspan(2);
        stage.addActor(table);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    // -----------------------------------------------------------------------
    // Construction de l'UI
    // -----------------------------------------------------------------------

    /**
     * Affiche le texte courant + les boutons de choix.
     * La liste est passée en paramètre (déjà lue par l'appelant) pour éviter
     * un second appel à story.getCurrentChoices() qui pouvait retourner []
     * dans certains états de bladecoder ink 1.2.0.
     */
    private void showChoices(List<Choice> choices) {
        table.clear();
        table.add(label).colspan(2).row();

        for (int i = 0; i < choices.size(); i++) {
            final int idx = i;
            final boolean[] consumed = {false};

            String btnText = choices.get(i).getText();
            if (btnText == null) btnText = "...";
            TextButton btn = new TextButton(btnText, skin);

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (consumed[0]) return;
                    consumed[0] = true;
                    handleChoice(idx);
                }
            });
            table.add(btn).colspan(2).pad(4f).row();
        }
    }

    /**
     * Affiche le texte final + un bouton "Continuer" pour quitter le dialogue.
     *
     * CORRECTION CLÉ : on n'appelle plus setIsInDialog(false) directement dans
     * handleChoice(). Si on le faisait (via postRunnable ou non), la fenêtre de
     * dialogue disparaissait avant que stage.draw() puisse rendre le texte final,
     * et le joueur ne voyait rien. Le bouton "Continuer" laisse le temps à
     * stage.draw() d'afficher le texte, puis ferme proprement le dialogue
     * quand le joueur clique dessus.
     */
    private void showEndButton() {
        table.clear();
        table.add(label).colspan(2).row();

        TextButton endBtn = new TextButton("Continuer", skin);
        endBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                endDialogue();
            }
        });
        table.add(endBtn).colspan(2).pad(4f).row();
    }

    /** Affiche uniquement le texte, sans aucun bouton. */
    private void showTextOnly() {
        table.clear();
        table.add(label).colspan(2);
    }

    /** Ferme proprement le dialogue et restaure l'état du joueur. */
    private void endDialogue() {
        showTextOnly();
        try {
            GameEventBus.getInstance().publish(GameEvent.DIALOG_FINISHED, "");
            player.setState(new AliveState(player));
            player.setIsInDialog(false);
        } catch (Exception e) {
            Gdx.app.error("DialogUI", "Erreur lors de la fermeture du dialogue", e);
        }
    }

    // -----------------------------------------------------------------------
    // Logique de choix
    // -----------------------------------------------------------------------

    /**
     * Appelé quand le joueur clique sur un bouton de choix.
     *
     * Pas de Gdx.app.postRunnable() ici : les modifications de la table pendant
     * stage.act() sont safe en scene2d LibGDX. C'est le postRunnable de la version
     * précédente qui causait le bug — il exécutait setIsInDialog(false) avant
     * que render() ait eu le temps de dessiner le texte final, rendant la réponse
     * invisible.
     *
     * Trois cas après un choix :
     *  1. Dialogue terminé (plus de canContinue ni de choix)
     *     → showEndButton() : texte final + bouton "Continuer"
     *  2. Nouveau point de décision
     *     → showChoices(nextChoices)
     *  3. Garde de sécurité (ne devrait pas arriver avec chooseOption corrigé)
     *     → showTextOnly()
     */
    private void handleChoice(int idx) {
        try {
            // chooseOption draine tout le texte disponible (correction dans Dialog.java).
            String nextText = currentDialog.chooseOption(idx);
            label.setText(nextText);

            // Lire l'état suivant une seule fois pendant que la story est stable.
            List<Choice> nextChoices = currentDialog.getChoices();
            boolean finished = !currentDialog.canContinue() && nextChoices.isEmpty();

            if (finished) {
                showEndButton();
            } else if (!nextChoices.isEmpty()) {
                showChoices(nextChoices);
            } else {
                showTextOnly();
            }

        } catch (Exception e) {
            Gdx.app.error("DialogUI", "Erreur handleChoice idx=" + idx, e);
            label.setText("[Erreur dialogue - voir logs]");
            showEndButton();
        }
    }

    // -----------------------------------------------------------------------
    // GameEventObserver
    // -----------------------------------------------------------------------

    @Override
    public void displayEvent(GameEvent event, String message) throws Exception {
        if (event == GameEvent.DIALOG_STARTED) {
            currentDialog = DialogManager.getInstance().getDialog();

            // Drainer tout le texte introductif jusqu'au premier point de choix.
            StringBuilder allText = new StringBuilder();
            while (currentDialog.canContinue()) {
                allText.append(currentDialog.getNextText());
            }
            label.setText(allText.toString().trim());

            // Lire les choix une seule fois.
            List<Choice> choices = currentDialog.getChoices();
            if (!choices.isEmpty()) {
                showChoices(choices);
            } else {
                // Dialogue sans choix (texte linéaire pur) : bouton pour fermer.
                showEndButton();
            }
        }
    }

    public InputProcessor getStage() {
        return stage;
    }
}
