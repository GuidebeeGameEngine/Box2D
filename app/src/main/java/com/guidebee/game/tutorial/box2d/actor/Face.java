package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;

import static com.guidebee.game.GameEngine.assetManager;

public class Face extends Actor {

    private final TextureRegion faceTextRegion;

    public Face(int x, int y){
        super("Face");
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",
                TextureAtlas.class);
        faceTextRegion=textureAtlas.findRegion("face_box");
        setTextureRegion(faceTextRegion);
        setPosition(x,y);
        initBody();
    }

    public  Face(){
        this((Configuration.SCREEN_WIDTH
                        -32)/2,
                (Configuration.SCREEN_HEIGHT
                        -32)/2);
    }

}
