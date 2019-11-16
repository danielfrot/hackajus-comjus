package com.example.root.appmultitec.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.appmultitec.R;
import com.example.root.appmultitec.Helper.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
    EditText email;
    EditText senha;
    Button btentrar;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



            inicializarComponentes();

    }


    public void inicializarComponentes(){


        if(currentUser != null){


            Intent intent = new Intent(LoginActivity.this,PrincipalActivity.class);
            startActivity(intent);

        }


        getSupportActionBar().hide();
        email= findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        btentrar = findViewById(R.id.btEntrar);



    }


    public void Login(View view){


            auth.signInWithEmailAndPassword(email.getText().toString(),senha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        Intent intent = new Intent(LoginActivity.this,PrincipalActivity.class);
                        startActivity(intent);

                    }else {

                        Toast.makeText(getApplicationContext(),"Falha ao logar: "+task.getException().toString(),Toast.LENGTH_LONG).show();

                    }

                }
            });


    }
}
