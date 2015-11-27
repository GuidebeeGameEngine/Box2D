package com.guidebee.game.tutorial.box2d.stage;

import android.util.Log;

import com.guidebee.game.Collidable;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.scene.collision.Collision;
import com.guidebee.game.scene.collision.CollisionListener;
import com.guidebee.game.tutorial.box2d.actor.AnimatedFaceGroup;
import com.guidebee.game.tutorial.box2d.actor.BoxGround;
import com.guidebee.game.tutorial.box2d.actor.Bullet;
import com.guidebee.game.tutorial.box2d.actor.Face;
import com.guidebee.game.tutorial.box2d.actor.Ground;
import com.guidebee.game.tutorial.box2d.actor.Player;
import com.guidebee.game.ui.GameController;
import com.guidebee.game.ui.drawable.TextureRegionDrawable;

import static com.guidebee.game.GameEngine.assetManager;


public class BulletStage  extends Box2DGameStage implements CollisionListener {


    private final BoxGround ground;

    private final Face face;
    private final AnimatedFaceGroup animatedFaceGroup;
    private final Player player;




    public BulletStage(){
        super();
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",TextureAtlas.class);
        GameController gameController
                = new GameController(new TextureRegionDrawable(textureAtlas.findRegion("Back")),
                new TextureRegionDrawable(textureAtlas.findRegion("Joystick")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Normal_Shoot")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Pressed_Shoot")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Normal_Virgin")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Pressed_Virgin"))
        );
        setGameController(gameController);

        ground=new BoxGround();
        addActor(ground);

        face=new Face();
        addActor(face);

        animatedFaceGroup=new AnimatedFaceGroup();
        addActor(animatedFaceGroup);

        player=new Player();

        addActor(player);
        player.addBulletGroup(this);
        gameController.addGameControllerListener(player);

        setCollisionListener(this, Collidable.BOX2D_CONTACT);
    }

    @Override
    public void collisionDetected(Collision collision) {
        Collidable objectA=collision.getObjectA();
        Collidable objectB=collision.getObjectB();

        //collision event can happen multiple times,

        if(objectA.getName()=="Bullet"){
            ((Bullet)objectA).setTouchedOther();
        }
        if(objectB.getName()=="Bullet"){
            ((Bullet)objectB).setTouchedOther();
        }

        if(!(objectA.getName()=="Ground" && objectB.getName()=="Ground")) {
            Log.d("Collide", collision.getObjectA().getName() + ":" +
                    collision.getObjectB().getName());
        }

    }
}