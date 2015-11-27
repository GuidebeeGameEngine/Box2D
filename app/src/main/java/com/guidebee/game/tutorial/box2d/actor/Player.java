package com.guidebee.game.tutorial.box2d.actor;

import android.util.Log;

import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Animation;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.physics.Body;
import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.Filter;
import com.guidebee.game.physics.Fixture;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.scene.Group;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.tutorial.box2d.Configuration;
import com.guidebee.game.ui.GameControllerListener;
import com.guidebee.game.ui.GameControllerListener.Direction;
import com.guidebee.game.ui.Touchpad;
import com.guidebee.math.Vector2;
import com.guidebee.math.geometry.Rectangle;
import com.guidebee.utils.collections.Array;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;


public class Player extends Actor implements GameControllerListener {



    private final Animation forwardAnimation;
    private final Animation backwardAnimation;
    private final Animation leftAnimation;
    private final Animation rightAnimation;
    private final TextureRegion playerTextureRegion;


    private final int SPRITE_HEIGHT=32;
    private final int SPRITE_WIDTH=24;
    private final int SPRITE_FRAME_SIZE=3;
    private float elapsedTime = 0;
    private float tick=0.05f;
    private float speed =5f;
    private Direction currentDirection= Direction.NONE;

    private boolean isSelfControl=false;
    private float oldX;
    private float oldY;
    private float scale=1.0f;
    private Direction actorFaceDirection = Direction.NONE;

    private Array<Bullet> bullets=new Array<>();
    private final Group bulletGroup=new Group();
    private final float bulletForce=100f;


    private void setFilter(Body body){

        if (body != null) {
            Array<Fixture> fixtures = body.getFixtureList();
            Filter filter = new Filter();
            filter.groupIndex=-1;
            for (Fixture fixture : fixtures) {
                fixture.setFilterData(filter);

            }
        }

    }

    public void addBulletGroup(Stage stage){
        stage.addActor(bulletGroup);
    }

    public void removeBullet(Actor actor){
        bulletGroup.removeActor(actor);
    }

