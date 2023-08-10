package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.implementation.enumType.Laurea;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.framework.data.DataItemImpl;

public class CorsoImpl extends DataItemImpl<Integer> implements Corso {

    private String nome;
    private String descrizione;
    private Laurea corsoLaurea;

    public CorsoImpl() {
        this.nome="";
        this.descrizione="";
        this.corsoLaurea=null;
    }
    
    

    public CorsoImpl(String nome, String descrizione, Laurea corso_laurea) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.corsoLaurea = corso_laurea;
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

    public Laurea getCorsoLaurea() {
        return corsoLaurea;
    }

    public void setCorsoLaurea(Laurea corsoLaurea) {
        this.corsoLaurea = corsoLaurea;
    }

}
