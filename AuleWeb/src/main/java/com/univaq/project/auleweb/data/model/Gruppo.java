package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

public interface Gruppo extends DataItem<Integer> {

    String getNome();

    void setNome(String nome);

    String getDescrizione();

    void setDescrizione(String descrizione);

    Categoria getCategoria();

    void setCategoria(Categoria categoria);

}