    public Player(){
        super("Player");

        TextureAtlas textureAtlas=assetManager.get("box2d.atlas",TextureAtlas.class);
        playerTextureRegion =textureAtlas.findRegion("player");
        Array<TextureRegion> keyFramesForward=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesRight=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesBackward=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesLeft=new Array<TextureRegion>();
        int i=0;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(playerTextureRegion,
                    j*SPRITE_WIDTH,
                    i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesForward.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(playerTextureRegion,
                    j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesRight.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(playerTextureRegion,
                    j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesBackward.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(playerTextureRegion
                    ,j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesLeft.add(textureRegion);

        }

        forwardAnimation=new Animation(tick,keyFramesForward);
        backwardAnimation=new Animation(tick,keyFramesBackward);
        rightAnimation=new Animation(tick,keyFramesRight);
        leftAnimation=new Animation(tick,keyFramesLeft);
        setTextureRegion(forwardAnimation.getKeyFrame(0));
        setPosition(Configuration.SCREEN_WIDTH / 2 - 64 / 2, 300);
        Rectangle boundRect=new Rectangle(2,0,SPRITE_WIDTH-4,SPRITE_HEIGHT-6);

        scaleBy(scale);
        initBody(BodyDef.BodyType.DynamicBody, boundRect);

        getBody().setSleepingAllowed(false);

        setSelfControl(isSelfControl);
        getBody().setFixedRotation(true);
        scale=getScaleX();
        setFilter(getBody());


    }

    @Override
    public void KnobMoved(Touchpad touchpad, Direction direction) {
        currentDirection=direction;
        actorFaceDirection=direction;
        handleKeyPress();
    }

    @Override
    public void ButtonPressed(GameButton button) {
       if (button == GameButton.BUTTON_B) {
            if (isSelfControl) {
                setY(getY() + 400 * graphics.getDeltaTime());
            } else {
                Vector2 originalSpeed=getBody().getLinearVelocity();
                originalSpeed.y+=speed * 2;
                getBody().setLinearVelocity( originalSpeed);
            }

        }
        if (button == GameButton.BUTTON_A) { //shoot
            Bullet bullet=null;
            float bulletX=getCenterX();
            float bulletY=getY()+getHeight();
            float xForce=0;
            float yForce=bulletForce;

            switch(actorFaceDirection){

                case SOUTHEAST:
                    bulletX=getX()+getWidth();
                    bulletY=getY();
                    xForce=bulletForce/2;
                    yForce=-bulletForce/2;
                    break;
                case SOUTHWEST:
                    bulletX=getX();
                    bulletY=getY();
                    xForce=-bulletForce/2;
                    yForce=-bulletForce/2;
                    break;
                case NORTHEAST:
                    bulletX=getX()+getWidth();
                    bulletY=getY()+getHeight();
                    xForce=bulletForce/2;
                    yForce=bulletForce/2;
                    break;

                case NORTHWEST:
                    bulletX=getX();
                    bulletY=getY()+getHeight();
                    xForce=-bulletForce/2;
                    yForce=bulletForce/2;
                    break;
                case SOUTH:
                    bulletX=getCenterX();
                    bulletY=getY();
                    xForce=0;
                    yForce=-bulletForce;


                    break;
                case EAST:

                    bulletX=getX()+getWidth();
                    bulletY=getY()+getHeight()/4;
                    xForce=bulletForce;
                    yForce=0;


                    break;
                case WEST:

                    bulletX=getX();
                    bulletY=getY()+getHeight()/4;
                    xForce=-bulletForce;
                    yForce=0;


                    break;
                case NORTH:
                default:
                    bulletX=getCenterX();
                    bulletY=getY()+getHeight();
                    xForce=0;
                    yForce=bulletForce;

                    break;
            }


            bullet=new Bullet(bulletX,bulletY);
            bullet.getBody().setBullet(true);
            bullet.getBody().applyLinearImpulse(
                    new Vector2(xForce, yForce),
                    bullet.getBody().getWorldCenter(),true);
            setFilter(bullet.getBody());
            bulletGroup.addActor(bullet);
        }
        Log.d("ButtonPressed",button.toString());
    }

    private void handleKeyPress(){


        oldX = getX();
            oldY=getY();

            switch (currentDirection) {
                case NORTHWEST:
                    setTextureRegion(forwardAnimation.getKeyFrame(elapsedTime, true));
                    if(isSelfControl){
                        setY(getY() + 100 * graphics.getDeltaTime());
                        setX(getX() - 100 * graphics.getDeltaTime());
                    }else {

                        getBody().setLinearVelocity(-speed, speed);
                    }

                    break;
                case NORTHEAST:
                    setTextureRegion(forwardAnimation.getKeyFrame(elapsedTime, true));
                    if(isSelfControl) {
                        setY(getY() + 100 * graphics.getDeltaTime());
                        setX(getX() + 100 * graphics.getDeltaTime());
                    }else {
                        getBody().setLinearVelocity(speed, speed);
                    }

                    break;

                case SOUTHWEST:
                    setTextureRegion(backwardAnimation.getKeyFrame(elapsedTime, true));
                    if(isSelfControl) {
                        setY(getY() - 100 * graphics.getDeltaTime());
                        setX(getX() - 100 * graphics.getDeltaTime());
                    }else {
                        getBody().setLinearVelocity(-speed, -speed);
                    }

                    break;
                case SOUTHEAST:
                    setTextureRegion(backwardAnimation.getKeyFrame(elapsedTime, true));
                    if(isSelfControl) {
                        setY(getY() - 100 * graphics.getDeltaTime());
                        setX(getX() + 100 * graphics.getDeltaTime());
                    }else {
                        getBody().setLinearVelocity(speed, -speed);
                    }

                    break;

                case WEST:
                    setTextureRegion(leftAnimation.getKeyFrame(elapsedTime, true));
                    if(isSelfControl) {
                        setX(getX() - 200 * graphics.getDeltaTime());
                    }
                    else {
                        getBody().setLinearVelocity(-speed, 0);
                    }
                    break;
                case EAST:

                    setTextureRegion(rightAnimation.getKeyFrame(elapsedTime, true));
                    if(isSelfControl) {
                        setX(getX() + 200 * graphics.getDeltaTime());
                    }else {
                        getBody().setLinearVelocity(speed, 0);
                    }
                    break;
                case NORTH:

                    setTextureRegion(forwardAnimation.getKeyFrame(elapsedTime, true));
                    if(isSelfControl) {
                        setY(getY() + 200 * graphics.getDeltaTime());
                    }else {
                        getBody().setLinearVelocity(0, speed);
                    }

                    break;
                case SOUTH:
                    setTextureRegion(backwardAnimation.getKeyFrame(elapsedTime, true));
                    if(isSelfControl) {
                        setY(getY() - 200 * graphics.getDeltaTime());
                    }else {
                        getBody().setLinearVelocity(0, -speed);
                    }
                    break;

            }

            currentDirection=Direction.NONE;
            if (getX() < 0) {
                setX(0);
                if(!isSelfControl) {
                    getBody().setLinearVelocity(0,0);
                    resetBodyWithSprite();
                }
                currentDirection=Direction.NONE;
            }
            if (getY() < 0) {
                setY(0);
                if(!isSelfControl) {
                    getBody().setLinearVelocity(0,0);
                    resetBodyWithSprite();
                }
                currentDirection=Direction.NONE;
            }
            if (getX() > Configuration.SCREEN_WIDTH - SPRITE_WIDTH*scale) {
                setX(Configuration.SCREEN_WIDTH - SPRITE_WIDTH*scale);
                if(!isSelfControl) {
                    getBody().setLinearVelocity(0,0);
                    resetBodyWithSprite();
                }
                currentDirection=Direction.NONE;
            }
            if (getY() > Configuration.SCREEN_HEIGHT - SPRITE_HEIGHT*scale) {
                setY(Configuration.SCREEN_HEIGHT - SPRITE_HEIGHT*scale);
                if(!isSelfControl) {
                    getBody().setLinearVelocity(0,0);
                    resetBodyWithSprite();
                }
                currentDirection=Direction.NONE;
            }


    }


    public void stopMoving(){
        currentDirection=Direction.NONE;
        //setX(oldX);
        //setY(oldY);
    }
    @Override
    public void act(float delta){
        super.act(delta);
        elapsedTime += GameEngine.graphics.getDeltaTime();
        //Log.d("state:", currentStage.toString());
        handleKeyPress();
        for(Bullet bullet: bullets){
            if(bullet.getY()>Configuration.SCREEN_HEIGHT ||
                    bullet.getY() < 40 ||
                    bullet.getX()<0 || bullet.getX()>Configuration.SCREEN_WIDTH){
                bulletGroup.addActor(bullet);
            }
        }


    }

}
