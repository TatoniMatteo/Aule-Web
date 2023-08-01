package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataItemImpl;
import java.util.Date;

public class EventoImpl extends DataItemImpl<Integer> implements Evento {

    private Integer id_ricorrenza;
    private String nome;
    private String descrizione;
    private Date data;
    private Integer ora_inizio;
    private Integer ora_fine;
    private Corso corso;
    private Responsabile responsabile;
    private Aula aula;
    private Tipo tipo_evento;

    public EventoImpl(Integer id_ricorrenza, String nome, String descrizione, Date data, Integer ora_inizio, Integer ora_fine, Corso corso, Responsabile responsabile, Aula aula, Tipo tipo) {
        super();
        this.id_ricorrenza = id_ricorrenza;
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.corso = corso;
        this.responsabile = responsabile;
        this.aula = aula;
        this.tipo_evento = tipo;
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

    public Integer getOra_inizio() {
        return ora_inizio;
    }

    public void setOra_inizio(Integer ora_inizio) {
        this.ora_inizio = ora_inizio;
    }

    public Integer getOra_fine() {
        return ora_fine;
    }

    public void setOra_fine(Integer ora_fine) {
        this.ora_fine = ora_fine;
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

    public Tipo getTipo_evento() {
        return tipo_evento;
    }

    public void setTipo_evento(Tipo tipo_evento) {
        this.tipo_evento = tipo_evento;
    }

}
