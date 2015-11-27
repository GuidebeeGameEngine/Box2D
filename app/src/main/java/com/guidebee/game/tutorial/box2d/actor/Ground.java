package com.guidebee.game.tutorial.box2d.actor;


import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.Sprite;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.ChainShape;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;

import static com.guidebee.game.GameEngine.*;

public class Ground extends Actor{

    private int posX;
    private int posY;
    private int groundWidth;
    private final TextureRegion groundTextRegion;

    public Ground(){
        this(0,0,Configuration.SCREEN_WIDTH);

    }


    private float []getVertices(){
        float [] vertices=new float[8];
        int height=groundTextRegion.getRegionHeight();
        vertices[0]=groundWidth;
        vertices[1]=0;

        vertices[2]=groundWidth;
        vertices[3]=height;

        vertices[4]=0;
        vertices[5]=height;

        vertices[6]=0;
        vertices[7]=0;
        return GameEngine.toBox2DVertices(vertices);

    }

    public Ground(int x ,int y, int width){
        super("Ground");
        posX=x;
        posY=y;
        groundWidth =width;


        TextureAtlas textureAtlas = assetManager.get("box2d.atlas",
                TextureAtlas.class);
        groundTextRegion = textureAtlas.findRegion("ground");
        int backWidth = groundTextRegion.getRegionWidth();
        int size = width/ backWidth;
        if (size * backWidth < groundWidth) size++;
        groundWidth =size*backWidth;

        //Sprite emptySprite=new Sprite();
        //emptySprite.setSize(groundWidth,groundTextRegion.getRegionHeight());
        //setSprite(emptySprite);
        setSize(groundWidth,
                groundTextRegion.getRegionHeight());
        setPosition(x, y);

        ChainShape chainShape=new ChainShape();
        chainShape.createChain(getVertices());
        initChainBody(BodyDef.BodyType.StaticBody, chainShape,1f,0.5f,0.3f);
        chainShape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int backWidth = groundTextRegion.getRegionWidth();
        int size = groundWidth / backWidth;
        if (size * backWidth < groundWidth) size++;
        for (int i = 0; i < size; i++) {
            batch.draw(groundTextRegion, posX+ i * backWidth, posY);
        }


    }
}
