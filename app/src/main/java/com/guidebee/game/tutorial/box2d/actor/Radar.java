package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.math.Vector2;

import static com.guidebee.game.GameEngine.assetManager;

public class Radar extends Actor {

    private final TextureRegion radarTextRegion;
    private final TextureRegion redDotTextRegion;

    public final Vector2[] helicopterPostions=new Vector2[2];


    public Radar(){
        super("Radar");

        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",
                TextureAtlas.class);
        radarTextRegion =textureAtlas.findRegion("radar");
        setTextureRegion(radarTextRegion);
        redDotTextRegion =textureAtlas.findRegion("reddot");
        setPosition(Configuration.SCREEN_WIDTH-radarTextRegion.getRegionWidth()-10,
                Configuration.SCREEN_HEIGHT-radarTextRegion.getRegionHeight()-10);
        helicopterPostions[0]=new Vector2();
        helicopterPostions[1]=new Vector2();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        for(Vector2 pos: helicopterPostions){
            if(pos.x * pos.y!=0)
            batch.draw(redDotTextRegion,pos.x,pos.y);
        }

    }
}
