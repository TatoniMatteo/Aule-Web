package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataItemImpl;
import java.sql.Time;
import java.sql.Date;

public class EventoImpl extends DataItemImpl<Integer> implements Evento {

    private Integer idRicorrenza;
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
        this.idRicorrenza = null;
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
        this.idRicorrenza = id_ricorrenza;
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

    @Override
    public Integer getIdRicorrenza() {
        return idRicorrenza;
    }

    @Override
    public void setIdRicorrenza(Integer idRicorrenza) {
        this.idRicorrenza = idRicorrenza;
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
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public Date getData() {
        return data;
    }

    @Override
    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public Time getOraInizio() {
        return oraInizio;
    }

    @Override
    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    @Override
    public Time getOraFine() {
        return oraFine;
    }

    @Override
    public void setOraFine(Time oraFine) {
        this.oraFine = oraFine;
    }

    @Override
    public Corso getCorso() {
        return corso;
    }

    @Override
    public void setCorso(Corso corso) {
        this.corso = corso;
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
    public Aula getAula() {
        return aula;
    }

    @Override
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    @Override
    public Tipo getTipoEvento() {
        return tipoEvento;
    }

    @Override
    public void setTipoEvento(Tipo tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

}
