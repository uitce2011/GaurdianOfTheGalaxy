package uit.guardianofthegalaxy;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.zip.Checksum;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.modifier.MoveXModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.modifier.ScaleAtModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.facebook.AppEventsLogger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.Log;

public class MainActivity extends BaseGameActivity implements
		IOnSceneTouchListener {

	private static int CAMERA_WIDTH;
	private static int CAMERA_HEIGHT;

	private Camera mCamera;
	private Scene scene;
	// Khai báo biến background
	private Texture mAutoParallaxBackgroundTexture;
	private TextureRegion mParallaxLayerBack;
	// Khai báo biến sprite animate
	private Texture mTextureAirplanet;
	private TiledTextureRegion mTextureRegionAirplanet;
	private AnimatedSprite Player;
	// Khai báo biến sprite
	private Texture mTexturePlanets;
	private TextureRegion mTextureRegionPlanets;
	private Texture mTexturePlanets2;
	private TextureRegion mTextureRegionPlanets2;
	private Texture mTexturePlanets3;
	private TextureRegion mTextureRegionPlanets3;
	private Texture mTexturePlanets4;
	private TextureRegion mTextureRegionPlanets4;
	private Texture mTexturePlanets5;
	private TextureRegion mTextureRegionPlanets5;
	// Khai báo biến Meteors.
	private Texture mTextureMeteors;
	private TextureRegion mTextureRegionMeteors;
	private Sprite SpriteMeteors;
	private LinkedList<Sprite> targetLLMeteors;
	private LinkedList<Sprite> TargetsToBeAddedMeteors;
	// Khai báo biến va chạm
	private Texture mTextureVacham;
	private TiledTextureRegion mTiledTextureRegionVacham;
	private AnimatedSprite Vacham;
	// Game Over
	private Texture mTextureGameover;
	private TextureRegion mTextureRegionGameover;
	private Sprite SpriteGameover;
	// Khai báo biến font
	private Texture mFontTexture;
	private Font mFont;
	private Text TextScore;
	private Texture FontGameoverTexture;
	private Font FontGameover;
	private ChangeableText TextBack;
	private ChangeableText TextAgain;


	// Khao báo biến music
	private Music backgroundMusic;
	private Sound Boom;
	QuyDaoBay quydaobay = new QuyDaoBay();
	float ty, py;
	int d, range;
	int dem = 0;
	int ScoreNumber = 0;
	int HighScore = 0;
	float scalesprite,speedmeteors,speedplanet;
	boolean check1 = false;
	boolean check2 = false;
	boolean check3 = false;
	boolean check4 = false;
	boolean check5 = false;
	boolean checkend = false;
	boolean checkpoint1 = true;
	boolean checkpoint2 = true;
	boolean checkpoint3 = true;
	boolean checkpoint4 = true;
	boolean checkpoint5 = true;
	boolean checkvacham = false;
	boolean checkmove = false;
	boolean checktouch = false;
	@Override
	public Engine onLoadEngine() {
		// load thông số màn hình của thiết bị
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		CAMERA_WIDTH = width;
		CAMERA_HEIGHT = height;
		// /////////////////////////////////////
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final Engine engine = new Engine(new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera)
				.setNeedsMusic(true).setNeedsSound(true));
		return engine;
	}

	@Override
	public void onLoadResources() {

		// ============Load Background=======================//
		this.mAutoParallaxBackgroundTexture = new Texture(2048, 1024,
				TextureOptions.DEFAULT);
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(
				this.mAutoParallaxBackgroundTexture, this,
				"gfx/background1024x2048.png", 0, 0);
		this.mEngine.getTextureManager().loadTextures(
				this.mAutoParallaxBackgroundTexture);

		// ===========Load sprite animate============//
		TextureRegionFactory.setAssetBasePath("gfx/");
		this.mTextureAirplanet = new Texture(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mTextureRegionAirplanet = TextureRegionFactory
				.createTiledFromAsset(this.mTextureAirplanet, this,
						"airplanet.png", 0, 0, 1, 1);
		this.mEngine.getTextureManager().loadTexture(this.mTextureAirplanet);

		// =================Load sprite Planets=========================//
		this.mTexturePlanets = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTextureRegionPlanets = TextureRegionFactory.createFromAsset(
				this.mTexturePlanets, this, "planet1.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets);

		this.mTexturePlanets2 = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTextureRegionPlanets2 = TextureRegionFactory.createFromAsset(
				this.mTexturePlanets2, this, "planet2.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets2);

		this.mTexturePlanets3 = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTextureRegionPlanets3 = TextureRegionFactory.createFromAsset(
				this.mTexturePlanets3, this, "planet3.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets3);

		this.mTexturePlanets4 = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTextureRegionPlanets4 = TextureRegionFactory.createFromAsset(
				this.mTexturePlanets4, this, "planet4.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets4);

		this.mTexturePlanets5 = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTextureRegionPlanets5 = TextureRegionFactory.createFromAsset(
				this.mTexturePlanets5, this, "planet5.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets5);

		// ===================Load Meteors==========================//
		this.mTextureMeteors = new Texture(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTextureRegionMeteors = TextureRegionFactory.createFromAsset(
				this.mTextureMeteors, this, "Meteors.png", 0, 0);
		// this.mTextureRegionMeteors =
		// TextureRegionFactory.createTiledFromAsset(this.mTextureMeteors, this,
		// "meteors1.png", 0, 0, 1, 1);
		this.mEngine.getTextureManager().loadTexture(this.mTextureMeteors);

		// ================== Gameover ==============================//
		this.mTextureGameover = new Texture(512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTextureRegionGameover = TextureRegionFactory.createFromAsset(
				this.mTextureGameover, this, "gameover.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mTextureGameover);

		// =================Load Vacham=============================//
		this.mTextureVacham = new Texture(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mTiledTextureRegionVacham = TextureRegionFactory
				.createTiledFromAsset(this.mTextureVacham, this, "burst.png",
						0, 0, 7, 7);
		mEngine.getTextureManager().loadTexture(mTextureVacham);

		// Text
		FontFactory.setAssetBasePath("font/");
		this.mFontTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.FontGameoverTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mFont = FontFactory.createFromAsset(this.mFontTexture, this,
				"Droid.ttf", 45, true, Color.YELLOW);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);

		FontGameover = FontFactory.createFromAsset(this.FontGameoverTexture,
				this, "MessingLettern.ttf", 45, true, Color.WHITE);
		this.mEngine.getTextureManager().loadTexture(this.FontGameoverTexture);
		this.mEngine.getFontManager().loadFont(this.FontGameover);
		// ///Music
		MusicFactory.setAssetBasePath("mfx/");
		try {
			backgroundMusic = MusicFactory.createMusicFromAsset(
					mEngine.getMusicManager(), this, "music_back.mp3");
			backgroundMusic.setLooping(true);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SoundFactory.setAssetBasePath("mfx/");
		try {
			Boom = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(),
					this, "Boom.mp3");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Scene onLoadScene() {
		
		// /=====================*Load background*========================//
		this.mEngine.registerUpdateHandler(new FPSLogger());
		scene = new Scene();
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(
				0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-50.0f,
				new Sprite(0, CAMERA_HEIGHT
						- this.mParallaxLayerBack.getHeight(),
						this.mParallaxLayerBack)));
		scene.setBackground(autoParallaxBackground);
		// =======================*Set Music*========================//
		SharedPreferences pre = getSharedPreferences("MusicStatus",
				MODE_PRIVATE);
		SharedPreferences.Editor edit = pre.edit();
		int Onoff = pre.getInt("MusicStatus", 0);
		if (Onoff == 1) {
			Boom.setVolume(0);

		}

		// ======================*Load sprite animate*========================//

		// Vị trí khởi tạo AnimatedSprite
		final int PlayerX = 0;
		final int PlayerY = (CAMERA_HEIGHT - this.mTextureRegionAirplanet
				.getHeight()) / 2;
		// Tạo đối tượng AnimatedSprite
		Player = new AnimatedSprite(PlayerX, PlayerY,
				this.mTextureRegionAirplanet);

		final PhysicsHandler physicsHandler = new PhysicsHandler(Player);
		Player.registerUpdateHandler(physicsHandler);
		scene.attachChild(Player);
		// Player.setScale((float) 0.7);

		// =======================*Load sprite Planets*=======================//
		final Sprite SpritePlanets = new Sprite(CAMERA_WIDTH,
				CAMERA_HEIGHT / 2, mTextureRegionPlanets);
		scene.attachChild(SpritePlanets);
		// SpritePlanets.setScale((float) 0.75);

		final Sprite SpritePlanets2 = new Sprite(CAMERA_WIDTH,
				CAMERA_HEIGHT / 2, mTextureRegionPlanets2);
		scene.attachChild(SpritePlanets2);
		// SpritePlanets2.setScale((float) 0.75);

		final Sprite SpritePlanets3 = new Sprite(CAMERA_WIDTH,
				CAMERA_HEIGHT / 2, mTextureRegionPlanets3);
		scene.attachChild(SpritePlanets3);
		// SpritePlanets3.setScale(1);

		final Sprite SpritePlanets4 = new Sprite(CAMERA_WIDTH,
				CAMERA_HEIGHT / 2, mTextureRegionPlanets4);
		scene.attachChild(SpritePlanets4);
		// SpritePlanets4.setScale(1);

		final Sprite SpritePlanets5 = new Sprite(CAMERA_WIDTH,
				CAMERA_HEIGHT / 2, mTextureRegionPlanets5);
		scene.attachChild(SpritePlanets5);
		// SpritePlanets5.setScale(1);

		SpriteMeteors = new Sprite(CAMERA_WIDTH, CAMERA_HEIGHT / 2,
				mTextureRegionMeteors);
		scene.attachChild(SpriteMeteors);
		// =========== Game over============//
		SpriteGameover = new Sprite(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2,
				mTextureRegionGameover);
		SpriteGameover.setPosition(CAMERA_WIDTH / 2 - SpriteGameover.getWidth()
				/ 2, CAMERA_HEIGHT / 2 / 2);
		// ======================Load Enemy===============================//
		if (CAMERA_HEIGHT > 480) {
			scalesprite=1;
			speedplanet=15;
			speedmeteors=60;
			
		} else if (CAMERA_HEIGHT <= 480) {
			scalesprite = (float) 0.5;
			speedplanet=10;
			speedmeteors=40;
			
		}
		Player.setScale((float) scalesprite);
		SpritePlanets.setScale((float) scalesprite);
		SpritePlanets2.setScale((float) scalesprite);
		SpritePlanets3.setScale((float) scalesprite);
		SpritePlanets4.setScale((float) scalesprite);
		SpritePlanets5.setScale((float) scalesprite);
		SpriteMeteors.setScale((float) scalesprite);
		// =======================Load Va chạm=============================
		Vacham = new AnimatedSprite((float) (-CAMERA_WIDTH), 100,
				this.mTiledTextureRegionVacham);
		scene.attachChild(Vacham);
		Vacham.setScale((float) 2f);
		Vacham.setRotation(0);
		Vacham.animate(new long[] { 200, 200, 200, 200 }, 0, 3, true);
		Vacham.setVisible(false);

		// ===================================================
		Text mText = new Text(70, 10, mFont, "Score:");
		scene.attachChild(mText);
		Text HighText = new Text(300, 10, mFont, "HighScore: ");
		scene.attachChild(HighText);

		final ChangeableText TextCurrentScore = new ChangeableText(mText.getX()
				+ mText.getWidth() + 10, 10, mFont, "", 10);
		scene.attachChild(TextCurrentScore);
		final ChangeableText TextHighScore = new ChangeableText(HighText.getX()
				+ HighText.getWidth() + 10, 10, mFont, "", 10);
		scene.attachChild(TextHighScore);
		
		// ===================* Meteors and move *============================//

		// SpriteMeteors.setScale((float) 0.75);

		Back_Menu("BackMenu", CAMERA_WIDTH, CAMERA_HEIGHT);
		Play_Again("PlayAgain", CAMERA_WIDTH, CAMERA_HEIGHT);
		IUpdateHandler detect = new IUpdateHandler() {
			@Override
			public void reset() {

			}

			public void onUpdate(float pSecondsElapsed) {
				try {
					// ========== Move Metoers ==============//
					if (checkvacham == true) {
						if (checkmove == false)
							scene.attachChild(SpriteMeteors);
						checkvacham = false;
						Random rand = new Random();
						int minY = 5;
						int maxY = CAMERA_HEIGHT
								- mTextureRegionMeteors.getWidth();
						int rangeY = maxY - minY;
						ty = rand.nextInt(rangeY) + minY;
						SpriteMeteors.setPosition(CAMERA_WIDTH, ty);
						d = rand.nextInt(3);
					}

					if ((SpriteMeteors.getX() < -50)) {
						Random rand = new Random();
						int minY = 5;
						int maxY = CAMERA_HEIGHT
								- mTextureRegionMeteors.getWidth();
						int rangeY = maxY - minY;
						ty = rand.nextInt(rangeY) + minY;
						double x = CAMERA_WIDTH;
						SpriteMeteors.setPosition(CAMERA_WIDTH, ty);
						quydaobay.setXY(ty, ty);
						d = rand.nextInt(3);
					}
					if (checkmove == false)
						if (d == 0)
							SpriteMeteors.setPosition(
									SpriteMeteors.getX() - speedmeteors, ty);
						else {

							quydaobay.setXY(SpriteMeteors.getX(), 0);
							quydaobay.moveDirection(d);
							ty = quydaobay.getY();
							SpriteMeteors.setPosition(
									SpriteMeteors.getX() - speedmeteors, ty);
						}
					else
						SpriteMeteors.setPosition(CAMERA_WIDTH, CAMERA_HEIGHT);
					if ((Player.getY() < -80)
							|| (Player.getY() > CAMERA_HEIGHT
									- Player.getWidth())) {

						gameover(scene);
					}
					if (checkmove == true)
						Boom.setVolume(0);
					// ================ Move Player ====================//
					if (checkmove == false)
						if (checktouch == false)
							Player.setPosition(Player.getX(),
									Player.getY() + 10);
						else
							Player.setPosition(Player.getX(), Player.getY() + 5);
					else
						Player.setPosition(Player.getX(), Player.getY());
					Thread.sleep(50);
					int check = 0;
					// ========================
					TextCurrentScore.setText(String.valueOf(ScoreNumber));
					SavingScore(ScoreNumber);
					TextHighScore.setText(String.valueOf(HighScore));
					// ================ Move Planets ===================//
					if (check == 0) {
						if (check1 == false) {
							Random rand = new Random();
							int minY = mTexturePlanets.getHeight();
							int maxY = CAMERA_HEIGHT;
							int rangeY = maxY - minY;
							range = rand.nextInt(CAMERA_WIDTH / 2);
							int py1 = rand.nextInt(rangeY);
							SpritePlanets.setPosition(CAMERA_WIDTH, py1);
							check1 = true;
						}
						if (SpritePlanets.getX() > 0 - SpritePlanets.getWidth()) {
							if (checkmove == false)
								SpritePlanets.setPosition(
										SpritePlanets.getX() - speedplanet,
										SpritePlanets.getY());
							else
								SpritePlanets.setPosition(SpritePlanets.getX(),
										SpritePlanets.getY());
							
							if (SpritePlanets.collidesWith(SpriteMeteors)) {
								scene.detachChild(SpriteMeteors);
								Boom.play();
								checkvacham = true;
							}

							if (vaChamPlanet2(Player, SpritePlanets)) {
								gameover(scene);
							}
							if ((SpritePlanets.getX() < Player.getX())
									&& (checkpoint1 == true)) {
								ScoreNumber++;
								checkpoint1 = false;
							}
						}

						// ===================================================================//
						if (SpritePlanets.getX() < range && (check2 == false)) {
							Random rand = new Random();
							int minY = mTexturePlanets2.getHeight();
							int maxY = CAMERA_HEIGHT;
							int rangeY = maxY - minY;
							range = rand.nextInt(CAMERA_WIDTH / 2);
							int py2 = rand.nextInt(rangeY);
							SpritePlanets2.setPosition(CAMERA_WIDTH, py2);
							check2 = true;
							check++;
						}
						if ((SpritePlanets2.getX() > 0 - SpritePlanets2
								.getWidth()) && (check2 == true)) {
							if (checkmove == false)
								SpritePlanets2.setPosition(
										SpritePlanets2.getX() - speedplanet,
										SpritePlanets2.getY());
							else
								SpritePlanets2.setPosition(
										SpritePlanets2.getX(),
										SpritePlanets2.getY());
							if (SpritePlanets2.collidesWith(SpriteMeteors)) {
								scene.detachChild(SpriteMeteors);
								Boom.play();
								checkvacham = true;
							}
							if (vaChamPlanet2(Player, SpritePlanets2)) {
								gameover(scene);
							}
							if ((SpritePlanets2.getX() < Player.getX())
									&& (checkpoint2 == true)) {
								ScoreNumber++;
								checkpoint2 = false;
							}
						}

						// ====================================================================//
						if (SpritePlanets2.getX() < range && (check3 == false)) {
							Random rand = new Random();
							int minY = mTexturePlanets3.getHeight();
							int maxY = CAMERA_HEIGHT;
							int rangeY = maxY - minY;
							range = rand.nextInt(CAMERA_WIDTH / 2);
							int py3 = rand.nextInt(rangeY);
							SpritePlanets3.setPosition(CAMERA_WIDTH, py3);
							check3 = true;
							check++;
						}
						if ((SpritePlanets3.getX() > 0 - SpritePlanets3
								.getWidth()) && (check3 == true)) {
							if (checkmove == false)
								SpritePlanets3.setPosition(
										SpritePlanets3.getX() - speedplanet,
										SpritePlanets3.getY());
							else
								SpritePlanets3.setPosition(
										SpritePlanets3.getX(),
										SpritePlanets3.getY());
							if (SpritePlanets3.collidesWith(SpriteMeteors)) {
								scene.detachChild(SpriteMeteors);
								Boom.play();
								checkvacham = true;
							}
							if (vaChamPlanet2(Player, SpritePlanets3)) {
								gameover(scene);
							}
							if ((SpritePlanets3.getX() < Player.getX())
									&& (checkpoint3 == true)) {
								ScoreNumber++;
								checkpoint3 = false;
							}
						}

						// ====================================================================//
						if (SpritePlanets3.getX() < range && (check4 == false)) {
							Random rand = new Random();
							int minY = mTexturePlanets4.getHeight();
							int maxY = CAMERA_HEIGHT;
							int rangeY = maxY - minY;
							range = rand.nextInt(CAMERA_WIDTH / 2);
							int py4 = rand.nextInt(rangeY);
							SpritePlanets4.setPosition(CAMERA_WIDTH, py4);
							check4 = true;
							check++;
						}
						if ((SpritePlanets4.getX() > 0 - SpritePlanets4
								.getWidth()) && (check4 == true)) {
							if (checkmove == false)
								SpritePlanets4.setPosition(
										SpritePlanets4.getX() - speedplanet,
										SpritePlanets4.getY());
							else
								SpritePlanets4.setPosition(
										SpritePlanets4.getX(),
										SpritePlanets4.getY());
							if (SpritePlanets4.collidesWith(SpriteMeteors)) {
								scene.detachChild(SpriteMeteors);
								Boom.play();
								checkvacham = true;
							}
							if (vaChamPlanet2(Player, SpritePlanets4)) {
								gameover(scene);
							}
							if ((SpritePlanets4.getX() < Player.getX())
									&& (checkpoint4 == true)) {
								ScoreNumber++;
								checkpoint4 = false;
							}
						}

						// ====================================================================//
						if (SpritePlanets4.getX() < range && (check5 == false)) {
							Random rand = new Random();
							int minY = mTexturePlanets5.getHeight();
							int maxY = CAMERA_HEIGHT;
							int rangeY = maxY - minY;
							int py5 = rand.nextInt(rangeY);
							SpritePlanets5.setPosition(CAMERA_WIDTH, py5);
							check5 = true;
							checkend = true;
						}
						if ((SpritePlanets5.getX() > 0 - SpritePlanets5
								.getWidth()) && (check5 == true)) {
							if (checkmove == false)
								SpritePlanets5.setPosition(
										SpritePlanets5.getX() - speedplanet,
										SpritePlanets5.getY());
							else
								SpritePlanets5.setPosition(
										SpritePlanets5.getX(),
										SpritePlanets5.getY());
							if (SpritePlanets5.collidesWith(SpriteMeteors)) {
								scene.detachChild(SpriteMeteors);
								Boom.play();
								checkvacham = true;
							}
							if (vaChamPlanet2(Player, SpritePlanets5)) {

								gameover(scene);
							}
							if ((SpritePlanets5.getX() < Player.getX())
									&& (checkpoint5 == true)) {
								ScoreNumber++;
								checkpoint5 = false;
							}
						}

						if (SpritePlanets5.getX() < -50) {
							check5 = false;
							SpritePlanets5.setPosition(CAMERA_WIDTH,
									CAMERA_HEIGHT);
							checkpoint5 = true;
						}
						if ((SpritePlanets5.getX() < range)
								&& (checkend == true)) {
							check1 = false;
							check2 = false;
							check3 = false;
							check4 = false;
							checkend = false;
							SpritePlanets.setPosition(CAMERA_WIDTH,
									CAMERA_HEIGHT);
							SpritePlanets2.setPosition(CAMERA_WIDTH,
									CAMERA_HEIGHT);
							SpritePlanets3.setPosition(CAMERA_WIDTH,
									CAMERA_HEIGHT);
							SpritePlanets4.setPosition(CAMERA_WIDTH,
									CAMERA_HEIGHT);
							checkpoint1 = true;
							checkpoint2 = true;
							checkpoint3 = true;
							checkpoint4 = true;

						}

						// /===============================================//
						if (Player.collidesWith(SpriteMeteors)) {
							gameover(scene);
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		scene.registerUpdateHandler(detect);
		scene.setOnSceneTouchListener(this);

		// =======================*Load music*======================//
		// this.backgroundMusic.play();
		return scene;
	}

	

	@Override
	public void onLoadComplete() {

	}

	public void gameover(Scene scene) {
		scene.attachChild(SpriteGameover);
		Boom.play();
		scene.detachChild(SpriteMeteors);
		Vacham.setVisible(true);
		Vacham.setPosition(Player.getX(), Player.getY());
		scene.detachChild(Player);
		Boom.stop();
		checkmove = true;
		TextBack.setPosition(CAMERA_WIDTH / 2 + 20, CAMERA_HEIGHT / 2);
		TextAgain.setPosition(CAMERA_WIDTH / 2 - TextAgain.getWidth() - 20,
				CAMERA_HEIGHT / 2);
	}

	public boolean Back_Menu(String text, float x, float y) {
		TextBack = new ChangeableText(CAMERA_WIDTH, CAMERA_HEIGHT,
				FontGameover, "" + text) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE
						|| pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					scene.registerEntityModifier(new ScaleAtModifier(0.5f,
							1.0f, 0.0f, CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2));
					Intent myIntent = new Intent(MainActivity.this,
							Main_Menu.class);
					MainActivity.this.startActivity(myIntent);
					MainActivity.this.finish();
				}
				return true;
			}
		};
		TextBack.setPosition(x, y);
		scene.registerTouchArea(TextBack);
		scene.attachChild(TextBack);

		return true;
	}

	public boolean Play_Again(String text, float x, float y) {
		// hiá»‡n Sore
		TextAgain = new ChangeableText(CAMERA_WIDTH, CAMERA_HEIGHT,
				FontGameover, "" + text) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE
						|| pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					scene.registerEntityModifier(new ScaleAtModifier(0.5f,
							1.0f, 0.0f, CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2));
					Intent myIntent = new Intent(MainActivity.this,
							MainActivity.class);
					MainActivity.this.startActivity(myIntent);
					MainActivity.this.finish();
				}
				return true;
			}
		};
		TextAgain.setPosition(x, y);
		scene.registerTouchArea(TextAgain);
		scene.attachChild(TextAgain);

		return true;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			final float touchX = Player.getX();
			final float touchY = 75;
			checktouch = true;
			if (checkmove == false)
				touchProjectile(touchX, touchY);
			else
				touchProjectile(touchX, 0);
			return true;
		} else
			checktouch = false;
		return false;
	}

	private void touchProjectile(final float pX, final float pY) {
		// TODO Auto-generated method stub
		Player.setPosition(Player.getX(), Player.getY() - pY);

	}

	public boolean vaChamPlanet2(AnimatedSprite a, Sprite b) {
		AnimatedSprite A = new AnimatedSprite(0, 0, a.getTextureRegion());
		Sprite B = new Sprite(0, 0, b.getTextureRegion());
		float xb = b.getWidth() * scalesprite;
		float yb = b.getHeight() * scalesprite;
		float xa = a.getWidth() * scalesprite;
		float ya = a.getHeight() * scalesprite;
		
		if (a.getY()>=b.getY()){
			if (a.getX()<=b.getX())
			A.setPosition(a.getX()-xa-speedplanet, a.getY());
			else A.setPosition(a.getX()+xb, a.getY());
			B.setPosition(b.getX(), b.getY() - yb);
		}
		else {
			if (a.getX()<=b.getX())
			A.setPosition(a.getX()-xa-10, a.getY()-ya);
			else A.setPosition(a.getX()+xb, a.getY());
			B.setPosition(b.getX(), b.getY());
		}
		
		if (A.collidesWith(B))
			return true;
		return false;
	}
	public void vachamMetoers(AnimatedSprite a,Sprite b)
	{
		AnimatedSprite A = new AnimatedSprite(0, 0, a.getTextureRegion());
		Sprite B = new Sprite(0, 0, b.getTextureRegion());
		
	}
	public boolean vaChamSprite(Sprite a, Sprite b) {
		Sprite A = new Sprite(0, 0, a.getTextureRegion());
		Sprite B = new Sprite(0, 0, b.getTextureRegion());
		A.setPosition(a.getX(), a.getY());
		B.setPosition(b.getX(), b.getY());
		if (A.collidesWith(B))
			return true;
		return false;
	}

	public void SavingScore(int CurrentScore) {
		SharedPreferences pre = getSharedPreferences("HighScore", MODE_PRIVATE);
		SharedPreferences.Editor edit = pre.edit();
		HighScore = pre.getInt("HighScore", 0);
		if (CurrentScore > HighScore) {
			// edit.clear();
			edit.putInt("HighScore", CurrentScore);
			edit.commit();
		}
	}

	protected void onResume() {
		super.onResume();

		// Logs 'install' and 'app activate' App Events.
		AppEventsLogger.activateApp(this);
	}

	protected void onPause() {
		super.onPause();

		// Logs 'app deactivate' App Event.
		AppEventsLogger.deactivateApp(this);
	}
}
