package com.guidebee.game.tutorial.box2d.stage;

import com.guidebee.game.tutorial.box2d.actor.AnimatedFaceGroup;
import com.guidebee.game.tutorial.box2d.actor.Face;
import com.guidebee.game.tutorial.box2d.actor.Ground;


public class BasicBox2DStage extends Box2DGameStage{

    private final Ground ground;
    private final Face face;
    private final AnimatedFaceGroup animatedFaceGroup;

    public BasicBox2DStage(){
        super();
        ground=new Ground();
        addActor(ground);
        face=new Face();
        addActor(face);
        animatedFaceGroup=new AnimatedFaceGroup();
        addActor(animatedFaceGroup);
    }
}
