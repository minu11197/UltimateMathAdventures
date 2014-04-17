package com.me.ultimatemathadventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	
	  
	final UltimateMathAdventures game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
    Texture character; 
    //Texture testimage;
    World world;
    GameChar darkNinja;
    Box2DDebugRenderer debugRenderer; 
    Rectangle floor;
    StaticBodyGenerator floors;
    
    private SpriteBatch spriteBatch;

    
	public GameScreen(final UltimateMathAdventures gam) {
		this.game  = gam;
		
		world = new World(new Vector2(0F, -1000000F), false);
		debugRenderer = new Box2DDebugRenderer();
		Sprite texture = new Sprite(new Texture(Gdx.files.internal("DarkNinja.png"))); 
		Sprite testimage = new Sprite(new Texture(Gdx.files.internal("TestImage.png")));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		floor = new Rectangle();

		int i = 0;
		do {
		floors = new StaticBodyGenerator(testimage, world, i,0 , camera, 50, 50, floor);
		i = i + 150;
		} while (i < 750);


		
		
		Rectangle darkNinjaDimensions = new Rectangle();    
		darkNinjaDimensions.x = 250;
		darkNinjaDimensions.y = 250;    
		darkNinjaDimensions.width = 75;    
		darkNinjaDimensions.height = 98 + 98/2; 
		
		CircleShape poly = new CircleShape();
		poly.setRadius(4f);
		FixtureDef fixture = new FixtureDef();
		fixture.shape = poly;
		fixture.density = 0;
		fixture.friction = 0f;
		fixture.restitution = 0;
		
		BodyDef darkNinjaBodyDef = new BodyDef();
		darkNinjaBodyDef.type = BodyType.DynamicBody;
		darkNinjaBodyDef.position.set(250, 250);
		
		darkNinja = new GameChar(texture, world, darkNinjaBodyDef, fixture, darkNinjaDimensions);

	}


	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		camera.update();
		debugRenderer.render(world, camera.combined);
		world.step(1/60f, 6, 2);
		Array<Body> bodies = new Array<Body>(world.getBodyCount());
		world.getBodies(bodies);
		for (Body b : bodies) {
			Object userData = b.getUserData();
			if (!(userData instanceof GameObject)) {
				continue;
			}
			GameObject gameObject = (GameObject) userData;
			Vector2 position = b.getPosition();
			gameObject.setX(position.x);
			gameObject.setY(position.y);
		}
		batch.setProjectionMatrix(camera.combined);
		   batch.begin();
		   {
			
			darkNinja.render(batch);
			
		batch.end();
		   
		   int xVel = 0;
		   int yVel = 0;
		   
		   
		  
				   
		   if(Gdx.input.isKeyPressed(Keys.LEFT)) xVel -= 200;
		   if(Gdx.input.isKeyPressed(Keys.RIGHT)) xVel += 200;
		   
		   if (xVel != 0) {
			   darkNinja.setX(darkNinja.getX() + xVel * Gdx.graphics.getDeltaTime());
			   xVel = 0;
		   }
		   		   
		  	//if (checkifair == false)	   
		   if (Gdx.input.isKeyPressed(Keys.UP)) yVel = 1000;
		   if (yVel != 0) 
			   darkNinja.setY(darkNinja.getY() + yVel * Gdx.graphics.getDeltaTime());
		   }	   
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
	}
}