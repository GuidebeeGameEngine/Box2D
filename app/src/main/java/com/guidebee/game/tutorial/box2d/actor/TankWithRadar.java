package com.guidebee.game.tutorial.box2d.actor;


import android.util.Log;

import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.CircleShape;
import com.guidebee.game.physics.Contact;
import com.guidebee.game.physics.Fixture;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.scene.collision.SensorListener;
import com.guidebee.math.Vector2;
import com.guidebee.math.geometry.Rectangle;

import static com.guidebee.game.GameEngine.toBox2D;

public class TankWithRadar extends  Tank
        implements SensorListener{

    private final Radar radar;

    private Actor[] actors=new Actor[2];

    public TankWithRadar(Radar radar){
        this.radar=radar;
        setX(200);
        CircleShape radarArea=new CircleShape();
        radarArea.setRadius(toBox2D(250));
        radarArea.setPosition(new Vector2(0,toBox2D(-60)));
        Rectangle dropRect=new Rectangle(0,0,
                tankTextRegion.getRegionWidth(),
                tankTextRegion.getRegionHeight());
        addBodyShape(BodyDef.BodyType.KinematicBody,
                radarArea, dropRect, 1.0f, 0.5f, 0.1f, true);

    }

        @Override
        public void beginContact(Contact contact) {
            handleContact(true, contact);

        }

        @Override
        public void endContact(Contact contact) {
            handleContact(false, contact);

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

        private void handleContact(boolean enterOrLeave,
                                   Contact contact){
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();

            Actor actorA = (Actor)fixtureA.getBody().getUserData();
            Actor actorB = (Actor)fixtureB.getBody().getUserData();

            if(fixtureA.isSensor()) {//Tank's radar
                //ActorB is Helicopter
                if(actorB.getName()=="Helicopter1"){
                    setHelicopterPos(actorB, 0, enterOrLeave);


                }else if(actorB.getName()=="Helicopter2"){
                    setHelicopterPos(actorB, 1, enterOrLeave);

                }
            }

            if(fixtureB.isSensor()){//Tank's radar
                //ActorA is Helicopter
                if(actorA.getName()=="Helicopter1"){
                    setHelicopterPos(actorA, 0, enterOrLeave);

                }else if(actorA.getName()=="Helicopter2"){
                    setHelicopterPos(actorA, 1, enterOrLeave);

                }
            }



            if(enterOrLeave){
                Log.d("Enter", actorA.getName()
                        +", "+actorB.getName());
            }else{
                Log.d("Leave", actorA.getName()
                        +", "+actorB.getName());
            }
        }


    @Override
    public void act(float delta) {
        super.act(delta);
        //tracking helicopters
        for(int i=0;i<2;i++){
            if(actors[i]!=null){
                setHelicopterPos(actors[i],i,true);
            }
        }

    }



}
