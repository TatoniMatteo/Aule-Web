package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

public interface Responsabile extends DataItem<Integer> {

    String getNome();

    void setNome(String nome);

    String getCognome();

    void setCognome(String cognome);

    String getEmail();

    void setEmail(String email);

}
