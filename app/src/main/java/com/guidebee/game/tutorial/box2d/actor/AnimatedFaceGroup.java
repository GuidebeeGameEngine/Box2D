package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.GameEngine;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.PolygonShape;
import com.guidebee.game.physics.Shape;
import com.guidebee.game.scene.Group;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.math.MathUtils;
import com.guidebee.math.geometry.Rectangle;
import com.guidebee.utils.TimeUtils;


public class AnimatedFaceGroup extends Group {
    protected long lastDropTime =0;

    protected void spawnFace(){
        int type=(MathUtils.random(4) + 1) % 4;

        AnimatedFace.Type faceType=AnimatedFace.Type.values()[type];
        AnimatedFace animatedFace=new AnimatedFace(faceType);
        animatedFace.setPosition(MathUtils.random(0,
                        Configuration.SCREEN_WIDTH - 64),
                Configuration.SCREEN_HEIGHT);

        Rectangle dropRect=new Rectangle(0,0,32,32);
        switch(faceType){
            case Box:
                animatedFace.initBody();
                break;
            case Circle:
                animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                        Shape.Type.Circle,dropRect,1.0f,0.5f,0.1f);

                break;
            case Triangle:
                PolygonShape triangleShape=new PolygonShape();

                float [] triangleVertices=getTriangleVertices();
                triangleShape.set(triangleVertices);
                animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                        triangleShape,dropRect,1.0f,0.5f,0.1f);
                break;
            case Hexagon:
                PolygonShape shape=new PolygonShape();

                float [] vertices=getHexagonVertices();
                shape.set(vertices);
                animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                        shape,dropRect,1.0f,0.5f,0.1f);

                break;
        }


        addActor(animatedFace);
        lastDropTime= TimeUtils.nanoTime();
    }

    protected float [] getHexagonVertices(){
        float[] vertices=new float[12];
        vertices[0]=0;
        vertices[1]=-16;
        vertices[2]=14;
        vertices[3]=-8;
        vertices[4]=14;
        vertices[5]=8;

        vertices[6]=0;
        vertices[7]=16;
        vertices[8]=-14;
        vertices[9]=8;
        vertices[10]=-14;
        vertices[11]=-8;

        return GameEngine.toBox2DVertices(vertices);

    }

    protected float [] getTriangleVertices(){
        float[] vertices=new float[6];
        vertices[0]=-16;
        vertices[1]=-16;
        vertices[2]=16;
        vertices[3]=-16;
        vertices[4]=0;
        vertices[5]=16;

        return GameEngine.toBox2DVertices(vertices);

    }

    @Override
    public void act(float delta) {
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnFace();
        }

        AnimatedFace [] animatedFaces=getChildren().toArray(AnimatedFace.class);
        for(AnimatedFace rainDrop: animatedFaces){
            float y = rainDrop.getY();
            if(y+64 <0){
                removeActor(rainDrop);
            }

        }
    }

}
