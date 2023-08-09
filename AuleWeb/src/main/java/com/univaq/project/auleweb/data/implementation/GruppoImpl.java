package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataItemImpl;

public class GruppoImpl extends DataItemImpl<Integer> implements Gruppo {

    private String nome;
    private String descrizione;
    private Categoria categoria;

    public GruppoImpl(String nome, String descrizione) {
        super();
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public GruppoImpl() {
        super();
        this.nome = "";
        this.descrizione = "";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
