package uit.guardianofthegalaxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Main_Menu extends Activity {
	private Button btnplay;
	private Button btnhighscore;
	private Button btnoption;
	private Button btnexit;
	private Button btnfacebook;
	private Button btngoogle;
	private Button btnhelp;
	private TextView txttitle;
	// private Context context = this;
	private MediaPlayer msback;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ============ delete title and full screen====================//
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_menu);

		// =================== get width and height screen================//
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;

		// ========== play background music=====================//
		msback = MediaPlayer.create(Main_Menu.this, R.raw.music_back);
		msback.setLooping(true);//
		msback.start(); //

		// ===================== creat font =========================//
		Typeface fontplay = Typeface.createFromAsset(getAssets(),
				"font/LOTSOFDOTZ.TTF");
		Typeface fontbtnother = Typeface.createFromAsset(getAssets(),
				"font/LOTSOFDOTZ.TTF");
		Typeface fonttitle = Typeface.createFromAsset(getAssets(),
				"font/VTCBelialsBlade3d.ttf");

		// ================= format title ========================//
		txttitle = (TextView) findViewById(R.id.txttitle);
		txttitle.setTypeface(fonttitle);
		int txtWidth = txttitle.getWidth();
		int txtHeight = txttitle.getHeight();
		// ================= format button ==========================//
		btnplay = (Button) findViewById(R.id.play);
		btnplay.setTypeface(fontplay);
		btnplay.setY((0 + txttitle.getHeight()) + 50);

		btnhighscore = (Button) findViewById(R.id.highscore);
		btnhighscore.setTypeface(fontbtnother);
		btnhighscore.setY(btnplay.getY() + 5);

		btnoption = (Button) findViewById(R.id.option);
		btnoption.setTypeface(fontbtnother);
		btnoption.setY(btnhighscore.getY() + 5);

		btnhelp = (Button) findViewById(R.id.help);
		btnhelp.setTypeface(fontbtnother);
		btnhelp.setY(btnoption.getY() + 5);

		btnexit = (Button) findViewById(R.id.exit);
		btnexit.setTypeface(fontbtnother);
		btnexit.setY(btnhelp.getY() + 5);

		btnfacebook = (Button) findViewById(R.id.share_fb);
		btnfacebook.setX(width / 2 - 50);

		btngoogle = (Button) findViewById(R.id.share_gg);
		btngoogle.setX(width / 2 - 10);

		SharedPreferences pre = getSharedPreferences("MusicStatus",
				MODE_PRIVATE);
		SharedPreferences.Editor edit = pre.edit();
		edit.putInt("MusicStatus", 0);
		edit.commit();
		// ================== event button=========================//
		btnplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(Main_Menu.this, MainActivity.class);
				Main_Menu.this.startActivity(myIntent);
				Main_Menu.this.finish();
			}
		});

		btnhighscore.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				int score = loadScore();
				Toast.makeText(Main_Menu.this, "High Score: " + score,
						Toast.LENGTH_SHORT).show();
			}

		});
		btnoption.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SharedPreferences pre = getSharedPreferences("MusicStatus",
						MODE_PRIVATE);
				SharedPreferences.Editor edit = pre.edit();
				int Onoff = pre.getInt("MusicStatus", 0);
				if (Onoff == 0) {
					msback.pause();
					edit.putInt("MusicStatus", 1);
					edit.commit();
				}
				if (Onoff == 1) {
					msback.start();
					edit.putInt("MusicStatus", 0);
					edit.commit();
				}
			}
		});
		btnhelp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setContentView(R.layout.help);
				Button btnviet = (Button) findViewById(R.id.btnvn);
				Button btneng = (Button) findViewById(R.id.btneng);
				Button btnback = (Button) findViewById(R.id.btnback);
				final TextView txttext = (TextView) findViewById(R.id.txtView);
				txttext.setX(10);
				Typeface fonthelp = Typeface.createFromAsset(getAssets(),
						"font/LOTSOFDOTZ.TTF");
				btnviet.setTypeface(fonthelp);
				btnviet.setTextSize(10);
				btneng.setTypeface(fonthelp);
				btneng.setTextSize(10);
				btnback.setTypeface(fonthelp);
				btnback.setTextSize(10);
				btnviet.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						txttext.setText("");
						txttext.setText(" ***Tên game: Guardian Of The Galaxy. \n "
								+ " ***Thể loại: Game 2D - Tính điểm. \n"
								+ " ***Cách chơi: Người chơi điều khiển chiếc phi cơ bằng cách chạm vào màn hình để vượt qua các hành tinh đang di chuyển và né tránh các thiên thạch bay với tốc độ nhanh. \n"
								+ " ***Cách tính điểm: vượt qua được 1 thiên thạch người chơi dc cộng 1d. Số điểm cao nhất sẽ được lưu lại.\n"
								+ " ***Game over: \n"
								+ "    - Phi thuyền va chạm với hành tinh hoặc thiên thạch. \n"
								+ "    - Phi thuyền rơi xuống đất hoặc vượt khỏi tầm nhìn. \n");

					}
				});
				btneng.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						txttext.setText("");
						txttext.setText(" ***Name: Guardian Of The Galaxy. \n "
								+ " ***Category: Game 2D - Count Point. \n"
								+ " ***How to play: Tap the screen to control the plane up and down and pass the planets and asteroids flying with high speed. \n"
								+ " ***Scoring method: overcome 1 planet player is add 1 point. Highscore is save.\n"
								+ " ***Game over: \n"
								+ "    - Aircraft collides with the planet or meteors. \n"
								+ "    - Aircraft fall to the ground or get out sigh. \n");

					}
				});
				btnback.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(Main_Menu.this, Main_Menu.class);
						Main_Menu.this.startActivity(myIntent);
						Main_Menu.this.finish();
						msback.stop();
					}
				});
			}
		});
		btnexit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Main_Menu.this.finish();
				msback.stop();
			}
		});

		btnfacebook.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// Bitmap bitmap=takeScreenshot();
				// saveBitmap(bitmap);
				Intent myIntent = new Intent(Main_Menu.this,
						FacebookActivity.class);
				Main_Menu.this.startActivity(myIntent);
			}
		});

		btngoogle.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(Main_Menu.this,
						GoogleActivity.class);
				Main_Menu.this.startActivity(myIntent);
			}
		});
	}

	public int loadScore() {
		int score;
		SharedPreferences pre = getSharedPreferences("HighScore", MODE_PRIVATE);
		score = pre.getInt("HighScore", 0);
		return score;
	}

	public Bitmap takeScreenshot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		return rootView.getDrawingCache();
	}

	public void saveBitmap(Bitmap bitmap) {
		File imagePath = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/screenshot.png");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(imagePath);
			bitmap.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e("GREC", e.getMessage(), e);
		} catch (IOException e) {
			Log.e("GREC", e.getMessage(), e);
		}
	}

}
