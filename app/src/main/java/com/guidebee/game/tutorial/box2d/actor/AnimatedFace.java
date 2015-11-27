package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Animation;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.utils.collections.Array;

import static com.guidebee.game.GameEngine.assetManager;


public class AnimatedFace extends Actor{

    /**
     * Animated face types
     */
    public enum Type{
        Box,
        Circle,
        Triangle,
        Hexagon

    }

    private float elapsedTime = 0;
    private final TextureRegion textureRegion;
    private final Animation animation;
    private final int SPRITE_FRAME_SIZE=2;
    private float tick=0.2f;

    private final Type faceType;

    public AnimatedFace(Type type){
        faceType=type;
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",TextureAtlas.class);
        switch(faceType){

            case Circle:
                textureRegion=textureAtlas.findRegion("face_circle_tiled");
                setName("Face_Circle");
                break;
            case Hexagon:
                textureRegion=textureAtlas.findRegion("face_hexagon_tiled");
                setName("Face_Hexagon");
                break;
            case Triangle:
                textureRegion=textureAtlas.findRegion("face_triangle_tiled");
                setName("Face_Triangle");
                break;
            case Box:
            default:
                textureRegion=textureAtlas.findRegion("face_box_tiled");
                setName("Face_Box");
                break;
        }

        Array<TextureRegion> keyFrames = new Array<>();
        int spriteHeight = textureRegion.getRegionHeight();
        int spriteWidth = textureRegion.getRegionWidth() / SPRITE_FRAME_SIZE;
        for (int i = 0; i < SPRITE_FRAME_SIZE; i++) {
            TextureRegion region = new TextureRegion(textureRegion,
                    i * spriteWidth,
                    0,
                    spriteWidth, spriteHeight);
            keyFrames.add(region);
        }
        animation = new Animation(tick, keyFrames);
        setTextureRegion(animation.getKeyFrame(0));
        setPosition(Configuration.SCREEN_WIDTH / 2,
                Configuration.SCREEN_HEIGHT / 2);


    }

    @Override
    public void act(float delta){
        super.act(delta);
        elapsedTime += GameEngine.graphics.getDeltaTime();
        setTextureRegion(animation.getKeyFrame(elapsedTime, true));

    }



}
