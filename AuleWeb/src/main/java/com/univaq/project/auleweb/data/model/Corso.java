package com.univaq.project.auleweb.data.model;

import com.univaq.project.auleweb.data.implementation.enumType.Laurea;
import com.univaq.project.framework.data.DataItem;

public interface Corso extends DataItem<Integer> {

    String getNome();

    void setNome(String nome);

    String getDescrizione();

    void setDescrizione(String descrizione);

    Laurea getCorso_laurea();

    void setCorso_laurea(Laurea corso_laurea);
}
