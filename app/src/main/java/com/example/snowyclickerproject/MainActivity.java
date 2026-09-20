package com.example.snowyclickerproject;

import static android.graphics.Typeface.BOLD;

import android.app.Notification;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView snowman, scarfImage, hatImage, tinyHat, tinyScarf;
    ConstraintLayout constraintLayout;
    TextView scoreText, scoreNum, textHat, textScarf;
    TextView hat, scarf, textOne;
    Button buyScarf, buyTopHat;
    Switch musicSwitch;
    int score = 0; int addScoreBy = 1;
    int costofHat = 10; int costofScarf = 5;
    long time = 12000;
    int numHats = 0; int numScarfs = 0;
    int duration = 10000;
    int randX, randY;
    private CountDownTimer timer;
    private MediaPlayer mp; private MediaPlayer snowyMp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mp = MediaPlayer.create(this, R.raw.bells);
        snowyMp = MediaPlayer.create(this, R.raw.frostythesnowman);
        snowyMp.setLooping(true);

        musicSwitch = findViewById(R.id.switch1);
        snowman = findViewById(R.id.snowman);
        scoreNum = findViewById(R.id.textActualScore);
        buyScarf = findViewById(R.id.buttonScarf);
        buyTopHat = findViewById(R.id.buttonHat);

        scarfImage = findViewById(R.id.imageScarf);
        hatImage = findViewById(R.id.imageHat);

        textHat = findViewById(R.id.textHatPurs);
        textScarf = findViewById(R.id.textScarfPurs);

        tinyHat = findViewById(R.id.miniHatImage);
        tinyScarf = findViewById(R.id.miniScarfImage);

        scarf = findViewById(R.id.textView);
        hat = findViewById(R.id.textView2);

        constraintLayout = findViewById(R.id.main);

        tinyHat.setVisibility(View.INVISIBLE);
        tinyScarf.setVisibility(View.INVISIBLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        final int widthArea = displayMetrics.widthPixels;
        final int heightArea = displayMetrics.heightPixels;

        final RotateAnimation scarfRotateAni = new RotateAnimation(0.0f, 360.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        scarfRotateAni.setDuration(12000);

        final ScaleAnimation hatGrow = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, ScaleAnimation.RELATIVE_TO_SELF,
                0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

       // hatGrow.setRepeatCount(Animation.INFINITE);
        hatGrow.setDuration(400);

        final ScaleAnimation aniGrow = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, ScaleAnimation.RELATIVE_TO_SELF,
                0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

        aniGrow.setDuration(200);

        constraintLayout = findViewById(R.id.main);

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("TAGGY","music should be playing!");
                    snowyMp.start();
                }
                else if(isChecked == false){
                    snowyMp.stop();
                }
            }
        });



        Runnable runny = new Runnable() {
            @Override
            public void run() {
                removeTheView();
            }
        };
        Handler h = new Handler();

        snowman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////////////////////////////////////////////////////////////////////////////////
                textOne = new TextView(MainActivity.this);
                textOne.setId(View.generateViewId());
                textOne.setText("+1 click!");
                textOne.setTextColor(Color.rgb(103,58,183));
                textOne.setTypeface(null, BOLD);

                Random random = new Random();
                int x = random.nextInt(500)-50; // makes it a random off set starting with -50 and then 100 plus that (so -50 to 50)
                int y = random.nextInt(500)-50;

                //set parameters for the views
                ConstraintLayout.LayoutParams textViewParams = new
                        ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //add the  parameters to the textview
                textOne.setLayoutParams(textViewParams);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(textOne.getId(),ConstraintSet.TOP, snowman.getId(), ConstraintSet.BOTTOM, y);
                constraintSet.connect(textOne.getId(),ConstraintSet.START, snowman.getId(), ConstraintSet.START, x);
                constraintSet.applyTo(constraintLayout);
                ///////////////////////////////////////////////////////////////////////////////////////////////

                constraintLayout.addView(textOne);
                randomize();
                Log.d("EEP - SATHVIKA","X: "+randX+"Y: "+randY);
                textOne.setX(randX);  textOne.setY(randY);  //before we had it x = 500, y = 600 or something like that
                textOne.animate().translationY(-200).alpha(0).setDuration(1000).start();
                //translationY will move it up by 50 pixels, setDuration is how long it takes to move up, alpha will make it fade to transparent (0)
                snowman.startAnimation(aniGrow);
                if(snowman.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.snowman).getConstantState())){
                    addToScore(addScoreBy);
                    Log.d("taglyer", "onClick: hey its the reg snowman");
                }
                else if(snowman.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.hatsnowmanfin).getConstantState())){
                    addToScore(addScoreBy);
                }
                scoreNum.setText(""+score);
                h.postDelayed(runny, 1000);
            }
        });

        buyTopHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score >= costofHat) {
                    mp.start();
                    subtractFromScore(costofHat);
                    numHats++;
                    tinyHat.setVisibility(View.VISIBLE);
                    hatImage.startAnimation(hatGrow);
                    textHat.setText(""+numHats+"");
                    scoreNum.setText(" "+score);
                    addScoreBy *= 3;
               //     Toast.makeText(MainActivity.this, "HAT!", Toast.LENGTH_SHORT).show();
                    snowman.setImageResource(R.drawable.hatsnowmanfin);
                    costofHat*=3;
                    buyTopHat.setText("Purchase: " + costofHat);
                }
                else{
                    Toast.makeText(MainActivity.this, "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buyScarf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // passively adds plus two (not sure if it'll crash yet though, add thread safety!)
                if(score >= costofScarf){
                    subtractFromScore(costofScarf);
                    numScarfs++;
                    tinyScarf.setVisibility(View.VISIBLE);
                    textScarf.setText(""+numScarfs+"");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mp.start();

                                // handler to update the UI, UI stuff needs to happen on main thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // start ani safely on the UI thread
                                        scarfImage.startAnimation(scarfRotateAni);
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
          /*          mp.start();
                    scarfImage.startAnimation(scarfRotateAni);*/
                    scoreNum.setText(""+score);
                    scarf.setText("A scarf produces "+costofScarf*2*2+" snowflakes a second for 12 seconds.");
                    timerScarfMethod();
                    costofScarf*=2;
                    buyScarf.setText("Purchase: " + costofScarf);
                }
                else{
                    Toast.makeText(MainActivity.this, "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void removeTheView(){
        constraintLayout.removeView(textOne);
    }

    public void randomize(){
        randX = (int)(Math.random() * 300) + 300;
        randY = (int)(Math.random() * 200) + 600;
    }

    public synchronized void addToScore(int addy) {     // synch methods make it so only one thread can run at a time,
                                                        // meaning the score is always added to/subtracted from the latest updated value
        score += addy;
    }
    public synchronized void subtractFromScore(int subb) {   // no overwhelming with multiple subtractions at once
        score -= subb;
    }



    int passiveAddy = 10;
    public void timerScarfMethod(){
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                addToScore(passiveAddy);
                scoreNum.setText(""+score);
                Log.d("PASSIVEING", "im walking here");
            }

            @Override
            public void onFinish() {
                addScoreBy *=1;
                passiveAddy *= 2;
            }
        }.start();

    }
}
