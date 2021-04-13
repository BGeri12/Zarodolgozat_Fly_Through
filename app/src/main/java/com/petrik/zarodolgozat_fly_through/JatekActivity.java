package com.petrik.zarodolgozat_fly_through;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JatekActivity extends AppCompatActivity {

    public static TextView txt_score,txt_bestScore,txt_scoreOver;
    public static RelativeLayout rl_gameOver;
    private static Button btn_start;
    private JatekMegjelenit jatekMegjelemit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Allandok.SCREEN_HEIGHT = dm.heightPixels;
        Allandok.SCREEN_WIDTH = dm.widthPixels;

        setContentView(R.layout.activity_jatek);
        init();
    }

    private void init() {

        txt_score = findViewById(R.id.txt_score);
        txt_bestScore = findViewById(R.id.txt_bestScore);
        txt_scoreOver = findViewById(R.id.txt_scoreOver);
        rl_gameOver = findViewById(R.id.rl_gameover);
        btn_start = findViewById(R.id.btn_start);
        jatekMegjelemit = findViewById(R.id.jatekView);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jatekMegjelemit.setStart(true);
                txt_score.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.INVISIBLE);
            }
        });
        rl_gameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setVisibility(View.VISIBLE);
                rl_gameOver.setVisibility(View.INVISIBLE);
                jatekMegjelemit.setStart(false);
                jatekMegjelemit.reset();
            }
        });

    }
}