package com.example.root.appmultitec.Model;

import com.example.root.appmultitec.Helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.List;

public class Rme implements Serializable {
    private String idRme;
    private String idUsuario;
    private String tecnico;
    private String local;
    private String data;
    private String descricao;
    private String situacao;
    private String fotoUsuario;

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private List<String> fotos;



    public String getIdRme() {
        return idRme;
    }

    public void setIdRme(String idRme) {
        this.idRme = idRme;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }



    public Rme() {

        DatabaseReference rmeRef = ConfiguracaoFirebase.getFirebase();
        rmeRef.child("problema");
        setIdRme(rmeRef.push().getKey());

    }

    public void salvar(){

        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference rmeRef = ConfiguracaoFirebase.getFirebase();
        rmeRef.child("problema")
        .child(idUsuario)
        .child(getIdRme())
        .setValue(this);

    }

    public void salvarAdmin(){

        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference rmeRef = ConfiguracaoFirebase.getFirebase();
        rmeRef.child("admin")
                .child("aberta") //foi alterado possivelmente pode dar errado
                .child(getIdRme())
                .setValue(this);
    }

    public void remover(){


        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference rmeRef = ConfiguracaoFirebase.getFirebase()
                .child("problema")
                .child(idUsuario)
                .child(getIdRme());

        rmeRef.removeValue();

        //removerAdmin();
    }

    public void removerAdmin(){


        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference rmeRef = ConfiguracaoFirebase.getFirebase();
        rmeRef.child("admin")
                .child("aberta") //foi alterado possivelmente pode dar errado
                .child(idUsuario)
                .child(getIdRme());


        rmeRef.removeValue();



    }

}
