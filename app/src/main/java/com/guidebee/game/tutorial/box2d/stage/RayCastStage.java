package com.guidebee.game.tutorial.box2d.stage;

import com.guidebee.game.camera.viewports.StretchViewport;
import com.guidebee.game.graphics.Color;
import com.guidebee.game.physics.Box2DDebugRenderer;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.game.tutorial.box2d.actor.Helicopter;
import com.guidebee.game.tutorial.box2d.actor.Radar;
import com.guidebee.game.tutorial.box2d.actor.TankWithSweepRadar;
import com.guidebee.math.Matrix4;

import static com.guidebee.game.GameEngine.pixelToBox2DUnit;


public class RayCastStage extends Stage {

    private class RayCastDebugRenderer extends Box2DDebugRenderer{


        public RayCastDebugRenderer(){
            renderer.setAutoShapeType(true);
        }

        @Override
        public void drawOverlays(){
            Color color=renderer.getColor();
            renderer.setColor(Color.GREEN);
            renderer.line(tank.getV1(), tank.getV2());
            renderer.setColor(color);
        }
    }

    protected final Matrix4 debugMatrix;
    protected final RayCastDebugRenderer debugRenderer;

    private final TankWithSweepRadar tank;
    private final Helicopter helicopter1;
    private final Helicopter helicopter2;

    private final Radar radar;

    public RayCastStage(){
        super(new StretchViewport(Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT));
        initWorld();

        debugMatrix = new Matrix4(getCamera().combined);
        debugMatrix.scale(pixelToBox2DUnit,
                pixelToBox2DUnit, 0);
        debugRenderer = new RayCastDebugRenderer();
        radar=new Radar();
        addActor(radar);
        tank=new TankWithSweepRadar(radar);
        addActor(tank);
        helicopter1=new Helicopter("Helicopter1",400,100,1);
        addActor(helicopter1);
        helicopter2=new Helicopter("Helicopter2",400,200,2);
        addActor(helicopter2);


    }

    @Override
    public void draw() {
        super.draw();
        debugRenderer.render(world, debugMatrix);
    }
}
