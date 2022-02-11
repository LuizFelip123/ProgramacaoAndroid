package com.aula.trabalhofinal.bancodedados;


import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

import java.util.Objects;


@Entity
public class ContatoBanco {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name ="telefone")
    public String telefone;

    @ColumnInfo(name="endereco")
    public String endereco;

    public ContatoBanco(String endereco, String nome, String telefone) {
        this.endereco = endereco;
        this.telefone = telefone;
        this.nome = nome;
    }
    public ContatoBanco(){

    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public void setUid(int uid){this.uid = uid;}
    public int getUid() {
        return uid;
    }

    public String getEndereco() {
        return this.endereco;
    }
    public String getNome(){
        return this.nome;
    }
    public String getTelefone(){
        return this.telefone;
    }

    @Override
    public String toString() {
        return "Nome: "+this.nome+"\n"+"Telefone: "+this.telefone+"\n" +
                "Endereço:"+this.endereco+".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContatoBanco that = (ContatoBanco) o;
        return uid == that.uid;
    }

}
