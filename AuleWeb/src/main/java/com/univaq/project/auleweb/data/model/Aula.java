package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

public interface Aula extends DataItem<Integer> {

    Responsabile getResponsabile();

    void setResponsabile(Responsabile responsabile);

    String getNome();

    void setNome(String nome);

    String getLuogo();

    void setLuogo(String luogo);

    String getEdificio();

    void setEdificio(String edficio);

    Integer getPiano();

    void setPiano(Integer piano);

    Integer getCapienza();

    void setCapienza(Integer capienza);

    Integer getPreseElettriche();

    void setPreseElettriche(Integer prese_elettriche);

    Integer getPreseRete();

    void setPreseRete(Integer prese_rete);

    String getNote();

    void setNote(String note);
}
