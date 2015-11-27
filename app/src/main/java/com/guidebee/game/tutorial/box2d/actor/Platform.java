package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;

import static com.guidebee.game.GameEngine.assetManager;


public class Platform extends Actor {

    private final TextureRegion platformTextRegion;

    private int direction =1;
    private final int limit = 100;
    private int step =1;
    private int currentPos=0;

    private final int posX;
    private final int posY;


    public Platform(int x,int y,int step){
        super("Platform");
        posX=x;
        posY=y;
        this.step=step;
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",
                TextureAtlas.class);
        platformTextRegion =textureAtlas.findRegion("Platform");
        setTextureRegion(platformTextRegion);
        setPosition(posX,posY);

        initBody(BodyDef.BodyType.KinematicBody);
    }

    public  Platform(){
        this(Configuration.SCREEN_WIDTH- 300,
                Configuration.SCREEN_HEIGHT - 250,1);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentPos+=direction*step;
        if(currentPos>0 && currentPos>=limit) {
            currentPos=limit;
            direction = -1 ;

        }
        if(currentPos<0 && currentPos<-limit){
            currentPos=-limit;
            direction = 1 ;
        }
        setX(posX+currentPos);


    }
}
