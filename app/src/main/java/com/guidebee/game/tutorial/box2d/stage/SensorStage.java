package com.guidebee.game.tutorial.box2d.stage;


import com.guidebee.game.tutorial.box2d.actor.Helicopter;
import com.guidebee.game.tutorial.box2d.actor.Radar;
import com.guidebee.game.tutorial.box2d.actor.TankWithRadar;

public class SensorStage extends Box2DGameStage{

    private final TankWithRadar tank;
    private final Helicopter helicopter1;
    private final Helicopter helicopter2;
    private final Radar radar;

    public SensorStage(){

        radar=new Radar();
        addActor(radar);
        tank=new TankWithRadar(radar);
        addActor(tank);
        helicopter1=new Helicopter("Helicopter1",400,100,1);
        addActor(helicopter1);
        helicopter2=new Helicopter("Helicopter2",400,200,2);
        addActor(helicopter2);

        setSensorListener(tank);


    }
}