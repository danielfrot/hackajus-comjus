package com.example.root.appmultitec.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.example.root.appmultitec.Adapter.AdapterChamado;
import com.example.root.appmultitec.Helper.ConfiguracaoFirebase;
import com.example.root.appmultitec.Helper.RecyclerItemClickListener;
import com.example.root.appmultitec.Model.Chamado;
import com.example.root.appmultitec.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChamadoListActivity extends AppCompatActivity implements Serializable {

    Intent intent;
    private RecyclerView recyclerChamado;
    private List<Chamado> chamados = new ArrayList<>();
    private AdapterChamado adapterChamado;
    private DatabaseReference chamadoUsuarioRef;



    //button navigation

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    intent = new Intent(ChamadoListActivity.this,PrincipalActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_dashboard:

                    intent = new Intent(ChamadoListActivity.this,FormRmeActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_notifications:

                    intent = new Intent(ChamadoListActivity.this,chamadoActivity.class);
                    startActivity(intent);


                    return true;

                case R.id.navigation_listaChamado:

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado_list);
        overridePendingTransition(0,0);
        chamadoUsuarioRef = ConfiguracaoFirebase.getFirebase()
                .child("chamado")
                .child(ConfiguracaoFirebase.getIdUsuario());


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_listaChamado);



        recyclerChamado = findViewById(R.id.recyclerViewChamado);

        recyclerChamado.setLayoutManager(new LinearLayoutManager(this));
        recyclerChamado.setHasFixedSize(true);
        adapterChamado = new AdapterChamado(chamados,this);
        recyclerChamado.setAdapter(adapterChamado);
        recuperarChamado();


        //foi alterado na faculdade prestar atenção nesse bloco de codigo kkkkk

        //adicionar um evento de click ao recicleview
        recyclerChamado.addOnItemTouchListener(new RecyclerItemClickListener(ChamadoListActivity.this, recyclerChamado, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Chamado DetalheChamado = chamados.get(position);


                Intent intent = new Intent(ChamadoListActivity.this,DetalheChamadoActivity.class);
                intent.putExtra("chamadoDetalhe",DetalheChamado);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

                alertaExcuirChamado(position);


            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));
    }


    private void alertaExcuirChamado(final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Excluir Chamado");
        builder.setMessage("Tem certeza que deseja Excluir esse Chamado");
        builder.setCancelable(true);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Chamado chamadoSelecionado = chamados.get(position);

                chamadoSelecionado.remover();
                adapterChamado.notifyDataSetChanged();

            }
        });


        AlertDialog dialog = builder.create();

        dialog.show();

    }


        //fim do codigo alterado na faculdade



    private void recuperarChamado(){

        chamadoUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chamados.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){


                    chamados.add(ds.getValue(Chamado.class));

                }

                Collections.reverse(chamados);
                adapterChamado.notifyDataSetChanged();
                //dialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}
