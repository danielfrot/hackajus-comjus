package com.example.root.appmultitec.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.root.appmultitec.R;
import com.example.root.appmultitec.Adapter.AdapterRme;
import com.example.root.appmultitec.Helper.ConfiguracaoFirebase;
import com.example.root.appmultitec.Helper.Permissoes;
import com.example.root.appmultitec.Helper.RecyclerItemClickListener;
import com.example.root.appmultitec.Model.Rme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PrincipalActivity extends AppCompatActivity {

 String[] permissoes = new String[]{

         Manifest.permission.READ_EXTERNAL_STORAGE
 };


 Intent intent;
 private RecyclerView recyclerRme;
 private List<Rme> rmes = new ArrayList<>();
 private AdapterRme adapterRme;
 private DatabaseReference rmeUsuarioRef;
    private DatabaseReference recuperaRME;
 Dialog dialog;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:


                    return true;
                case R.id.navigation_dashboard:

                    intent = new Intent(PrincipalActivity.this,FormRmeActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_notifications:

                    intent = new Intent(PrincipalActivity.this,chamadoActivity.class);
                    startActivity(intent);


                    return true;

                case R.id.navigation_listaChamado:

                    intent = new Intent(PrincipalActivity.this,ChamadoListActivity.class);
                    startActivity(intent);


                    return true;
            }
            return false;
        }
    };

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);



        return super.onCreateOptionsMenu(menu);

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                    ConfiguracaoFirebase.logOut();

                intent = new Intent(PrincipalActivity.this,LoginActivity.class);
                startActivity(intent);

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //ConfiguracaoFirebase.getFirebaseAutenticacao().signOut();

        overridePendingTransition(0,0);
        Permissoes.validarPermissoes(permissoes,this,1);
        rmeUsuarioRef = ConfiguracaoFirebase.getFirebase()
                .child("problema")
                .child(ConfiguracaoFirebase.getIdUsuario());


                //dialog para carregar as fotos
      /*  dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialog.show();*/



        inicializarComponentes();

        recyclerRme.setLayoutManager(new LinearLayoutManager(this));
        recyclerRme.setHasFixedSize(true);
        adapterRme = new AdapterRme(rmes,this);
        recyclerRme.setAdapter(adapterRme);

        recuperarRme();

        //adicionar um evento de click ao recicleview
        recyclerRme.addOnItemTouchListener(new RecyclerItemClickListener(PrincipalActivity.this, recyclerRme, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Rme rmeAtual = rmes.get(position);

                Intent intent = new Intent(PrincipalActivity.this,DetalhesRmeActivity.class);
                intent.putExtra("rmeAtual",rmeAtual);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

                alertaExcuirRme(position);


            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));
    }

    private void recuperarRme(){


        recuperaRME = ConfiguracaoFirebase.getFirebase()
                .child("admin")
                .child("aberta");

       recuperaRME.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rmes.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    //Toast.makeText(getApplicationContext(),,Toast.LENGTH_LONG).show();


                        rmes.add(ds.getValue(Rme.class));

                }

                Collections.reverse(rmes);
                adapterRme.notifyDataSetChanged();
                //dialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void inicializarComponentes(){

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerRme = findViewById(R.id.recycleRme);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int ResultadoPermissao: grantResults){


           if(ResultadoPermissao == PackageManager.PERMISSION_DENIED){

               alertaValidaçãoPermissao();

           }


            }


        }


        private void alertaValidaçãoPermissao(){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("PermissoesNegadas");
            builder.setMessage("Para Utilizar o app e necessario aceitar as permissoes");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finish();

                }
            });


            AlertDialog dialog = builder.create();

            dialog.show();

        }



    private void alertaExcuirRme(final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Excluir Requisição de material");
        builder.setMessage("Tem certeza que deseja Excluir essa requisição?");
        builder.setCancelable(true);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Rme rmeselecionada = rmes.get(position);

                rmeselecionada.remover();
                adapterRme.notifyDataSetChanged();

            }
        });


        AlertDialog dialog = builder.create();

        dialog.show();

    }

}

