package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataItemImpl;

public class AulaImpl extends DataItemImpl<Integer> implements Aula {

    private String nome;
    private String luogo;
    private String edificio;
    private Integer piano;
    private Integer capienza;
    private Integer preseElettriche;
    private Integer preseRete;
    private String note;
    private Responsabile responsabile;

    public AulaImpl() {
        super();
        this.nome = "";
        this.luogo = "";
        this.edificio = "";
        this.piano = 0;
        this.capienza = 0;
        this.preseElettriche = 0;
        this.preseRete = 0;
        this.note = "";
        this.responsabile = null;

    }

    public AulaImpl(String nome, String luogo, String edificio, Integer piano, Integer capienza, Integer prese_elettriche, Integer prese_rete, String note, Responsabile responsabile) {
        super();
        this.nome = nome;
        this.luogo = luogo;
        this.edificio = edificio;
        this.piano = piano;
        this.capienza = capienza;
        this.preseElettriche = prese_elettriche;
        this.preseRete = prese_rete;
        this.note = note;
        this.responsabile = responsabile;
    }

    public Responsabile getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

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

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
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

    public Integer getPreseElettriche() {
        return preseElettriche;
    }

    public void setPreseElettriche(Integer preseElettriche) {
        this.preseElettriche = preseElettriche;
    }

    public Integer getPreseRete() {
        return preseRete;
    }

    public void setPreseRete(Integer preseRete) {
        this.preseRete = preseRete;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
