package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataItemImpl;

public class AulaImpl extends DataItemImpl<Integer> implements Aula {

    private String nome;
    private String luogo;
    private String edficio;
    private Integer piano;
    private Integer capienza;
    private Integer prese_elettriche;
    private Integer prese_rete;
    private String note;
    private Responsabile responsabile;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getEdficio() {
        return edficio;
    }

    public void setEdficio(String edficio) {
        this.edficio = edficio;
    }

    public Integer getPiano() {
        return piano;
    }

    public void setPiano(Integer piano) {
        this.piano = piano;
    }

    public Integer getCapienza() {
        return capienza;
    }

    public void setCapienza(Integer capienza) {
        this.capienza = capienza;
    }

    public Integer getPrese_elettriche() {
        return prese_elettriche;
    }

    public void setPrese_elettriche(Integer prese_elettriche) {
        this.prese_elettriche = prese_elettriche;
    }

    public Integer getPrese_rete() {
        return prese_rete;
    }

    public void setPrese_rete(Integer prese_rete) {
        this.prese_rete = prese_rete;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
