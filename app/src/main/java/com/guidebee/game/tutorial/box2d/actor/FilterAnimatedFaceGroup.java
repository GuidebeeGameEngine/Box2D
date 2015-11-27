package com.guidebee.game.tutorial.box2d.actor;

import com.guidebee.game.physics.Body;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.Filter;
import com.guidebee.game.physics.Fixture;
import com.guidebee.game.physics.PolygonShape;
import com.guidebee.game.physics.Shape;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.math.MathUtils;
import com.guidebee.math.geometry.Rectangle;
import com.guidebee.utils.TimeUtils;
import com.guidebee.utils.collections.Array;

public class FilterAnimatedFaceGroup extends AnimatedFaceGroup{

    private final short BOX_CATEGORY_MASK=1;
    private final short CIRCLE_CATEGORY_MASK=2;
    private final short TRIANGLE_CATEGORY_MASK=4;
    private final short HEXAGON_CATEGORY_MASK=8;

    private boolean useFilter=false;

    public void setUseFilter(boolean filter){
        useFilter=filter;
        clearChildren();
    }

    private void setFilter(Body body,short categoryBits, short maskBits){
        if(useFilter) {
            if (body != null) {
                Array<Fixture> fixtures = body.getFixtureList();
                Filter filter = new Filter();
                filter.categoryBits = categoryBits;
                filter.maskBits = maskBits;
                for (Fixture fixture : fixtures) {
                    fixture.setFilterData(filter);

                }
            }
        }
    }

    @Override
    protected void spawnFace(){
        int type=(MathUtils.random(4) + 1) % 4;

        AnimatedFace.Type faceType=AnimatedFace.Type.values()[type];
        if(faceType!=AnimatedFace.Type.Box) {
            AnimatedFace animatedFace = new AnimatedFace(faceType);
            animatedFace.setPosition(MathUtils.random(0,
                            Configuration.SCREEN_WIDTH - 64),
                    Configuration.SCREEN_HEIGHT);

            Rectangle dropRect = new Rectangle(0, 0, 32, 32);
            switch (faceType) {
                case Box:
                    animatedFace.initBody();
                    setFilter(animatedFace.getBody(), BOX_CATEGORY_MASK,
                            (short) (CIRCLE_CATEGORY_MASK
                                    | TRIANGLE_CATEGORY_MASK
                                    | HEXAGON_CATEGORY_MASK));
                    break;
                case Circle:
                    animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                            Shape.Type.Circle, dropRect, 1.0f, 0.5f, 0.1f);
                    setFilter(animatedFace.getBody(), CIRCLE_CATEGORY_MASK,
                            (short) (BOX_CATEGORY_MASK
                                    | TRIANGLE_CATEGORY_MASK
                                    | HEXAGON_CATEGORY_MASK));
                    break;
                case Triangle:
                    PolygonShape triangleShape = new PolygonShape();

                    float[] triangleVertices = getTriangleVertices();
                    triangleShape.set(triangleVertices);
                    animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                            triangleShape, dropRect, 1.0f, 0.5f, 0.1f);
                    setFilter(animatedFace.getBody(), TRIANGLE_CATEGORY_MASK,
                            (short) (BOX_CATEGORY_MASK
                                    | CIRCLE_CATEGORY_MASK
                                    | HEXAGON_CATEGORY_MASK));
                    break;
                case Hexagon:
                    PolygonShape shape = new PolygonShape();

                    float[] vertices = getHexagonVertices();
                    shape.set(vertices);
                    animatedFace.initBody(BodyDef.BodyType.DynamicBody,
                            shape, dropRect, 1.0f, 0.5f, 0.1f);
                    setFilter(animatedFace.getBody(), HEXAGON_CATEGORY_MASK,
                            (short) (BOX_CATEGORY_MASK
                                    | CIRCLE_CATEGORY_MASK
                                    | TRIANGLE_CATEGORY_MASK));

                    break;
            }


            addActor(animatedFace);
        }
        lastDropTime= TimeUtils.nanoTime();

    }
}
