package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.ResponsabiliDAO;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.auleweb.data.proxy.ResponsabileProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResponsabiliDAO_MySQL extends DAO implements ResponsabiliDAO {

    private PreparedStatement getResponsabileByID, getResponsabiliNumber, getAllResponsabili, getResponsabileByName;

    public ResponsabiliDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            getResponsabileByID = this.connection.prepareStatement("SELECT * FROM responsabile WHERE ID=?");
            getResponsabiliNumber = this.connection.prepareStatement("SELECT COUNT(*) AS numero_responsabili FROM responsabile");
            getAllResponsabili = this.connection.prepareStatement("SELECT * FROM responsabile ORDER BY nome,cognome");
            getResponsabileByName = this.connection.prepareStatement("SELECT * FROM responsabile WHERE nome=? ORDER BY nome");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            getResponsabileByID.close();
            getResponsabiliNumber.close();
            getAllResponsabili.close();
            getResponsabileByName.close();

        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public Responsabile getResponsabileById(int id_responsabile) throws DataException {
        Responsabile responsabile = null;
        try {
            getResponsabileByID.setInt(1, id_responsabile);
            try ( ResultSet rs = getResponsabileByID.executeQuery()) {
                if (rs.next()) {
                    responsabile = importResponsabile(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il responsabile da ID", ex);
        }
        return responsabile;
    }

    @Override
    public Responsabile importResponsabile() {
        return new ResponsabileProxy(this.getDataLayer());
    }

    private ResponsabileProxy importResponsabile(ResultSet rs) throws DataException {
        ResponsabileProxy r = (ResponsabileProxy) this.importResponsabile();
        try {
            r.setKey(rs.getInt("ID"));
            r.setNome(rs.getString("nome"));
            r.setCognome(rs.getString("cognome"));
            r.setEmail(rs.getString("email"));
            r.setVersion(rs.getInt("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore nel DataLayer", ex);
        }

        return r;
    }

    @Override
    public int getResponsabiliNumber() throws DataException {
        try ( ResultSet rs = getResponsabiliNumber.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("numero_responsabili");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile calcolare il numero di responsabili", ex);
        }
    }

    @Override
    public List<Responsabile> getResponsabileByName(String filter) throws DataException {
        List<Responsabile> responsabili = new ArrayList<>();
        try {
            getResponsabileByName.setString(1, "%" + filter + "%");
            try ( ResultSet rs = getResponsabileByName.executeQuery()) {
                while (rs.next()) {
                    responsabili.add(importResponsabile(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i responsabili con questo filtro : '" + filter + "'", ex);
        }

        return responsabili;
    }

    @Override
    public List<Responsabile> getAllResponsabili() throws DataException {
        List<Responsabile> responsabili = new ArrayList<>();
        try {
            try ( ResultSet rs = getAllResponsabili.executeQuery()) {
                while (rs.next()) {
                    responsabili.add(importResponsabile(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista di responsabili", ex);
        }
        return responsabili;
    }

}
