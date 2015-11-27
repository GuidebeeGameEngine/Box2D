package com.guidebee.game.tutorial.box2d.stage;

import android.util.Log;

import com.guidebee.game.Collidable;
import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.scene.collision.Collision;
import com.guidebee.game.scene.collision.CollisionListener;
import com.guidebee.game.tutorial.box2d.actor.Button;
import com.guidebee.game.tutorial.box2d.actor.Face;
import com.guidebee.game.tutorial.box2d.actor.FilterAnimatedFaceGroup;
import com.guidebee.game.tutorial.box2d.actor.Ground;
import com.guidebee.game.tutorial.box2d.actor.Player;
import com.guidebee.game.ui.GameController;
import com.guidebee.game.ui.drawable.TextureRegionDrawable;
import com.guidebee.math.Vector2;

import static com.guidebee.game.GameEngine.assetManager;


public class CollisionStage extends Box2DGameStage implements CollisionListener {

    private class ClickButton extends Button {


        @Override
        protected void clickEventHandler() {
            useFilter=!useFilter;
            filterAnimatedFaceGroup.setUseFilter(useFilter);

        }
    }

    private final Ground ground;

    private final Face face;

    private final Player player;

    private final FilterAnimatedFaceGroup filterAnimatedFaceGroup;

    private final ClickButton button;

    private boolean useFilter=false;

    public CollisionStage(){

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

        face=new Face();
        addActor(face);


        player=new Player();
        addActor(player);

        button = new ClickButton();
        addActor(button);

        filterAnimatedFaceGroup=new FilterAnimatedFaceGroup();
        addActor(filterAnimatedFaceGroup);
        gameController.addGameControllerListener(player);

        setCollisionListener(this, Collidable.BOX2D_CONTACT);
    }

    @Override
    public void collisionDetected(Collision collision) {
        Collidable objectA=collision.getObjectA();
        Collidable objectB=collision.getObjectB();
            Log.d("Collide", objectA.getName() + ":" +
                    objectB.getName());
    }



}
