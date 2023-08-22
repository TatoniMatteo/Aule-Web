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

    @Override
    public Responsabile getResponsabile() {
        return responsabile;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getLuogo() {
        return luogo;
    }

    @Override
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    @Override
    public String getEdificio() {
        return edificio;
    }

    @Override
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    @Override
    public Integer getPiano() {
        return piano;
    }

    @Override
    public void setPiano(Integer piano) {
        this.piano = piano;
    }

    @Override
    public Integer getCapienza() {
        return capienza;
    }

    @Override
    public void setCapienza(Integer capienza) {
        this.capienza = capienza;
    }

    @Override
    public Integer getPreseElettriche() {
        return preseElettriche;
    }

    @Override
    public void setPreseElettriche(Integer preseElettriche) {
        this.preseElettriche = preseElettriche;
    }

    @Override
    public Integer getPreseRete() {
        return preseRete;
    }

    @Override
    public void setPreseRete(Integer preseRete) {
        this.preseRete = preseRete;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }

}
