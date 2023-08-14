package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface EventiDAO {

    Evento importEvento();

    List<Evento> getAllEeventiNext3Hours() throws DataException;

    List<Evento> getEventiByAulaAndWeek(int aulaId, String input) throws DataException;

    List<Evento> getEventiByCorsoAndWeek(int corsoId, String input) throws DataException;

    Evento getEventoById(int eventoId) throws DataException;

    List<Evento> getEventiByCorsoAndDateRange(int corsoId, String dataInizio, String dataFine) throws DataException;

    List<Evento> getEventiByDateRange(String dataInizio, String dataFine) throws DataException;

    List<Evento> getEventiByGruppoIdAndDate(int gruppoId, String data) throws DataException;

    int getEventiNumber() throws DataException;
    
    int getActiveEventiNumber() throws DataException;

}
