package com.example.root.appmultitec.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.appmultitec.Model.Chamado;
import com.example.root.appmultitec.R;

import java.util.List;

public class AdapterChamado extends RecyclerView.Adapter<AdapterChamado.MyviewHolder> {

    private List<Chamado> chamados;
    private Context context;

    public AdapterChamado(List<Chamado> chamados, Context context) {
        this.chamados = chamados;
        this.context = context;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chamado,parent,false);
        return new MyviewHolder(item);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        Chamado chamado = chamados.get(position);

        holder.local.setText(chamado.getLocal());
        holder.data.setText(chamado.getData());
       holder.hora.setText(chamado.getHora());
        holder.status.setText("aberto");//so para teste alterar depois
        holder.dificuldade.setText(chamado.getDificuldade());

    }

    @Override
    public int getItemCount() {
        return chamados.size();
    }


    public class MyviewHolder extends RecyclerView.ViewHolder{

        TextView local;
        TextView data;
        TextView hora;
        TextView status;
        TextView dificuldade;



        public MyviewHolder(View itemView) {
            super(itemView);


            local = itemView.findViewById(R.id.tviewLocal);
            data =  itemView.findViewById(R.id.tviewData);
            hora =  itemView.findViewById(R.id.tviewHora);
            status = itemView.findViewById(R.id.tviewstatus);
            dificuldade = itemView.findViewById(R.id.tviewDificuldade);

        }
    }


}
