package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataItemImpl;


public class AttrezzaturaImpl extends DataItemImpl<Integer> implements Attrezzatura {

    private String nome;
    private String numeroSerie;
    private Aula aula;

    public AttrezzaturaImpl() {
        this.nome="";
        this.numeroSerie="";
        this.aula=null;
    }
    

    public AttrezzaturaImpl(String nome, String numero_serie, Aula aula) {
        super();
        this.nome = nome;
        this.numeroSerie = numero_serie;
        this.aula = aula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

}
