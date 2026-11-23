package com.ProgPro;

import com.bladecoder.ink.runtime.Choice;
import com.bladecoder.ink.runtime.Story;

import java.util.List;

public class Dialog {
    private Story story;

    public Dialog(Story story) {
        this.story = story;
    }

    public boolean canContinue() {
        return story.canContinue();
    }

    public void reset() throws Exception {
        story.resetState();
    }

    public String getNextText() throws Exception {
        if (story.canContinue()) {
            return story.Continue();
        }
        return "";
    }

    public List<Choice> getChoices() throws Exception {
        return story.getCurrentChoices();
    }

    /**
     * Choisit l'option à l'index donné, puis draine TOUT le texte disponible
     * jusqu'au prochain point de décision (ou la fin du dialogue).
     *
     * BUG CORRIGÉ : l'ancien code appelait Continue() une seule fois, laissant
     * la story dans un état canContinue()=true sans choix courants. Les anciens
     * boutons restaient à l'écran et un second clic déclenchait
     * chooseChoiceIndex() sans aucun choix disponible → "choice out of range null".
     */
    public String chooseOption(int index) throws Exception {
        story.chooseChoiceIndex(index);
        StringBuilder sb = new StringBuilder();
        while (story.canContinue()) {
            sb.append(story.Continue());
        }
        return sb.toString();
    }

    public boolean isFinished() throws Exception {
        return !story.canContinue() && getChoices().isEmpty();
    }
}
