package com.guidebee.game.tutorial.box2d.stage;

import android.util.Log;

import com.guidebee.game.Collidable;
import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.PolygonShape;
import com.guidebee.game.physics.Shape;
import com.guidebee.game.scene.collision.Collision;
import com.guidebee.game.scene.collision.CollisionListener;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.game.tutorial.box2d.actor.AnimatedFace;
import com.guidebee.game.tutorial.box2d.actor.Face;
import com.guidebee.game.tutorial.box2d.actor.Ground;
import com.guidebee.game.tutorial.box2d.actor.Platform;
import com.guidebee.game.tutorial.box2d.actor.Player;
import com.guidebee.game.tutorial.box2d.actor.Tank;
import com.guidebee.game.ui.GameController;
import com.guidebee.game.ui.drawable.TextureRegionDrawable;
import com.guidebee.math.MathUtils;
import com.guidebee.math.geometry.Rectangle;
import com.guidebee.utils.TimeUtils;

import static com.guidebee.game.GameEngine.assetManager;


public class ShapeTypeStage extends Box2DGameStage implements CollisionListener {


    private final Ground ground;

    private final Face face;

    private final Player player;

    private final Tank tank;


    private final AnimatedFace boxFace;

    private final AnimatedFace circleFace;

    private final AnimatedFace hexagonFace;

    private final AnimatedFace triangleFace;



    public ShapeTypeStage(){
        super();
        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",TextureAtlas.class);
        GameController gameController
                = new GameController(new TextureRegionDrawable(textureAtlas.findRegion("Back")),
                new TextureRegionDrawable(textureAtlas.findRegion("Joystick")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Normal_Shoot")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Pressed_Shoot")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Normal_Virgin")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Pressed_Virgin"))
        );
        setGameController(gameController);

        ground=new Ground();
        addActor(ground);


        face=new Face();
        addActor(face);


        player=new Player();
        addActor(player);


        boxFace=createFace(AnimatedFace.Type.Box);
        addActor(boxFace);

        circleFace=createFace(AnimatedFace.Type.Circle);
        addActor(circleFace);

        hexagonFace=createFace(AnimatedFace.Type.Hexagon);
        addActor(hexagonFace);

        triangleFace=createFace(AnimatedFace.Type.Triangle);
        addActor(triangleFace);

        tank=new Tank();
        addActor(tank);

        gameController.addGameControllerListener(player);

        setCollisionListener(this, Collidable.BOX2D_CONTACT);
    }

    @Override
    public void collisionDetected(Collision collision) {
        Collidable objectA=collision.getObjectA();
        Collidable objectB=collision.getObjectB();

        if(!(objectA.getName()=="Ground" && objectB.getName()=="Ground")) {
            Log.d("Collide", collision.getObjectA().getName() + ":" +
                    collision.getObjectB().getName());
        }
    }


    private float [] getHexagonVertices(){
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

    private float [] getTriangleVertices(){
        float[] vertices=new float[6];
        vertices[0]=-16;
        vertices[1]=-16;
        vertices[2]=16;
        vertices[3]=-16;
        vertices[4]=0;
        vertices[5]=16;

        return GameEngine.toBox2DVertices(vertices);

    }


    private AnimatedFace createFace(AnimatedFace.Type type){

        AnimatedFace.Type faceType=type;
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

        return animatedFace;

    }
}

