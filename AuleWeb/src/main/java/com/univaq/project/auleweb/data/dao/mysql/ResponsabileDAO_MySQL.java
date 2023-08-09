/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author david
 */
public class ResponsabileDAO_MySQL extends DAO implements ResponsabiliDAO {

    private PreparedStatement getResponsabileByID;

    public ResponsabileDAO_MySQL(DataLayer d) {
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
                    //responsabile = createResponsabile(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il responsabile da ID", ex);
        }
        return responsabile;
    }
  

}
