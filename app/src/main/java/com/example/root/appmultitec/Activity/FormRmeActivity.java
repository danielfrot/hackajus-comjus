package com.example.root.appmultitec.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.root.appmultitec.R;
import com.example.root.appmultitec.Helper.ConfiguracaoFirebase;
import com.example.root.appmultitec.Model.Rme;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;

public class FormRmeActivity extends AppCompatActivity implements View.OnClickListener {


    Intent intent;
    ImageView imagem1,imagem2,imagem3;
    private List<String> listaFotoRecuperada = new ArrayList<>();
    private List<String> listaUrlFotos = new ArrayList<>();
    private StorageReference storageReference;
    EditText tecnico,local,descricao;
    private String  fotoperfil;
    EditText data;

    Rme rme = new Rme();
    Button btenviar;
    AlertDialog dialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(FormRmeActivity.this,PrincipalActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_dashboard:



                    return true;
                case R.id.navigation_notifications:

                    intent = new Intent(FormRmeActivity.this,chamadoActivity.class);
                    startActivity(intent);

                    return true;

                case R.id.navigation_listaChamado:

                    intent = new Intent(FormRmeActivity.this,ChamadoListActivity.class);
                    startActivity(intent);


                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_rme);





        //esconder a transição das activity;
        overridePendingTransition(0,0);

        //esconder a action bar
       // getSupportActionBar().hide();
        //referencia do banco de dados
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();
        inicializarComponentes();

        //configuração do bottom Navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }


    public void inicializarComponentes(){


        imagem1 = findViewById(R.id.imageCadastro1);
        imagem2 = findViewById(R.id.imageCadastro2);
        imagem3 = findViewById(R.id.imageCadastro3);

        imagem1.setOnClickListener(this);
        imagem2.setOnClickListener(this);
        imagem3.setOnClickListener(this);

        tecnico = findViewById(R.id.edTecnico);
        local = findViewById(R.id.edLocal);
        descricao = findViewById(R.id.editDescricao);

        data =findViewById(R.id.editData);


        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(data,simpleMaskFormatter);
        data.addTextChangedListener(maskTextWatcher);





        btenviar = findViewById(R.id.btChamado);

        btenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAnucio();
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageCadastro1:
                escolherImagem(1);

                break;

            case R.id.imageCadastro2:
                escolherImagem(2);
                break;
            case R.id.imageCadastro3:
                escolherImagem(3);
                break;
        }

    }


    public void escolherImagem(int resquestCode){


        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,resquestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {

            Uri imagemselcionada = data.getData();
            String caminhoimagem = imagemselcionada.toString();

           // Toast.makeText(FormRmeActivity.this,"result ok",Toast.LENGTH_LONG).show();
            try {
                comprimirFotos(data.getData(),requestCode);
             //   Toast.makeText(FormRmeActivity.this,"try OK",Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                Toast.makeText(FormRmeActivity.this,e.toString(),Toast.LENGTH_LONG).show();

                e.printStackTrace();
            }
        }

    }

    public void comprimirFotos(Uri imagemSelecionada,int requestCode) throws IOException {


            File imagem = new File(getRealPathFromURI(imagemSelecionada));

            //"/storage/emulated/0/DCIM/Camera/IMG_20190501_204705609.jpg"


            File compressor = new Compressor(this).compressToFile(imagem.getAbsoluteFile());

        /**/
            Uri imagemselected = Uri.fromFile(compressor);


        if(requestCode == 1){

            imagem1.setImageURI(imagemselected);

        }else if(requestCode == 2){

            imagem2.setImageURI(imagemselected);

        }else if(requestCode == 3 ){

            imagem3.setImageURI(imagemselected);
        }

        listaFotoRecuperada.add(imagemselected.toString());
      //  Toast.makeText(FormRmeActivity.this,comprimirFotos(data.getData()).toString(),Toast.LENGTH_LONG).show();
/**/
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void salvarAnucio(){

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Salvando Dificuldade")
                .setCancelable(false)
                .build();
        dialog.show();

        for (int i =0; i< listaFotoRecuperada.size();i++){

            String urlImagem = listaFotoRecuperada.get(i);
            int tamanhoLista = listaFotoRecuperada.size();
           //alterado 15/11/2019
            if(urlImagem != null) {
                salvarFotoStorage(urlImagem, tamanhoLista, i);
            }
        }

    }



    public void recuperarImagemUsuario(){

        StorageReference storageRef = ConfiguracaoFirebase.getFirebaseStorage();
        String uidUsuario = ConfiguracaoFirebase.getIdUsuario();

        storageRef.child("usuarios")
        .child(uidUsuario+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        }



    public void salvarFotoStorage(String urlString, final int totalFotos, int contador){


        //criar a referencia do storage
        StorageReference imagemRme = storageReference.child("imagens")
                .child("rme")
                .child(rme.getIdRme())
                .child("imagem"+contador);


        //farzer upload das imagens

        UploadTask uploadTask = imagemRme.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String fotoLink = uri.toString();

                        listaUrlFotos.add(fotoLink);

                        if (totalFotos == listaUrlFotos.size()){


                            //tentando recuperar a foto do perfil

                            StorageReference storageRef = ConfiguracaoFirebase.getFirebaseStorage();
                            String uidUsuario = ConfiguracaoFirebase.getIdUsuario();

                            storageRef.child("usuarios")
                                    .child(uidUsuario+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    rme.setTecnico(tecnico.getText().toString());
                                    rme.setLocal(local.getText().toString());
                                    rme.setData(data.getText().toString());
                                    rme.setDescricao(descricao.getText().toString());
                                    rme.setFotos(listaUrlFotos);
                                    rme.setSituacao("aberta");
                                    rme.setIdUsuario(ConfiguracaoFirebase.getIdUsuario());
                                    //recuperarImagemUsuario();

                                    rme.setFotoUsuario(fotoperfil);
                                    rme.setFotoUsuario(uri.toString());


                                    //salvando no firebase
                                    rme.salvar();
                                    rme.salvarAdmin();

                                    //fechando o dialog quando finalizar o upload
                                    dialog.dismiss();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });




                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(FormRmeActivity.this,"falaha ao fazer Upload",Toast.LENGTH_LONG).show();


            }
        });



            }
}



