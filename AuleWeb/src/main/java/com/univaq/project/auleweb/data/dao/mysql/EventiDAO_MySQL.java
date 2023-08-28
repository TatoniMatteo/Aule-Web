package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.EventiDAO;
import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.proxy.EventoProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventiDAO_MySQL extends DAO implements EventiDAO {

    private PreparedStatement getAllEeventiNext3Hours,
            getEventiByAulaAndWeek,
            getEventiByCorsoAndWeek,
            getEventoById,
            getEventiByCorsoAndDateRange,
            getEventiByDateRange,
            getEventiByGruppoIdAndDate,
            getEventiNumber,
            getActiveEventiNumber,
            removeOldAulaEventi;

    CallableStatement insertEventi, updateEvento;

    public EventiDAO_MySQL(DataLayer d) {
        super(d);

    }

    @Override
    public void init() throws DataException {

        try {
            super.init();
            getAllEeventiNext3Hours = connection.prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE (ADDTIME(data, ora_inizio) <= ADDTIME(NOW(), '03:00:00') AND ADDTIME(data, ora_inizio) >= NOW()) "
                    + "OR (ADDTIME(data, ora_fine) >= NOW() AND ADDTIME(data, ora_fine) <= ADDTIME(NOW(), '03:00:00')) "
                    + "ORDER BY ora_inizio"
            );
            getEventiByAulaAndWeek = connection.prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE data BETWEEN ? AND DATE_ADD(?, INTERVAL 6 DAY) AND id_aula = ? "
                    + "ORDER BY ora_inizio AND data"
            );

            getEventiByCorsoAndWeek = connection.prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE data BETWEEN ? AND DATE_ADD(?, INTERVAL 6 DAY) AND id_corso = ? "
                    + "ORDER BY data ASC, ora_inizio"
            );

            getEventoById = connection.prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE id = ? "
            );
            getEventiByCorsoAndDateRange = connection.prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE data BETWEEN ? AND ? AND id_corso = ? "
                    + "ORDER BY ora_inizio AND data"
            );

            getEventiByDateRange = connection.prepareStatement(
                    "SELECT * "
                    + "FROM evento "
                    + "WHERE data BETWEEN ? AND ? "
                    + "ORDER BY data ASC, ora_inizio"
            );

            getEventiByGruppoIdAndDate = connection.prepareStatement(
                    "SELECT evento.* "
                    + "FROM evento "
                    + "JOIN aula ON evento.id_aula = aula.id "
                    + "JOIN aula_gruppo ON aula_gruppo.id_aula = aula.id "
                    + "WHERE aula_gruppo.id_gruppo = ? AND evento.data = ? "
                    + "ORDER BY data ASC, ora_inizio"
            );

            getEventiNumber = connection.prepareStatement(
                    "SELECT COUNT(*) AS numero_eventi "
                    + "FROM evento "
                    + "WHERE data > CURRENT_DATE() OR (data = CURRENT_DATE() AND ora_fine >= CURRENT_TIME)"
            );

            getActiveEventiNumber = connection.prepareStatement(
                    "SELECT COUNT(*) AS numero_eventi "
                    + "FROM evento "
                    + "WHERE data = CURRENT_DATE() AND ora_inizio <= CURRENT_TIME AND ora_fine >= CURRENT_TIME"
            );

            removeOldAulaEventi = connection.prepareStatement("DELETE FROM evento WHERE id_aula = ? AND data < CURDATE();");

            updateEvento = connection.prepareCall("CALL modifica_evento(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            insertEventi = connection.prepareCall("CALL inserisci_eventi(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }

    }

    @Override
    public void destroy() throws DataException {
        try {
            getAllEeventiNext3Hours.close();
            getEventiByAulaAndWeek.close();
            getEventiByCorsoAndWeek.close();
            getEventoById.close();
            getEventiByCorsoAndDateRange.close();
            getEventiByDateRange.close();
            getEventiByGruppoIdAndDate.close();
            getEventiNumber.close();
            getActiveEventiNumber.close();
            removeOldAulaEventi.close();
            updateEvento.close();
            insertEventi.close();

        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
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
            e.setIdRicorrenza(rs.getInt("id_ricorrenza"));
            e.setData(rs.getDate("data"));
            e.setOraInizio(rs.getTime("ora_inizio"));
            e.setOraFine(rs.getTime("ora_fine"));
            e.setResponsabileId(rs.getInt("id_responsabile"));
            e.setCorsoId(rs.getInt("id_corso"));
            e.setAulaId(rs.getInt("id_aula"));
            Tipo selectedTipo = null;
            for (Tipo t : Tipo.values()) {
                if (t.toString().equals(rs.getString("tipo_evento"))) {
                    selectedTipo = t;
                    break;
                }
            }
            if (selectedTipo == null) {
                selectedTipo = Tipo.ALTRO;
            }
            e.setTipoEvento(selectedTipo);
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

    @Override
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
                    return null;
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

    @Override
    public List<Evento> getEventiByCorsoAndDateRange(int corsoId, String dataInizio, String dataFine) throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            getEventiByCorsoAndDateRange.setString(1, dataInizio);
            getEventiByCorsoAndDateRange.setString(2, dataFine);
            getEventiByCorsoAndDateRange.setInt(3, corsoId);
            try ( ResultSet rs = getEventiByCorsoAndDateRange.executeQuery()) {
                while (rs.next()) {
                    eventi.add(importEvento(rs));
                }
            }
            return eventi;
        } catch (SQLException ex) {
            throw new DataException("Impossibile scaricare gli eventi del corso con id "
                    + corsoId + " per il periodo che va dal " + dataInizio + " al " + dataFine, ex);
        }
    }

    @Override
    public List<Evento> getEventiByDateRange(String dataInizio, String dataFine) throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            getEventiByDateRange.setString(1, dataInizio);
            getEventiByDateRange.setString(2, dataFine);
            try ( ResultSet rs = getEventiByDateRange.executeQuery()) {
                while (rs.next()) {
                    eventi.add(importEvento(rs));
                }
            }
            return eventi;
        } catch (SQLException ex) {
            throw new DataException("Impossibile scaricare gli eventi del periodo che va dal "
                    + dataInizio + " al " + dataFine, ex);
        }
    }

    @Override
    public List<Evento> getEventiByGruppoIdAndDate(int gruppoId, String data) throws DataException {
        List<Evento> eventi = new ArrayList<>();
        try {
            getEventiByGruppoIdAndDate.setInt(1, gruppoId);
            getEventiByGruppoIdAndDate.setString(2, data);
            try ( ResultSet rs = getEventiByGruppoIdAndDate.executeQuery()) {
                while (rs.next()) {
                    eventi.add(importEvento(rs));
                }
            }
            return eventi;
        } catch (SQLException ex) {
            throw new DataException("Impossibile scaricare gli eventi per il gruppo con id "
                    + gruppoId + " per il giorno " + data, ex);
        }
    }

    @Override
    public int getEventiNumber() throws DataException {
        try ( ResultSet rs = getEventiNumber.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("numero_eventi");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile calcolare il numero di eventi", ex);
        }
    }

    @Override
    public int getActiveEventiNumber() throws DataException {
        try ( ResultSet rs = getActiveEventiNumber.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("numero_eventi");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile calcolare il numero di eventi", ex);
        }
    }

    @Override
    public void removeOldAulaEventi(int aulaId) throws DataException {
        try {
            removeOldAulaEventi.setInt(1, aulaId);
            removeOldAulaEventi.executeUpdate();

        } catch (SQLException ex) {
            throw new DataException("Impossibile calcolare il numero di eventi", ex);
        }
    }

    @Override
    public void insertEvento(Evento evento, int tipoRicorrenza, String fineRicorrenza) throws DataException {

        try {

            insertEventi.setString(1, evento.getNome());
            insertEventi.setString(2, evento.getDescrizione());
            insertEventi.setDate(3, evento.getData());
            insertEventi.setTime(4, evento.getOraInizio());
            insertEventi.setTime(5, evento.getOraFine());
            if (evento.getCorso() != null) {
                insertEventi.setInt(6, evento.getCorso().getKey());
            } else {
                insertEventi.setNull(6, Types.INTEGER);
            }
            insertEventi.setInt(7, evento.getResponsabile().getKey());
            insertEventi.setInt(8, evento.getAula().getKey());
            insertEventi.setString(9, evento.getTipoEvento().toString());
            insertEventi.setInt(10, tipoRicorrenza);
            if (tipoRicorrenza != 0) {
                insertEventi.setString(11, fineRicorrenza);
            } else {
                insertEventi.setDate(11, evento.getData());
            }
            insertEventi.execute();

        } catch (SQLException e) {
            throw new DataException("Errore durante l'inserimento dell'evento", e);
        }
    }

    @Override
    public void updateEvento(Evento evento, boolean tutti) throws DataException {
        try {

            // Aggiorniamo l'aula
            updateEvento.setInt(1, evento.getKey());
            updateEvento.setLong(2, evento.getVersion());
            updateEvento.setString(3, evento.getNome());
            updateEvento.setString(4, evento.getDescrizione());
            updateEvento.setString(5, evento.getData().toString());
            updateEvento.setTime(6, evento.getOraInizio());
            updateEvento.setTime(7, evento.getOraFine());
            if (evento.getCorso() != null) {
                updateEvento.setInt(8, evento.getCorso().getKey());
            } else {
                updateEvento.setNull(8, Types.INTEGER);
            }
            updateEvento.setInt(9, evento.getResponsabile().getKey());
            updateEvento.setInt(10, evento.getAula().getKey());
            updateEvento.setBoolean(11, tutti);
            updateEvento.execute();

        } catch (SQLException ex) {
            throw new DataException("Errore durante il rollback della transazione (eventi)", ex);
        }
    }

    @Override
    public void storeEvento(Evento evento, Boolean tutti, Integer tipoRicorrenza, String fineRicorrenza) throws DataException {
        if (evento.getKey() != null) {
            updateEvento(evento, tutti);
        } else {
            insertEvento(evento, tipoRicorrenza, fineRicorrenza);
        }
    }
}
