package com.example.root.appmultitec.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.root.appmultitec.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FotoActivity extends AppCompatActivity {

    private String fotoSlecionada;
    private ImageView fotoFull;
    Button botaoVoltar;
    PhotoViewAttacher myattacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);

        fotoFull = findViewById(R.id.imageViewFull);
        fotoSlecionada =(String) getIntent().getSerializableExtra("fotoExibir");
        Picasso.get().load(fotoSlecionada).into(fotoFull);

        myattacher = new PhotoViewAttacher(fotoFull);
        myattacher.update();
        botaoVoltar = findViewById(R.id.btVoltar);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
