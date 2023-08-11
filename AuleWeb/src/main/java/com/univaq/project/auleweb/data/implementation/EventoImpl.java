package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataItemImpl;
import java.sql.Time;
import java.util.Date;

public class EventoImpl extends DataItemImpl<Integer> implements Evento {

    private Integer id_ricorrenza;
    private String nome;
    private String descrizione;
    private Date data;
    private Time oraInizio;
    private Time oraFine;
    private Corso corso;
    private Responsabile responsabile;
    private Aula aula;
    private Tipo tipoEvento;

    public EventoImpl() {
        this.id_ricorrenza = null;
        this.nome = "";
        this.descrizione = "";
        this.data = null;
        this.oraInizio = null;
        this.oraFine = null;
        this.corso = null;
        this.responsabile = null;
        this.aula = null;
        this.tipoEvento = null;

    }

    public EventoImpl(Integer id_ricorrenza, String nome, String descrizione, Date data, Time ora_inizio, Time ora_fine, Corso corso, Responsabile responsabile, Aula aula, Tipo tipo) {
        super();
        this.id_ricorrenza = id_ricorrenza;
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.oraInizio = ora_inizio;
        this.oraFine = ora_fine;
        this.corso = corso;
        this.responsabile = responsabile;
        this.aula = aula;
        this.tipoEvento = tipo;
    }

    public Integer getId_ricorrenza() {
        return id_ricorrenza;
    }

    public void setId_ricorrenza(Integer id_ricorrenza) {
        this.id_ricorrenza = id_ricorrenza;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Time getOraFine() {
        return oraFine;
    }

    public void setOraFine(Time oraFine) {
        this.oraFine = oraFine;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public Responsabile getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Tipo getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(Tipo tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

}
