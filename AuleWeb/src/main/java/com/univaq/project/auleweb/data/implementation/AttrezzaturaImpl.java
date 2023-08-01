/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataItemImpl;

/**
 *
 * @author david
 */
public class AttrezzaturaImpl extends DataItemImpl<Integer> implements Attrezzatura {

    private String nome;
    private Integer numero_serie;
    private Aula aula;

    public AttrezzaturaImpl(String nome, Integer numero_serie, Aula aula) {
        super();
        this.nome = nome;
        this.numero_serie = numero_serie;
        this.aula = aula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(Integer numero_serie) {
        this.numero_serie = numero_serie;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

}
