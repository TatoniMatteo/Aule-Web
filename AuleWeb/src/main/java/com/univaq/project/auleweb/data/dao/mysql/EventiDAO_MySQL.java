package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.EventiDAO;
import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.proxy.EventoProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventiDAO_MySQL extends DAO implements EventiDAO {

    private PreparedStatement getAllEeventiNext3Hours, getEventiByAulaAndWeek, getEventiByCorsoAndWeek, getEventoById;

    public EventiDAO_MySQL(DataLayer d) {
        super(d);

    }

    public void init() throws DataException {

        try {
            super.init();
            getAllEeventiNext3Hours = this.dataLayer.getConnection().prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE (ADDTIME(data, ora_inizio) <= ADDTIME(NOW(), '03:00:00') AND ADDTIME(data, ora_inizio) >= NOW()) "
                    + "OR (ADDTIME(data, ora_fine) >= NOW() AND ADDTIME(data, ora_fine) <= ADDTIME(NOW(), '03:00:00')) "
                    + "ORDER BY ora_inizio"
            );
            getEventiByAulaAndWeek = this.dataLayer.getConnection().prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE data BETWEEN ? AND DATE_ADD(?, INTERVAL 6 DAY) AND id_aula = ? "
                    + "ORDER BY data AND ora_inizio"
            );

            getEventiByCorsoAndWeek = this.dataLayer.getConnection().prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE data BETWEEN ? AND DATE_ADD(?, INTERVAL 6 DAY) AND id_corso = ? "
                    + "ORDER BY data AND ora_inizio"
            );

            getEventoById = this.dataLayer.getConnection().prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE id = ? "
            );

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }

    }

    public void destroy() throws DataException {
        try {
            getAllEeventiNext3Hours.close();
            getEventiByAulaAndWeek.close();
            getEventiByCorsoAndWeek.close();
            getEventoById.close();

        } catch (SQLException ex) {

        }
        super.destroy();
    }

    @Override
    public Evento importEvento() {
        return new EventoProxy(this.getDataLayer());
    }

    private EventoProxy importEvento(ResultSet rs) throws DataException {
        EventoProxy e = (EventoProxy) this.importEvento();
        try {
            e.setKey(rs.getInt("ID"));
            e.setNome(rs.getString("nome"));
            e.setDescrizione(rs.getString("descrizione"));
            e.setId_ricorrenza(rs.getInt("id_ricorrenza"));
            e.setData(rs.getDate("data"));
            e.setOraInizio(rs.getTime("ora_inizio"));
            e.setOraFine(rs.getTime("ora_fine"));
            e.setResponsabileId(rs.getInt("id_responsabile"));
            e.setCorsoId(rs.getInt("id_corso"));
            e.setAulaId(rs.getInt("id_aula"));
            for (Tipo t : Tipo.values()) {
                if (t.toString().equals(rs.getString("tipo_evento"))) {
                    e.setTipoEvento(t);
                    break;
                }
            }
            e.setVersion(rs.getInt("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore nel DataLayer", ex);
        }
        return e;
    }

    @Override
    public List<Evento> getAllEeventiNext3Hours() throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            try ( ResultSet rs = getAllEeventiNext3Hours.executeQuery()) {
                while (rs.next()) {
                    eventi.add(importEvento(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare gli eventi per le prossime 3 ore", ex);
        }

        return eventi;
    }

    public List<Evento> getEventiByAulaAndWeek(int aulaId, String input) throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            LocalDate startDate;
            if (input.length() != 10) {
                // Presumiamo che sia una settimana nel formato "yyyy-Wxx"
                startDate = getStartOfWeek(input);
            } else {
                // Presumiamo che sia una data nel formato "yyyy-MM-dd"
                startDate = LocalDate.parse(input, DateTimeFormatter.ISO_DATE);
            }

            getEventiByAulaAndWeek.setInt(3, aulaId);
            getEventiByAulaAndWeek.setString(1, startDate.toString());
            getEventiByAulaAndWeek.setString(2, startDate.toString());
            try ( ResultSet rs = getEventiByAulaAndWeek.executeQuery()) {
                while (rs.next()) {
                    eventi.add(importEvento(rs));
                }
            }
        } catch (SQLException ex) {
            if (input.length() != 10) {
                throw new DataException("Impossibile caricare gli eventi per la settimana " + input + " per l'aula con id " + aulaId, ex);
            } else {
                throw new DataException("Impossibile caricare gli eventi per la settimana che inizia il " + input + " per l'aula con id " + aulaId, ex);
            }
        }

        return eventi;
    }

    @Override
    public List<Evento> getEventiByCorsoAndWeek(int corsoId, String input) throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            LocalDate startDate;
            if (input.length() != 10) {
                // Presumiamo che sia una settimana nel formato "yyyy-Wxx"
                startDate = getStartOfWeek(input);
            } else {
                // Presumiamo che sia una data nel formato "yyyy-MM-dd"
                startDate = LocalDate.parse(input, DateTimeFormatter.ISO_DATE);
            }

            getEventiByCorsoAndWeek.setInt(3, corsoId);
            getEventiByCorsoAndWeek.setString(1, startDate.toString());
            getEventiByCorsoAndWeek.setString(2, startDate.toString());
            try ( ResultSet rs = getEventiByCorsoAndWeek.executeQuery()) {
                while (rs.next()) {
                    eventi.add(importEvento(rs));
                }
            }
        } catch (SQLException ex) {
            if (input.length() != 10) {
                throw new DataException("Impossibile caricare gli eventi per la settimana " + input + " per il corso con id " + corsoId, ex);
            } else {
                throw new DataException("Impossibile caricare gli eventi per la settimana che inizia il " + input + " per il corso con id " + corsoId, ex);
            }
        }

        return eventi;
    }

    @Override
    public Evento getEventoById(int eventoId) throws DataException {
        try {
            getEventoById.setInt(1, eventoId);
            try ( ResultSet rs = getEventoById.executeQuery()) {
                if (rs.next()) {
                    return importEvento(rs);
                } else {
                    throw new SQLException("Elemento non trovato");
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile trovare l'evento con id " + eventoId, ex);
        }
    }

    private static LocalDate getStartOfWeek(String weekString) {
        String[] parts = weekString.split("-W");
        int year = Integer.parseInt(parts[0]);
        int week = Integer.parseInt(parts[1]);

        LocalDate firstDayOfWeek = LocalDate.of(year, 1, 1).with(java.time.temporal.TemporalAdjusters.firstInMonth(java.time.DayOfWeek.MONDAY));
        firstDayOfWeek = firstDayOfWeek.plusWeeks(week - 1);
        return firstDayOfWeek;
    }
}
