package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.Shape;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.math.Vector2;
import com.guidebee.math.geometry.Rectangle;

import static com.guidebee.game.GameEngine.assetManager;


public class Bullet extends Actor {

    private static final TextureRegion bulletTextRegion;



    private volatile  boolean touchedOther=false;

    static{
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",
                TextureAtlas.class);
        bulletTextRegion =textureAtlas.findRegion("reddot");

    }

    public void setTouchedOther(){
        touchedOther=true;
    }

    public  Bullet(float x,float y){
        super("Bullet");
        setTextureRegion(bulletTextRegion);
        setPosition(x, y);
        Rectangle dropRect=new Rectangle(0,0,
                bulletTextRegion.getRegionWidth(),
                bulletTextRegion.getRegionHeight());
        initBody(BodyDef.BodyType.DynamicBody,
                Shape.Type.Circle,dropRect,1.0f,0.5f,0.1f);


    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(getX()<0 || getX()>Configuration.SCREEN_WIDTH ||
                getY() > Configuration.SCREEN_HEIGHT  || touchedOther){
            remove();
        }

    }





}