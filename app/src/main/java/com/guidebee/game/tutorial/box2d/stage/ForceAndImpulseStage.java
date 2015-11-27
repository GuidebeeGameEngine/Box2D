package com.guidebee.game.tutorial.box2d.stage;

import android.util.Log;

import com.guidebee.game.Collidable;
import com.guidebee.game.GameEngine;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.PolygonShape;
import com.guidebee.game.physics.Shape;
import com.guidebee.game.scene.collision.Collision;
import com.guidebee.game.scene.collision.CollisionListener;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.game.tutorial.box2d.actor.AnimatedFace;
import com.guidebee.game.tutorial.box2d.actor.Button;
import com.guidebee.game.tutorial.box2d.actor.Face;
import com.guidebee.game.tutorial.box2d.actor.Ground;
import com.guidebee.game.tutorial.box2d.actor.Platform;
import com.guidebee.game.tutorial.box2d.actor.Player;
import com.guidebee.math.MathUtils;
import com.guidebee.math.Vector2;
import com.guidebee.math.geometry.Rectangle;


public class ForceAndImpulseStage extends Box2DGameStage implements CollisionListener {


    private class ClickButton extends Button {


        @Override
        protected void clickEventHandler() {
            face1.getBody().applyForce(new Vector2(0, 50),
                    face1.getBody().getWorldCenter(), true);
            face2.getBody().applyLinearImpulse(new Vector2(0, 50),
                    face2.getBody().getWorldCenter(), true);

            face3.getBody().setTransform(new Vector2(
                    GameEngine.toBox2D(150f),
                    GameEngine.toBox2D(350f)), 0);
            face3.getBody().setAwake(true);

            face4.getBody().applyTorque(20,true);


        }
    }

    private final Ground ground;

    private final AnimatedFace face1;

    private final AnimatedFace face2;

    private final AnimatedFace face3;

    private final AnimatedFace face4;


    private final ClickButton button;

    public ForceAndImpulseStage() {
        super();

        ground = new Ground();
        addActor(ground);

        face1 = createFace(AnimatedFace.Type.Box);
        face1.setX(20);
        face1.resetBodyWithSprite();
        addActor(face1);

        face2 = createFace(AnimatedFace.Type.Triangle);
        face2.setX(Configuration.SCREEN_WIDTH / 3);
        face2.resetBodyWithSprite();
        addActor(face2);

        face3 = createFace(AnimatedFace.Type.Circle);
        face3.setX(Configuration.SCREEN_WIDTH / 3*2);
        face3.resetBodyWithSprite();
        addActor(face3);

        face4 = createFace(AnimatedFace.Type.Hexagon);
        face4.setX(Configuration.SCREEN_WIDTH -50);
        face4.resetBodyWithSprite();
        addActor(face4);


        button = new ClickButton();
        addActor(button);

        setCollisionListener(this, Collidable.BOX2D_CONTACT);
    }

    @Override
    public void collisionDetected(Collision collision) {
        Collidable objectA = collision.getObjectA();
        Collidable objectB = collision.getObjectB();

        if (!(objectA.getName() == "Ground" && objectB.getName() == "Ground")) {
            Log.d("Collide", collision.getObjectA().getName() + ":" +
                    collision.getObjectB().getName());
        }
    }


    private float[] getHexagonVertices() {
        float[] vertices = new float[12];
        vertices[0] = 0;
        vertices[1] = -16;
        vertices[2] = 14;
        vertices[3] = -8;
        vertices[4] = 14;
        vertices[5] = 8;

        vertices[6] = 0;
        vertices[7] = 16;
        vertices[8] = -14;
        vertices[9] = 8;
        vertices[10] = -14;
        vertices[11] = -8;

        return GameEngine.toBox2DVertices(vertices);

    }

    private float[] getTriangleVertices() {
        float[] vertices = new float[6];
        vertices[0] = -16;
        vertices[1] = -16;
        vertices[2] = 16;
        vertices[3] = -16;
        vertices[4] = 0;
        vertices[5] = 16;

        return GameEngine.toBox2DVertices(vertices);

    }


    private AnimatedFace createFace(AnimatedFace.Type type) {

        AnimatedFace.Type faceType = type;
        AnimatedFace animatedFace = new AnimatedFace(faceType);
        animatedFace.setPosition(MathUtils.random(0,
                        Configuration.SCREEN_WIDTH - 64),
                Configuration.SCREEN_HEIGHT/2);

        Rectangle dropRect = new Rectangle(0, 0, 32, 32);
        switch (faceType) {
            case Box:
                animatedFace.initBody();
                break;
            case Circle:
                animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                        Shape.Type.Circle, dropRect, 1.0f, 0.5f, 0.1f);

                break;
            case Triangle:
                PolygonShape triangleShape = new PolygonShape();

                float[] triangleVertices = getTriangleVertices();
                triangleShape.set(triangleVertices);
                animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                        triangleShape, dropRect, 1.0f, 0.5f, 0.1f);
                break;
            case Hexagon:
                PolygonShape shape = new PolygonShape();

                float[] vertices = getHexagonVertices();
                shape.set(vertices);
                animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                        shape, dropRect, 1.0f, 0.5f, 0.1f);

                break;
        }

        return animatedFace;

    }
}
