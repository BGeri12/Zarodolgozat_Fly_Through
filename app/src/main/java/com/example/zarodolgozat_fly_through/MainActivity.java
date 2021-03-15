package com.example.zarodolgozat_fly_through;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_felhasznaloNev,et_jelszo;
    private Button btn_bejelentkezes,btn_regisztracio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        
        JatekActivityreIranyit();
        RegisztracioActivityreIranyit();
    }

    private void RegisztracioActivityreIranyit() {

        btn_regisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegistracioActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void JatekActivityreIranyit() {
        btn_bejelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_felhasznaloNev.getText().toString().isEmpty() || et_jelszo.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, JatekActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void init() {

        et_felhasznaloNev = findViewById(R.id.editBejelentUserName);
        et_jelszo = findViewById(R.id.editBejelentPassword);
        btn_bejelentkezes = findViewById(R.id.buttonBejelent);
        btn_regisztracio = findViewById(R.id.buttonAtIranyitRegisztracio);
    }
}