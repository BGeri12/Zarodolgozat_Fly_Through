package com.example.zarodolgozat_fly_through;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistracioActivity extends AppCompatActivity {

    private EditText et_felhasznalonev,et_jelszo,et_email;
    private Button btn_elkuld;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private Users users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracio);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

        adatbevitel();
    }

    private void adatbevitel() {

        btn_elkuld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_felhasznalonev.getText().toString().isEmpty() || et_jelszo.getText().toString().isEmpty()
                        || et_email.getText().toString().isEmpty()) {
                    Toast.makeText(RegistracioActivity.this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
                } else {

                    firebaseAuth.createUserWithEmailAndPassword
                            (et_email.getText().toString(),et_jelszo.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (!firebaseUser.isEmailVerified()){
                                users = new Users(et_felhasznalonev.getText().toString(),
                                        et_jelszo.getText().toString(),
                                        et_email.getText().toString());
                                databaseReference.child(firebaseUser.getUid()).setValue(users);
                                firebaseUser.sendEmailVerification();
                                Intent intent = new Intent(RegistracioActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(RegistracioActivity.this,"Sikeres regisztráció !!!",Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegistracioActivity.this,"Kérem erősítse meg az email címét !!!",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegistracioActivity.this,"Ezzel  az email címmel már regisztráltak !!!",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(RegistracioActivity.this,"Hiba keletkezett a regisztráció során !!!"+ e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void init() {

        et_felhasznalonev = findViewById(R.id.editRegisztracioUserName);
        et_jelszo = findViewById(R.id.editRegisztracioPassword);
        et_email = findViewById(R.id.editRegisztracioEmail);
        btn_elkuld = findViewById(R.id.buttonAdatbevitel);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        users = new Users();

        firebaseAuth = FirebaseAuth.getInstance();

    }
}