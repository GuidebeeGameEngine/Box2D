package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Animation;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.scene.Actor;
import com.guidebee.utils.collections.Array;

import static com.guidebee.game.GameEngine.assetManager;


public class Helicopter extends Actor {



    private final TextureRegion helicopterTextureRegion;
    private final Animation forwardAnimation;
    private float tick=0.05f;




    private final int SPRITE_WIDTH=48;
    private final int SPRITE_HEIGHT=42;
    private float elapsedTime = 0;
    private final int posX;
    private final int posY;
    private int direction =1;
    private final int limit = 300;
    private int step =1;
    private int currentPos=0;


    public Helicopter(String name,int x,int y,int step){
        super(name);
        posX=x;
        posY=y;
        this.step=step;
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas", TextureAtlas.class);
        helicopterTextureRegion =textureAtlas.findRegion("helicopter_tiled");
        Array<TextureRegion> keyFramesForward=new Array<TextureRegion>();

        for(int i=0;i<2;i++) {
            TextureRegion textureRegion = new TextureRegion(helicopterTextureRegion,
                    i*SPRITE_WIDTH,
                    0,
                    SPRITE_WIDTH, SPRITE_HEIGHT);
            keyFramesForward.add(textureRegion);
        }
        {
            TextureRegion textureRegion = new TextureRegion(helicopterTextureRegion,
                    0,
                    SPRITE_HEIGHT,
                    SPRITE_WIDTH, SPRITE_HEIGHT);
            keyFramesForward.add(textureRegion);
        }

        forwardAnimation=new Animation(tick,keyFramesForward);
        setTextureRegion(forwardAnimation.getKeyFrame(0));
        setPosition(posX,posY);
        setSelfControl(true);
        initBody();

    }

    @Override
    public void act(float delta){
        super.act(delta);

        elapsedTime += GameEngine.graphics.getDeltaTime();
        setTextureRegion(forwardAnimation.getKeyFrame(elapsedTime, true));

        currentPos += direction * step;
        if (currentPos > 0 && currentPos >= limit) {
            currentPos = limit;
            direction = -1;

        }
        if (currentPos < 0 && currentPos < -limit) {
            currentPos = -limit;
            direction = 1;
        }
        setX(posX + currentPos);




    }
}
