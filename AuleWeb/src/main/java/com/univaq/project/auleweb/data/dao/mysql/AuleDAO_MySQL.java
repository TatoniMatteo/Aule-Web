/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataLayer;
import com.univaq.project.auleweb.data.dao.AulaDAO;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuleDAO_MySQL extends DAO implements AulaDAO {

    public AuleDAO_MySQL(DataLayer d) {
        super(d);
    }

    private PreparedStatement getAllAule, getAulaByID, getAuleByGruppo;

    public void init() throws DataException {

        try {
            super.init();
            getAllAule = connection.prepareStatement("SELECT * FROM Aula");
            getAulaByID = connection.prepareStatement("SELECT * FROM Aula WHERE ID = ?");
            getAuleByGruppo = connection.prepareStatement("SELECT A.* FROM Aula A, Aula_gruppo AG, Gruppo G WHERE "
                    + "G.ID = ? AND AG.ID_gruppo = G.ID AND A.ID = AG.ID_aula");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DatLayer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            getAllAule.close();
            getAulaByID.close();
            getAuleByGruppo.close();
            super.destroy();
        } catch (SQLException ex) {
            throw new DataException("Errore durante la chiusura del DatLayer", ex);
        }

    }

    @Override
    public List<Aula> getAllAule() throws DataException {
        List<Aula> aule = new ArrayList<>();
        try ( ResultSet rs = getAllAule.executeQuery()) {
            while (rs.next()) {
                //aule.add(createAula(rs));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Aula dal nome e dalla posizione digitati", ex);
        }
        return aule;
    }

    public Aula getAulaById(int id) throws DataException {
        Aula aula = null;
        try {

            getAulaByID.setInt(1, id);

            try ( ResultSet rs = getAulaByID.executeQuery()) {
                if (rs.next()) {
                    //aula = createAula(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Aula da ID", ex);
        }
        return aula;
    }
    
    @Override
    public List<Aula> getAuleByGruppoID(int id_gruppo) throws DataException {
        List<Aula> aule = new ArrayList<>();
        try {
            getAuleByGruppo.setInt(1, id_gruppo);
        } catch (SQLException ex) {
            Logger.getLogger(AuleDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try ( ResultSet rs = getAuleByGruppo.executeQuery()) {
            while (rs.next()) {
                //Aula aula = createAula(rs);
                //aule.add(aula);
            }

        } catch (SQLException ex) {
            throw new DataException("error DB", ex);
        }

        return aule;
    }

}
