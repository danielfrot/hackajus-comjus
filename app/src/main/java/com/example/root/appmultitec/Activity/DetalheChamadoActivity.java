package com.example.root.appmultitec.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.root.appmultitec.Model.Chamado;
import com.example.root.appmultitec.R;

import java.io.Serializable;

public class DetalheChamadoActivity extends AppCompatActivity  implements Serializable {


    private TextView hora,local,data,status,descricao;

    private Chamado chamadoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_chamado);
        inicializarComponentes();

        //botao back

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chamadoAtual = (Chamado) getIntent().getSerializableExtra("chamadoDetalhe");


        local.setText(chamadoAtual.getLocal());
        data.setText(chamadoAtual.getData());
        hora.setText(chamadoAtual.getHora());
        descricao.setText(chamadoAtual.getDescricao());
        status.setText("aberto");

    }



    public  void inicializarComponentes(){




        hora = findViewById(R.id.texthoraDetalheChamado);
        local = findViewById(R.id.textLocalDetalheChamado);
        data = findViewById(R.id.textDataDetalheChamado);
        status = findViewById(R.id.textStatusDetalheChamado);
        descricao = findViewById(R.id.textDescicaoDetalheChamado);


    }

}
