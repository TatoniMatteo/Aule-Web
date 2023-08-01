/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataItemImpl;

/**
 *
 * @author david
 */
public class GruppoImpl extends DataItemImpl<Integer> implements Gruppo {

    private String nome;
    private String descrizione;

    public GruppoImpl(String nome, String descrizione) {
        super();
        this.nome = nome;
        this.descrizione = descrizione;
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

}
