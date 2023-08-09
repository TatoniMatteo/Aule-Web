package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.GruppiDAO;
import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.auleweb.data.proxy.CategoriaProxy;
import com.univaq.project.auleweb.data.proxy.GruppoProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GruppiDAO_MySQL extends DAO implements GruppiDAO {

    public GruppiDAO_MySQL(DataLayer d) {
        super(d);
    }

    private PreparedStatement getAllGruppi, getGruppoByID;

    public void init() throws DataException {

        try {
            super.init();
            getAllGruppi = connection.prepareStatement("SELECT * FROM Gruppo");
            getGruppoByID = connection.prepareStatement("SELECT * FROM Gruppo WHERE ID = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DatLayer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            getAllGruppi.close();
            getGruppoByID.close();
            super.destroy();
        } catch (SQLException ex) {
            throw new DataException("Errore durante la chiusura del DatLayer", ex);
        }

    }

    @Override
    public List<Gruppo> getAllGruppi() throws DataException {
        List<Gruppo> gruppi = new ArrayList<>();

        try ( ResultSet rs = getAllGruppi.executeQuery()) {
            while (rs.next()) {
                Gruppo gruppo = importGruppo(rs);
                gruppi.add(gruppo);
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il gruppo", ex);
        }

        return gruppi;
    }

    @Override
    public Gruppo getGruppoByID(int id) throws DataException {
        Gruppo gruppo = null;
        try {
            getGruppoByID.setInt(1, id);
            try ( ResultSet rs = getGruppoByID.executeQuery()) {
                if (rs.next()) {
                    gruppo = importGruppo(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i gruppi richiesti", ex);
        }
        return gruppo;
    }

    @Override
    public Gruppo importGruppo() {
        return new GruppoProxy(getDataLayer());
    }

    private GruppoProxy importGruppo(ResultSet rs) throws DataException {
        GruppoProxy gruppo = (GruppoProxy) importGruppo();
        try {
            gruppo.setKey(rs.getInt("ID"));
            gruppo.setNome(rs.getString("nome"));
            gruppo.setDescrizione(rs.getString("descrizione"));
            gruppo.setCategoriaId(rs.getInt("id_categoria"));
            gruppo.setVersion(rs.getLong("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'importazione dell' oggetto Gruppo", ex);
        }
        return gruppo;
    }

}
