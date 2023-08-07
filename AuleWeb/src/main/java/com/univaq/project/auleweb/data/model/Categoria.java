package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

public interface Categoria extends DataItem<Integer> {

    String getNome();

    void setNome(String nome);
}
