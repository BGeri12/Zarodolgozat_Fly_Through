package com.example.zarodolgozat_fly_through;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class JatekMegjelenit extends View {

    private KarakterObject bat;
    private Handler handler;
    private Runnable r;
    private ArrayList<AkadalyObject> arrAkadaly;
    private int sumpipe,distence;
    private int score,bestscore=0;
    private boolean start;
    private Context contex;

    public JatekMegjelenit(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.contex = context;
        SharedPreferences sp = context.getSharedPreferences("gamesetting",Context.MODE_PRIVATE);
        if (sp!=null){
            bestscore = sp.getInt("bestscore",0);
        }
        score = 0;
        start = false;

        initKarakter();
        initAkadaly();

        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    private void initAkadaly() {

        sumpipe = 4;
        distence = 300*Allandok.SCREEN_HEIGHT/1920;
        arrAkadaly = new ArrayList<>();
        for (int i = 0; i < sumpipe; i++) {
            if (i<sumpipe/2){
                this.arrAkadaly.add(new AkadalyObject(Allandok.SCREEN_WIDTH+i*((Allandok.SCREEN_WIDTH+200*Allandok.SCREEN_WIDTH/1080)/(sumpipe/2)),
                        0,200*Allandok.SCREEN_WIDTH/1080,Allandok.SCREEN_HEIGHT/2 ));
                this.arrAkadaly.get(this.arrAkadaly.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.akadaly2));
                this.arrAkadaly.get(this.arrAkadaly.size()-1).randomY();
            }else {
                this.arrAkadaly.add(new AkadalyObject(this.arrAkadaly.get(i-sumpipe/2).getX(),this.arrAkadaly.get(i-sumpipe/2).getY()
                        + this.arrAkadaly.get(i-sumpipe/2).getHeight() + this.distence,200*Allandok.SCREEN_WIDTH/1080, Allandok.SCREEN_HEIGHT/2));

                this.arrAkadaly.get(this.arrAkadaly.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.akadaly1));

            }
        }

    }

    private void initKarakter() {

        bat = new KarakterObject();
        bat.setWidth(100*Allandok.SCREEN_WIDTH/1080);
        bat.setHeight(100*Allandok.SCREEN_HEIGHT/1920);
        bat.setX(100*Allandok.SCREEN_WIDTH/1000);
        bat.setY(Allandok.SCREEN_HEIGHT/2-bat.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.denever1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.denever2));
        bat.setArrBms(arrBms);

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (start){

            bat.draw(canvas);
            for (int i = 0; i <sumpipe ; i++) {
                if (bat.getRect().intersect(arrAkadaly.get(i).getRect()) ||bat.getY()-bat.getHeight()<0 ||bat.getY()>Allandok.SCREEN_HEIGHT){

                    AkadalyObject.speed = 0;
                    JatekActivity.txt_scoreOver.setText(JatekActivity.txt_score.getText());
                    JatekActivity.txt_bestScore.setText("Best score:" +bestscore);
                    JatekActivity.txt_score.setVisibility(INVISIBLE);
                    JatekActivity.rl_gameOver.setVisibility(VISIBLE);
                }
                if (this.bat.getX()+this.bat.getWidth()>arrAkadaly.get(i).getX()+arrAkadaly.get(i).getWidth()/2
                        &&this.bat.getX()+this.bat.getWidth()<=arrAkadaly.get(i).getX()+arrAkadaly.get(i).getWidth()/2+AkadalyObject.speed
                        &&i<sumpipe/2){
                    score++;
                    if (score>bestscore){
                        bestscore = score;
                        SharedPreferences sp = contex.getSharedPreferences("gamessatting",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("bestscore",bestscore);
                        editor.apply();
                    }
                    JatekActivity.txt_score.setText(""+score);
                }
                if (this.arrAkadaly.get(i).getX() < -arrAkadaly.get(i).getWidth()){
                    this.arrAkadaly.get(i).setX(Allandok.SCREEN_WIDTH);
                    if (i<sumpipe/2){
                        arrAkadaly.get(i).randomY();
                    }else{
                        arrAkadaly.get(i).setY(this.arrAkadaly.get(i-sumpipe/2).getY()
                                + this.arrAkadaly.get(i-sumpipe/2).getHeight() + this.distence);
                    }
                }
                this.arrAkadaly.get(i).draw(canvas);
            }
        }else {
            if (bat.getY()>Allandok.SCREEN_HEIGHT/2){
                bat.setDrop(-15*Allandok.SCREEN_HEIGHT/1920);
            }
            bat.draw(canvas);
        }
        handler.postDelayed(r,10);

        handler.postDelayed(r,10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            bat.setDrop(-15);
        }
        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {

        JatekActivity.txt_score.setText("0");
        score = 0;
        initAkadaly();
        initAkadaly();

    }
}
