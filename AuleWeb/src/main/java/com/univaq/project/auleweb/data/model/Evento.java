package com.univaq.project.auleweb.data.model;

import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.framework.data.DataItem;
import java.util.Date;

public interface Evento extends DataItem<Integer> {

    Integer getId_ricorrenza();

    void setId_ricorrenza(Integer id_ricorrenza);

    String getNome();

    void setNome(String nome);

    String getDescrizione();

    void setDescrizione(String descrizione);

    Date getData();

    void setData(Date data);

    Integer getOra_inizio();

    void setOra_inizio(Integer ora_inizio);

    Integer getOra_fine();

    void setOra_fine(Integer ora_fine);

    Corso getCorso();

    void setCorso(Corso corso);

    Responsabile getResponsabile();

    void setResponsabile(Responsabile responsabile);

    Aula getAula();

    void setAula(Aula aula);

    Tipo getTipo_evento();

    void setTipo_evento(Tipo tipo_evento);
}
