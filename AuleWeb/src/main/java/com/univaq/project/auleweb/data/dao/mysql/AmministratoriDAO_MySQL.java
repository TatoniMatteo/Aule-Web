package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.AmministratoriDAO;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.proxy.AmministratoreProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import com.univaq.project.framework.security.SecurityHelpers;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmministratoriDAO_MySQL extends DAO implements AmministratoriDAO {

    private PreparedStatement getAmministratoreById, getAmministratoreByUsername, getPasswordByUsername;

    public AmministratoriDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            getAmministratoreById = this.connection.prepareStatement("SELECT * FROM amministratore WHERE ID=?");
            getAmministratoreByUsername = this.connection.prepareStatement("SELECT id,username,email,versione FROM amministratore WHERE username=?");
            getPasswordByUsername = this.connection.prepareStatement("SELECT password FROM amministratore WHERE username=?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            getAmministratoreById.close();
            getAmministratoreByUsername.close();
            getPasswordByUsername.close();
        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement");
        }
        super.destroy();
    }

    @Override
    public Amministratore importAmministratore() {
        return new AmministratoreProxy(getDataLayer());
    }

    private AmministratoreProxy importAmministratore(ResultSet rs) throws DataException {
        AmministratoreProxy amministratore = (AmministratoreProxy) importAmministratore();
        try {
            amministratore.setKey(rs.getInt("ID"));
            amministratore.setUsername(rs.getString("username"));
            amministratore.setEmail(rs.getString("email"));
            amministratore.setVersion(rs.getInt("versione"));

        } catch (SQLException ex) {
            throw new DataException("Errore l'importazione dell'oggetto Amministratore", ex);
        }
        return amministratore;
    }

    @Override
    public Amministratore getAmministratoreById(int id) throws DataException {
        Amministratore amministratore = null;
        try {
            getAmministratoreById.setInt(1, id);

            try ( ResultSet rs = getAmministratoreById.executeQuery()) {
                if (rs.next()) {
                    amministratore = importAmministratore(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare l'amministratore con Id: " + id, ex);
        }
        return amministratore;
    }

    @Override
    public Amministratore getAmministratoreByUsername(String username) throws DataException {
        Amministratore amministratore = null;
        try {

            getAmministratoreByUsername.setString(1, username);
            try ( ResultSet rs = getAmministratoreByUsername.executeQuery()) {
                if (rs.next()) {
                    amministratore = importAmministratore(rs);
                }
            }
        } catch ( SQLException ex) {
            throw new DataException("Impossibile recuperare l'amministratore con username: " + username, ex);
        }
        return amministratore;
    }
    
    
    @Override
    public String getPasswordByUsername(String username) throws DataException {
       String password = null;
        try {

            getPasswordByUsername.setString(1, username);
            try ( ResultSet rs = getPasswordByUsername.executeQuery()) {
                if (rs.next()) {
                    password = rs.getString("password");
                
                }
            }
        } catch ( SQLException ex) {
            throw new DataException("Impossibile recuperare l'amministratore con username: " + username, ex);
        }
        return password;
    }
    

  
}
