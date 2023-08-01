package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.implementation.enumType.Laurea;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.framework.data.DataItemImpl;

public class CorsoImpl extends DataItemImpl<Integer> implements Corso {

    private String nome;
    private String descrizione;
    private Laurea corso_laurea;

    public CorsoImpl(String nome, String descrizione, Laurea corso_laurea) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.corso_laurea = corso_laurea;
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

    public Laurea getCorso_laurea() {
        return corso_laurea;
    }

    public void setCorso_laurea(Laurea corso_laurea) {
        this.corso_laurea = corso_laurea;
    }

}
