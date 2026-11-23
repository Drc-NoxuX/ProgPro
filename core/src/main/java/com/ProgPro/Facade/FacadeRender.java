package com.ProgPro.Facade;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.Enemies.FarCombatEnemy;
import com.ProgPro.Factory.Object.PNJ.MerchandPNJ;
import com.ProgPro.Factory.Object.PNJ.PNJ;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Room.Room;
import com.ProgPro.Factory.Object.Usable.Gold;
import com.ProgPro.Factory.Object.Usable.Item;
import com.ProgPro.Factory.Object.Usable.Pickable;
import com.ProgPro.Factory.Object.Usable.Potion;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.GameEvent.GameEventObserver;
import com.ProgPro.Inventory;
import com.ProgPro.Managers.QuestManager;
import com.ProgPro.Managers.RessourceManager;
import com.ProgPro.State.AliveState;
import com.ProgPro.State.DialogState;
import com.ProgPro.UI.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

import static com.ProgPro.Factory.Object.PhysicsObject.PPM;

public class FacadeRender implements GameEventObserver {

    private ShapeRenderer shapeRenderer;
    private CombatUI combatUI;
    private GameOverUI gameOverUI;
    private DialogUI dialogUI;
    private InventoryUI inventoryUI;
    private ShopUI shopUI;
    private QuestUI questUI;
    private PauseMenuUI pauseMenuUI;
    private HealthUI healthUI;
    private EnemyHealthUI enemyHealthUI;
    private boolean isGameOver = false;
    private boolean isDialog = false;
    private boolean isInventoryOpen = false;
    private boolean isPause = false;
    private TiledMap currentTM;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private List<Body> bodyList = new ArrayList<Body>();
    private Skin skin;
    private Label notifLabel;
    private Stage notifStage;
    private float notifTimer = 0f;
    private static final float NOTIF_DURATION = 2f;
    private PNJ nearbyPNJ;
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private FacadeCombat facadeCombat;

    public FacadeRender(FacadeCombat facadeCombat, Player player, PauseMenuUI pauseMenuUI) throws Exception {
        this.pauseMenuUI = pauseMenuUI;
        shapeRenderer = new ShapeRenderer();
        combatUI = new CombatUI(player);
        gameOverUI = new GameOverUI();
        dialogUI = new DialogUI();
        inventoryUI = new InventoryUI(player);
        questUI = new QuestUI();
        healthUI = new HealthUI(shapeRenderer,  player);
        enemyHealthUI = new EnemyHealthUI();
        facadeCombat.setCombatUI(combatUI);
        this.facadeCombat = facadeCombat;
        loadMap(RessourceManager.getInstance().getRoom().getPath());
        createCollisions(currentTM);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        font = skin.getFont("default-font");
        spriteBatch =  new SpriteBatch();
        notifLabel = new Label("", skin);
        notifStage = new Stage();
        notifStage.addActor(notifLabel);
        notifLabel.setPosition(20, 100);
        GameEventBus.getInstance().subscribe(this);
    }

    public boolean getInventoryOpen() {
        return isInventoryOpen;
    }
    public boolean isPause() {
        return isPause;
    }

    public void setNearbyPNJ (PNJ nearbyPNJ) {
        this.nearbyPNJ = nearbyPNJ;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
        if(this.isGameOver) {
            Gdx.input.setInputProcessor(gameOverUI.getStage());
        }else {
            Gdx.input.setInputProcessor(combatUI.getStage());
        }
    }

    public void setDialog(boolean isDialog) {
        this.isDialog = isDialog;
        if(this.isDialog) {
            Gdx.input.setInputProcessor(dialogUI.getStage());
        }else{
            Gdx.input.setInputProcessor(combatUI.getStage());
        }
    }

    public void setInventoryOpen(boolean isInventoryOpen) {
        this.isInventoryOpen = isInventoryOpen;
        if(this.isInventoryOpen) {
            Gdx.input.setInputProcessor(inventoryUI.getStage());
        }else {
            Gdx.input.setInputProcessor(combatUI.getStage());
        }
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
        if(this.isPause) {
            Gdx.input.setInputProcessor(pauseMenuUI.getStage());
        }else{
            Gdx.input.setInputProcessor(combatUI.getStage());
        }
    }

    @Override
    public void displayEvent(GameEvent event, String message) {
        if(event == GameEvent.ITEM_PICKED) {
            notifLabel.setText(message);
            notifTimer = NOTIF_DURATION;
        }else if (event == GameEvent.COMBAT_STARTED){
            enemyHealthUI.setEnemy(facadeCombat.getEnemy());
        }
    }

