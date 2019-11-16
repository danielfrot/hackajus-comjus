package com.example.root.appmultitec.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.appmultitec.Helper.ConfiguracaoFirebase;
import com.example.root.appmultitec.R;
import com.example.root.appmultitec.Model.Rme;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 01/05/19.
 */

public class AdapterRme extends RecyclerView.Adapter<AdapterRme.MyViewHolder> {

    private List<Rme> rmes;
    private Context context;

    public AdapterRme(List<Rme> rme, Context context) {
        this.rmes = rme;
        this.context = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_rme,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Rme rme = rmes.get(position);
        holder.titulo.setText(rme.getLocal());
        holder.nome.setText(rme.getTecnico());
        holder.situacao.setText(rme.getSituacao());
        holder.data.setText(rme.getData());


        //pegar imagem

        List<String> urlFotos = rme.getFotos();

        //String urlCapa = urlFotos.get(0);
        String urlCapa = rme.getFotoUsuario();
        //String urlCapa = "https://firebasestorage.googleapis.com/v0/b/appmultitec-cf564.appspot.com/o/usuarios%2FWhatsApp%20Image%202019-11-15%20at%2022.09.00.jpeg?alt=media&token=46c807f1-e927-4f7a-9a82-2f2e9856c1fa";

        //Toast.makeText(context.getApplicationContext(),ConfiguracaoFirebase.getIdUsuario(),Toast.LENGTH_LONG).show();

        Picasso.get().load(urlCapa).into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return rmes.size();
    }

    public  class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView titulo;
        TextView nome;
        TextView data;
        TextView situacao;
        ImageView foto;

        public MyViewHolder(View itemView){

            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            nome = itemView.findViewById(R.id.textNome);
            foto = itemView.findViewById(R.id.imageRme);
            situacao = itemView.findViewById(R.id.textSituaco);
            data = itemView.findViewById(R.id.textData);
        }



    }

}
