package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;

import static com.guidebee.game.GameEngine.assetManager;


public class BoxGround extends Actor {

    private final TextureRegion groundTextRegion;

    public BoxGround(int x, int y){
        super("Ground");
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",
                TextureAtlas.class);
        groundTextRegion =textureAtlas.findRegion("ground");
        setTextureRegion(groundTextRegion);
        if(x==-1){
            x=(Configuration.SCREEN_WIDTH- groundTextRegion.getRegionWidth())/2;
        }
        if(y==-1){
            y=0;
        }
        setPosition(x,y);
        initBody(BodyDef.BodyType.StaticBody);
    }

    public  BoxGround(){
        this(-1,-1);
    }

}