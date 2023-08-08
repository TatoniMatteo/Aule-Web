package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.framework.data.DataItemImpl;

public class CategoriaImpl extends DataItemImpl<Integer> implements Categoria {

    private String nome;

    public CategoriaImpl() {
        this.nome = "";
    }

    public CategoriaImpl(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