    public void render(Player player, Room currentRoom) throws Exception {
        renderMap();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            renderPlayer(player);
            renderEntities(currentRoom, player);
        shapeRenderer.end();
        renderNearbyPNJPrompt();
        renderUI(player);

    }

    private void renderMap() throws Exception {
        renderer.setView(camera);
        renderer.render();
    }

    private void loadMap(String path) throws Exception {
        if(renderer != null){
            renderer.dispose();
        }
        if(currentTM != null){
            currentTM.dispose();
        }
        currentTM = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(currentTM, 0.5f);
        for(Body body : bodyList){
            RessourceManager.getInstance().getWorld().destroyBody(body);
        }
        bodyList.clear();
    }

    public void changeRoom(Room room) throws Exception {
        loadMap(room.getPath());
        createCollisions(currentTM);
    }

    private void renderEntities(Room room, Player player) throws Exception {
        for(Enemy enemy : room.getEnemys()){
            if(enemy.isAlive()){
                if(!player.isInCombat() || enemy.isInCombat()){
                    if (enemy instanceof FarCombatEnemy){
                        shapeRenderer.setColor(Color.RED);
                    }else{
                        shapeRenderer.setColor(Color.GREEN);
                    }
                    shapeRenderer.rect(enemy.getX(), enemy.getY(), 32, 48);
                }
            }
        }

        for(PNJ pnj : room.getPNJs()){
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(pnj.getX(), pnj.getY(), 32, 48);
        }

        for(Pickable item : room.getItems()){
            if(item instanceof Gold){
                shapeRenderer.setColor(Color.YELLOW);
            }else if(item instanceof Potion){
                shapeRenderer.setColor(Color.PURPLE);
            }
            BasicObject object = (BasicObject) item;
            shapeRenderer.rect(object.getX(), object.getY(), 32, 48);
        }
    }

    private void renderPlayer(Player player){
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(player.getX(), player.getY(), 32, 48);
    }

    private void renderUI(Player player) throws Exception {
        if(player.isInCombat()){
            combatUI.render();
            enemyHealthUI.render();
        }
        if(isGameOver){
            gameOverUI.render();
        }
        if(player.isInDialog()){
            dialogUI.render();
        }
        if(isInventoryOpen){
            inventoryUI.update(player);
            inventoryUI.render();
        }
        if(!player.isInCombat() && notifTimer > 0) {
            notifTimer -= Gdx.graphics.getDeltaTime();
            notifStage.act();
            notifStage.draw();
        }
        if(player.isInShop()  && shopUI != null) {
            shopUI.update(player);
            shopUI.render();
        }
        if(!player.isInCombat()) {
            questUI.update(QuestManager.getInstance().getQuests());
            questUI.render();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameEventBus.getInstance().publish(GameEvent.PAUSE_STARTED, "");
        }
        healthUI.render();
    }

    private void createCollisions(TiledMap map) throws Exception {
        MapLayer layer = map.getLayers().get("collision");
        MapObjects objects = layer.getObjects();
        for(MapObject object : objects){
            RectangleMapObject rectangleMapObject = ((RectangleMapObject)object);
            Rectangle rect =  rectangleMapObject.getRectangle();

            float scale = 0.5f;
            float x = (rect.x * scale) / PPM;
            float y = (rect.y * scale) / PPM;
            float w = ((rect.width / 2) * scale) / PPM;
            float h = ((rect.height / 2) * scale) / PPM;

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(x + w, y + h);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(w, h);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            Body body = RessourceManager.getInstance().getWorld().createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData("ground");
            bodyList.add(body);
            shape.dispose();
        }
    }

    public void dispose(){
        shapeRenderer.dispose();
        spriteBatch.dispose();
    }

    public void openShop(MerchandPNJ marchand, Player player) {
        shopUI = new ShopUI(player, marchand);
        Gdx.input.setInputProcessor(shopUI.getStage());
    }

    public void closeShop() {
        shopUI = null;
        Gdx.input.setInputProcessor(combatUI.getStage());
    }


    public void renderNearbyPNJPrompt(){
        if(nearbyPNJ != null){
            spriteBatch.begin();
            GlyphLayout layout = new GlyphLayout(font, "[E] Parler");
            float textWidth = layout.width;
            font.draw(spriteBatch, "[E] Parler", nearbyPNJ.getX() + 16 - textWidth/2, nearbyPNJ.getY() + 60);
            spriteBatch.end();
        }
    }
}
