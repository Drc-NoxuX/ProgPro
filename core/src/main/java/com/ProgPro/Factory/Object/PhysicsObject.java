package com.ProgPro.Factory.Object;
import com.badlogic.gdx.physics.box2d.*;

public abstract class PhysicsObject extends BasicObject {

    private Body body;
    public static final float PPM = 32f; //Pixel Per Meter -> 1 mètre Box2D = 32 pixels à l'écran
    //Box2D travaille en mètre, pas en pixels.

    public PhysicsObject(int x, int y, String name) {
        super(x, y, name);
    }

    public void createBody(World world, float width, float height) {
        createBody(world, width, height, BodyDef.BodyType.DynamicBody);//Soumis à la gravité
    }

    public void createBody(World world, float width, float height, BodyDef.BodyType type) {
        BodyDef bd = new BodyDef(); //Recette du corps qu'on veut créer
        bd.type = type;
        bd.position.set(super.getX()/PPM, super.getY()/PPM); //Position de départ en mètres. On divise par PPM pour convertir les pixels en mètres
        bd.fixedRotation = true; //Empêche le corps de tourner quand il heurte quelque chose.
        this.body = world.createBody(bd); //on donne la "recette" au world de Box2D et le world créé le corps
        body.setUserData(this); //J'associe ma classe au body
        PolygonShape shape = new PolygonShape(); //Forme de la Hitbox
        shape.setAsBox(width/2f/PPM, height/2f/PPM);//Forme de rectangle -> setAsBox prend la demi-largeur et la demi-hauteur donc on divise width et height par deux
        FixtureDef fd = new FixtureDef(); //Donne les propriétés physique à la forme
        fd.shape = shape;
        fd.density = 1.0f; //densité -> influence la masse de l'objet (masse = densité x surface) -> plus la densité est élevée plus l'objet est lourd
        fd.friction = 0.1f;//frottement avec les autres surfaces (0 = glace, 1 = colle)
        body.createFixture(fd); //attache la forme au corps -> le corps a une hitbox physique
        shape.dispose();//LibGDX/Box2D gère la mémoire manuellement -> on libère la mémoire (plus besoin de la forme, elle est dans la hitbox)
    }

    @Override
    public int getX(){
        if(body != null){
            return (int)(body.getPosition().x * PPM);
        }else {
            return super.getX();
        }
    }

    public int getY(){
        if(body != null){
            return (int)(body.getPosition().y * PPM);
        }else{
            return super.getY();
        }
    }

    public Body getBody(){
        return body;
    }


}
