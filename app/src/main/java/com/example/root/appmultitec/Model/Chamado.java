package com.example.root.appmultitec.Model;

import com.example.root.appmultitec.Helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Chamado implements Serializable {
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private String local;
    private String data;
    private String hora;
    private String descricao;
    private String idChamado;
    private String idRecebe;
    //geter vindo da rme
    public String getIdRecebe() {
        return idRecebe;
    }
    //seter vindo da rme
    public void setIdRecebe(String idRecebe) {
        this.idRecebe = idRecebe;
    }

    //geter vindo da rme
    public String getDificuldade() {
        return dificuldade;
    }
    //seter vindo da rme
    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    private String dificuldade;

    public Chamado(String local, String data, String hora, String descricao, String idChamado) {
        this.local = local;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.idChamado = idChamado;
    }

    public Chamado(String local, String idChamado) {

        this.local = local;
        this.idChamado = idChamado;

    }

    public String getIdChamado() {
        return idChamado;
    }

    @Exclude
    public void setIdChamado(String idChamado) {
        this.idChamado = idChamado;
    }

    public Chamado() {

        DatabaseReference rmeRef = ConfiguracaoFirebase.getFirebase();
        rmeRef.child("chamado");
        setIdChamado(rmeRef.push().getKey());

    }

    public String getLocal() {
        return local;
    }


    public void setLocal(String local) {
        this.local = local;
    }




    public void salvarChamado(){


        DatabaseReference rmeRef = ConfiguracaoFirebase.getFirebase();
        rmeRef.child("chamado")
                .child(idRecebe)
                .child(getIdChamado())
                .setValue(this);

    }

    public void remover(){


        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference rmeRef = ConfiguracaoFirebase.getFirebase()
                .child("problema")
                .child(idUsuario)
                .child(getIdChamado());

        rmeRef.removeValue();

        //removerAdmin();
    }


}
