package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.PolygonShape;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.math.geometry.Rectangle;

import static com.guidebee.game.GameEngine.assetManager;


public class Tank extends Actor {

    protected final TextureRegion tankTextRegion;

    public  Tank(){
        super("Tank");
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",
                TextureAtlas.class);
        tankTextRegion =textureAtlas.findRegion("tank");
        setTextureRegion(tankTextRegion);

        setPosition((Configuration.SCREEN_WIDTH
                        - tankTextRegion.getRegionWidth()) / 2,
                (Configuration.SCREEN_HEIGHT
                        - tankTextRegion.getRegionHeight()) / 2 + 100);
        initTankBody();

    }



    protected void initTankBody(){
        PolygonShape []polygonShapes=new PolygonShape[tankPolygons.length];
        for(int i=0;i<polygonShapes.length;i++){
            polygonShapes[i]=new PolygonShape();
            polygonShapes[i].set(getTankVertices(i));
        }

        Rectangle dropRect=new Rectangle(0,0,
                tankTextRegion.getRegionWidth(),
                tankTextRegion.getRegionHeight());

        initBody(BodyDef.BodyType.KinematicBody,
                polygonShapes, dropRect, 1.0f, 0.5f, 0.1f);
    }

    protected final String [] tankPolygons=new String[] {
            "15, 42  , 33, 5  , 33, 48  , 31, 50  , 20, 52",
            "-30, 50  , -32, 48  , -32, 5  , -13, 41  , -19, 52",
            "8, -56  , -6, 36  , -3, -56  , 2, -59",
            "-22, -64  , -28, -14  , -32, -16  , -35, -25  , -36, -60",
            "29, -64  , 37, -60  , 37, -25  , 33, -16  , 29, -14  , 18, -58  , 23, -63",
            "4, 36  , 29, -2  , 33, 5  , 15, 42",
            "-32, 5  , -29, -2  , -6, 36  , -13, 41",
            "-28, -14  , -22, -64  , -17, -59  , -6, 36  , -29, -2",
            "-2, 67  , -3, 36  , 4, 36  , 2, 68",
            "4, 36  , -6, 36  , 14, -57  , 18, -58  , 29, -14  , 29, -2",
            "-3, -56  , -6, 36  , -17, -59",
            "14, -57  , -6, 36  , 8, -56",
    };


    protected float [] getTankVertices(int index){
        float[] vertices=GameEngine.getVerticesFromString(tankPolygons[index]);

        for(int i=0;i<vertices.length/2;i++){
            vertices[i*2+1]=-vertices[i*2+1];
        }
        return GameEngine.toBox2DVertices(vertices);

    }



}