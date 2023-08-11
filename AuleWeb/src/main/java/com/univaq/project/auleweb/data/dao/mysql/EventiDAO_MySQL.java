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
import java.util.ArrayList;
import java.util.List;

public class EventiDAO_MySQL extends DAO implements EventiDAO {

    private PreparedStatement getAllEeventiNext3Hours, getEventiByAulaAndWeek1, getEventiByAulaAndWeek2;

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
            getEventiByAulaAndWeek1 = this.dataLayer.getConnection().prepareStatement("");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }

    }

    public void destroy() throws DataException {
        try {
            getAllEeventiNext3Hours.close();

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
            //e.getData(rs.getInt("id_ricorrenza"));

            //COMPLETARE
            for (Tipo t : Tipo.values()) {
                if (t.toString().equals(rs.getString("tipo_evento"))) {
                    e.setTipo_evento(t);
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

    @Override
    public List<Evento> getEventiByAulaAndWeek(int id, String dataInizio, String dataFine) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiByAulaAndWeek(int id, String week) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
