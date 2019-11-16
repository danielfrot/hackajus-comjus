package com.example.root.appmultitec.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.appmultitec.Model.Chamado;
import com.example.root.appmultitec.Model.Rme;
import com.example.root.appmultitec.R;

import java.io.Serializable;

public class chamadoActivity extends AppCompatActivity implements Serializable {

    Intent intent;

    private EditText local,data,hora,descricao;
    private Button btChamado;
     Rme rmeSelecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado);

        inicializarComponentes();



    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                     intent = new Intent(chamadoActivity.this,PrincipalActivity.class);
                    startActivity(intent);


                    return true;
                case R.id.navigation_dashboard:

                     intent = new Intent(chamadoActivity.this,FormRmeActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_notifications:

                    return true;

                case R.id.navigation_listaChamado:

                    intent = new Intent(chamadoActivity.this,ChamadoListActivity.class);
                    startActivity(intent);


                    return true;
            }
            return false;
        }
    };



    private void inicializarComponentes(){


        overridePendingTransition(0,0);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_notifications);
        local = findViewById(R.id.chamadoLocal);
        data = findViewById(R.id.chamadoData);
        hora = findViewById(R.id.chamadoHora);
        descricao = findViewById(R.id.chamadoDescricao);
        btChamado = findViewById(R.id.btChamado);

        final Chamado chamado = new Chamado();
       rmeSelecionado =(Rme) getIntent().getSerializableExtra("rmeAtual");


        btChamado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                chamado.setLocal(local.getText().toString());
                chamado.setData(data.getText().toString());
                chamado.setHora(hora.getText().toString());
                chamado.setDescricao(descricao.getText().toString());
                chamado.setDificuldade(rmeSelecionado.getTecnico());
                chamado.setIdRecebe(rmeSelecionado.getIdUsuario());
                //Toast.makeText(getApplicationContext(),rmeSelecionado.getIdUsuario(),Toast.LENGTH_LONG).show();
                try {
                    chamado.salvarChamado();

                    Intent intent = new Intent(chamadoActivity.this, PrincipalActivity.class);
                    startActivity(intent);

                }catch (Exception e){

                    //printar o erro ocorrido

                    System.err.println(e.toString());

                }
            }
        });


        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    local.setBackground(getDrawable(R.drawable.form_style_change));
                }

            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    data.setBackground(getDrawable(R.drawable.form_style_change));
                }

            }
        });
    }



}
