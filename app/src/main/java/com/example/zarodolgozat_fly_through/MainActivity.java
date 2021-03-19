package com.example.zarodolgozat_fly_through;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText et_email,et_jelszo;
    private Button btn_bejelentkezes,btn_regisztracio;

    private FirebaseAuth mAuth;

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

                if (et_email.getText().toString().isEmpty() || et_jelszo.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(et_email.getText().toString(),et_jelszo.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (!user.isEmailVerified())
                                        {
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(MainActivity.this, "Erősítsd meg az emailed", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else
                                        {
                                            Toast.makeText(MainActivity.this, "Sikeres bejelentkezés " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(MainActivity.this, JatekActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    else
                                        Toast.makeText(MainActivity.this, "Hibás email cím vagy jelszo!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private void init() {

        et_email = findViewById(R.id.editBejelentEmail);
        et_jelszo = findViewById(R.id.editBejelentPassword);
        btn_bejelentkezes = findViewById(R.id.buttonBejelent);
        btn_regisztracio = findViewById(R.id.buttonAtIranyitRegisztracio);

        mAuth = FirebaseAuth.getInstance();
    }
}