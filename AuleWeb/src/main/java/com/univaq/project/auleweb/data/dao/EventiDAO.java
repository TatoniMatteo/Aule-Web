package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface EventiDAO {

    Evento importEvento();

    List<Evento> getAllEeventiNext3Hours() throws DataException;

    List<Evento> getEventiByAulaAndWeek(int id, String dataInizio, String dataFine);

    List<Evento> getEventiByAulaAndWeek(int id, String week);

}