package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.CorsiDAO;
import com.univaq.project.auleweb.data.implementation.enumType.Laurea;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.proxy.CorsoProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CorsiDAO_MySQL extends DAO implements CorsiDAO {

    private PreparedStatement getAllCorsi, getCorsoById, getCorsiByName;

    public CorsiDAO_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {
        super.init();

        try {
            this.getCorsoById = this.connection.prepareStatement("SELECT * FROM Corso WHERE ID = ?");
            this.getAllCorsi = this.connection.prepareStatement("SELECT * FROM corso ORDER BY nome");
            this.getCorsiByName = this.connection.prepareStatement("SELECT * FROM Corso WHERE nome LIKE ? ORDER BY nome");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }

    }

    @Override
    public void destroy() throws DataException {
        try {
            getCorsoById.close();
            getAllCorsi.close();
            getCorsiByName.close();
        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public Corso importCorso() {
        return new CorsoProxy(this.getDataLayer());
    }

    private CorsoProxy importCorso(ResultSet rs) throws DataException {
        CorsoProxy c = (CorsoProxy) this.importCorso();
        try {
            c.setKey(rs.getInt("ID"));
            c.setNome(rs.getString("nome"));
            c.setDescrizione(rs.getString("descrizione"));

            for (Laurea l : Laurea.values()) {
                if (l.toString().equals(rs.getString("corso_laurea"))) {
                    c.setCorsoLaurea(l);
                    break;
                }
            }

            c.setVersion(rs.getInt("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore nel DataLayer", ex);
        }

        return c;
    }

    @Override
    public Corso getCorsoById(int id) throws DataException {
        Corso corso = null;
        try {
            getCorsoById.setInt(1, id);
            try ( ResultSet rs = getCorsoById.executeQuery()) {
                if (rs.next()) {
                    corso = importCorso(rs);
                    //e lo mettiamo anche nella cache
                    //and put it also in the cache
                    //dataLayer.getCache().add(Article.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricate il corso con l'ID: " + id, ex);
        }
        return corso;
    }

    @Override
    public List<Corso> getAllCorsi() throws DataException {
        List<Corso> corsi = new ArrayList<>();
        try {
            try ( ResultSet rs = getAllCorsi.executeQuery()) {
                while (rs.next()) {
                    corsi.add(importCorso(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i corsi", ex);
        }
        return corsi;
    }

    @Override
    public List<Corso> getCorsiByName(String filter) throws DataException {
        List<Corso> corsi = new ArrayList<>();
        try {
            getCorsiByName.setString(1, "%" + filter + "%");
            try ( ResultSet rs = getCorsiByName.executeQuery()) {
                while (rs.next()) {
                    Corso corso = importCorso(rs);
                    corsi.add(corso);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i corsi con questo filtro : '" + filter + "'", ex);
        }

        return corsi;
    }

}
