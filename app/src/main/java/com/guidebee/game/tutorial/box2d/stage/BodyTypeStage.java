package com.guidebee.game.tutorial.box2d.stage;


import android.util.Log;

import com.guidebee.game.Collidable;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.scene.collision.Collision;
import com.guidebee.game.scene.collision.CollisionListener;
import com.guidebee.game.tutorial.box2d.actor.AnimatedFaceGroup;
import com.guidebee.game.tutorial.box2d.actor.Face;
import com.guidebee.game.tutorial.box2d.actor.Ground;
import com.guidebee.game.tutorial.box2d.actor.Platform;
import com.guidebee.game.tutorial.box2d.actor.Player;
import com.guidebee.game.ui.GameController;
import com.guidebee.game.ui.drawable.TextureRegionDrawable;

import static com.guidebee.game.GameEngine.assetManager;

public class BodyTypeStage extends Box2DGameStage implements CollisionListener {


    private final Ground ground;

    private final Face face;

    private final Player player;

    private final Platform platform;

    private final Platform secondPlatform;



    public BodyTypeStage(){
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

        ground=new Ground();
        addActor(ground);


        platform =new Platform();
        addActor(platform);

        secondPlatform=new Platform(150,350,2);
        addActor(secondPlatform);

        face=new Face();
        addActor(face);


        player=new Player();
        addActor(player);



        gameController.addGameControllerListener(player);

        setCollisionListener(this, Collidable.BOX2D_CONTACT);
    }

    @Override
    public void collisionDetected(Collision collision) {
        Collidable objectA=collision.getObjectA();
        Collidable objectB=collision.getObjectB();

        if(!(objectA.getName()=="Ground" && objectB.getName()=="Ground")) {
            Log.d("Collide", collision.getObjectA().getName() + ":" +
                    collision.getObjectB().getName());
        }
    }
}
