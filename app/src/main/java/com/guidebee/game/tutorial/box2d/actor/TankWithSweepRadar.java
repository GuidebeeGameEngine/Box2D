package com.guidebee.game.tutorial.box2d.actor;


import com.guidebee.game.physics.Fixture;
import com.guidebee.game.physics.RayCastCallback;
import com.guidebee.game.scene.Actor;
import com.guidebee.math.Vector2;
import com.guidebee.utils.TimeUtils;

import static com.guidebee.game.GameEngine.toBox2D;
import static com.guidebee.game.GameEngine.world;

public class TankWithSweepRadar extends  Tank
        implements RayCastCallback{

    private final Radar radar;

    private Actor[] actors=new Actor[2];

    private final float rotateStep =2f;//degree
    private final float rayLength = 400f;
    private final Vector2 contactPoint=new Vector2();

    private final Vector2 sweepVector;

    private final Vector2 v1=new Vector2();
    private final Vector2 v2 =new Vector2();

    private long lastDropTime =0;

    public TankWithSweepRadar(Radar radar){
        this.radar=radar;
        setX(200);
        sweepVector=new Vector2(0,rayLength);

    }

    public Vector2 getV1(){
        return v1;
    }

    public Vector2 getV2(){
        return v2;
    }



    private void setHelicopterPos(Actor helicopter,int index,
                                  boolean enterOrLeave){
        float x=helicopter.getX();
        float y=helicopter.getY();
        if(enterOrLeave) {
            radar.helicopterPostions[index].x =
                    (x - getCenterX()) / 4 + radar.getCenterX();
            radar.helicopterPostions[index].y =
                    (y - getCenterY()) / 4 + radar.getCenterY();

        }else{
            radar.helicopterPostions[index].x
                    =radar.helicopterPostions[index].y=0;
        }
        if(enterOrLeave){
            //start tracking this helicopter
            actors[index]=helicopter;
        }else{
            //stop tracking this helicopter
            actors[index]=null;
        }

    }


    private void clearHelicopterPosition(){
        lastDropTime= TimeUtils.nanoTime();
        for(int i=0;i<2;i++){
            if(actors[i]!=null){
                setHelicopterPos(actors[i],i,false);
            }
        }
        contactPoint.x=contactPoint.y=0;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(TimeUtils.nanoTime()-lastDropTime>1000000000) {
            clearHelicopterPosition();
        }

        sweepVector.rotate(rotateStep);
        v1.x=toBox2D(getCenterX());
        v1.y=toBox2D(getCenterY());
        Vector2 v3=getSweepVector();
        v2.x= toBox2D(v3.x + getCenterX());
        v2.y= toBox2D(v3.y + getCenterY());
        world.rayCast(this,v1,v2);

    }

    public Vector2 getSweepVector(){
        return sweepVector;
    }


    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point,
                                  Vector2 normal, float fraction) {
        Actor actor=(Actor)fixture.getBody().getUserData();
        if(actor.getName()=="Tank") return -1.0f;

        if(actor!=null) {
            if(actor.getName()=="Helicopter1"){
                actors[0]=actor;
                setHelicopterPos(actors[0],0,true);
            }else if(actor.getName()=="Helicopter2"){
                actors[1]=actor;
                setHelicopterPos(actors[1],1,true);
            }
            contactPoint.x = point.x;
            contactPoint.y = point.y;
        }
        //return fraction;
        return 0f;
    }
}
