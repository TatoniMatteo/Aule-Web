/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

/**
 *
 * @author david
 */
public interface Attrezzatura extends DataItem<Integer> {

    String getNome();

    void setNome(String nome);

    String getNumeroSerie();

    void setNumeroSerie(String numero_serie);

    Aula getAula();

    void setAula(Aula aula);

}
