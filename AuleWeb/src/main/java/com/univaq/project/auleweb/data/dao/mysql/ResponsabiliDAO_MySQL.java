package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.univaq.project.auleweb.data.dao.ResponsabiliDAO;
import com.univaq.project.auleweb.data.proxy.ResponsabileProxy;

public class ResponsabiliDAO_MySQL extends DAO implements ResponsabiliDAO {

    private PreparedStatement getResponsabileByID;

    public ResponsabiliDAO_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {
        try {
            super.init();
            getResponsabileByID = this.connection.prepareStatement("SELECT * FROM responsabile WHERE ID=?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            getResponsabileByID.close();

        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
        }
        super.destroy();
    }

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

}
