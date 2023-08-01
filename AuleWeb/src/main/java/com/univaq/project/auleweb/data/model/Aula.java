/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

/**
 *
 * @author taton
 */
public interface Aula extends DataItem<Integer> {

    String getNome();

    void setNome(String nome);

    String getLuogo();

    void setLuogo(String luogo);

    String getEdficio();

    void setEdficio(String edficio);

    Integer getPiano();

    void setPiano(Integer piano);

    Integer getCapienza();

    void setCapienza(Integer capienza);

    Integer getPrese_elettriche();

    void setPrese_elettriche(Integer prese_elettriche);

    Integer getPrese_rete();

    void setPrese_rete(Integer prese_rete);

    String getNote();

    void setNote(String note);
}
