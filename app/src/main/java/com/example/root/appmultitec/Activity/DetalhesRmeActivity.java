package com.example.root.appmultitec.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.appmultitec.R;
import com.example.root.appmultitec.Model.Rme;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.io.Serializable;

public class DetalhesRmeActivity extends AppCompatActivity implements Serializable {

    private TextView tecnico,local,data,status,descricao;
    private CarouselView carouselView;
    private Rme rmeSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_rme);


        inicializarComponentes();

        rmeSelecionado =(Rme) getIntent().getSerializableExtra("rmeAtual");



        if(rmeSelecionado != null){

            tecnico.setText(rmeSelecionado.getTecnico());
            local.setText(rmeSelecionado.getLocal());
            data.setText(rmeSelecionado.getData());
            status.setText(rmeSelecionado.getSituacao());
            descricao.setText(rmeSelecionado.getDescricao());

            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {

                    String urlString = rmeSelecionado.getFotos().get(position);
                    Picasso.get().load(urlString).into(imageView);
                }
            };

            carouselView.setPageCount(rmeSelecionado.getFotos().size());
            carouselView.setImageListener(imageListener);
            carouselView.setImageClickListener(new ImageClickListener() {
                @Override
                public void onClick(int position) {
                    //Toast.makeText(DetalhesRmeActivity.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();

                    String fotoSelecionadaFull = rmeSelecionado.getFotos().get(position);
                    Intent intent = new Intent(DetalhesRmeActivity.this,FotoActivity.class);
                    intent.putExtra("fotoExibir",fotoSelecionadaFull);
                    intent.putExtra("rmeAtual",rmeSelecionado);
                    startActivity(intent);


                  /*  String fotoSelecionadaFull = rmeSelecionado.getFotos().get(position);
                    PhotoFragment photoFragment = new PhotoFragment();
                    photoFragment.setFoto(fotoSelecionadaFull);
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.conteiner,photoFragment);
                    transaction.commit();
                                        */


                    /*getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new PhotoFragment())
                            .commit();
                    */
                }
            });

        }

    }

    public void chamarSolucao(View view){

        Intent intent = new Intent(DetalhesRmeActivity.this,chamadoActivity.class);
        intent.putExtra("rmeAtual",rmeSelecionado);
        startActivity(intent);

    }


    public void inicializarComponentes(){

        carouselView = findViewById(R.id.carouselView);
        tecnico = findViewById(R.id.textTecnicoDetalhe);
        local = findViewById(R.id.textLocalDetalhe);
        data = findViewById(R.id.textDataDetalhe);
        status = findViewById(R.id.textStatusDetalhe);
        descricao = findViewById(R.id.textDescicaoDetalhe);



    }

}
