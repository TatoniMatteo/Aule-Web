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

    private PreparedStatement getAmministratoreById, getAmministratoreByUsernamePassword;

    public AmministratoriDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            getAmministratoreById = this.connection.prepareStatement("SELECT * FROM amministratore WHERE ID=?");
            getAmministratoreByUsernamePassword = this.connection.prepareStatement("SELECT id,username,email,versione FROM amministratore WHERE username=? AND password=?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            getAmministratoreById.close();
            getAmministratoreByUsernamePassword.close();
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
    public Amministratore getAmministratoreByUsernamePassword(String username, String password) throws DataException {
        Amministratore amministratore = null;
        try {
            password = SecurityHelpers.getPasswordHashSHA(password);
            getAmministratoreByUsernamePassword.setString(1, username);
            getAmministratoreByUsernamePassword.setString(2, password);
            try ( ResultSet rs = getAmministratoreByUsernamePassword.executeQuery()) {
                if (rs.next()) {
                    amministratore = importAmministratore(rs);
                }
            }
        } catch (NoSuchAlgorithmException | SQLException ex) {
            throw new DataException("Impossibile verificare le credenziali (username: " + username + ", password: " + password + ")", ex);
        }
        return amministratore;
    }
}
