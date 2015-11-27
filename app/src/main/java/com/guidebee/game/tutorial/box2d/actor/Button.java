package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.math.Vector3;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.input;

public abstract class Button extends Actor {

    protected abstract void clickEventHandler();

    public Button(){
        super("Button");
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",
                TextureAtlas.class);
        TextureRegion buttonTextRegion=textureAtlas.findRegion("buttonrefresh");
        setTextureRegion(buttonTextRegion);
        setPosition(Configuration.SCREEN_WIDTH
                        - buttonTextRegion.getRegionWidth()-20,
                Configuration.SCREEN_HEIGHT
                        -buttonTextRegion.getRegionHeight()-20);
    }

    @Override
    public void act(float delta) {
        if (input.isTouched()) {
            //handel touch event
            Vector3 touchPos = new Vector3();
            touchPos.set(input.getX(), input.getY(), 0);
            getStage().getCamera().unproject(touchPos);
            if (getBoundingAABB().contains(touchPos.x, touchPos.y)) {
                clickEventHandler();
            }
        }
    }

}
