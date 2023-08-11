package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.AuleDAO;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.proxy.AulaProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuleDAO_MySQL extends DAO implements AuleDAO {

    public AuleDAO_MySQL(DataLayer d) {
        super(d);
    }

    private PreparedStatement getAllAule, getAulaByID, getAuleByGruppo, getAuleByName;

    public void init() throws DataException {

        try {
            super.init();
            getAllAule = connection.prepareStatement("SELECT * FROM Aula ORDER BY nome");
            getAulaByID = connection.prepareStatement("SELECT * FROM Aula WHERE ID = ? ORDER BY nome");
            getAuleByGruppo = connection.prepareStatement("SELECT A.* FROM Aula A, Aula_gruppo AG, Gruppo G WHERE G.ID = ? AND AG.ID_gruppo = G.ID AND A.ID = AG.ID_aula ORDER BY A.nome");
            getAuleByName = connection.prepareStatement("SELECT * FROM Aula WHERE nome LIKE ?");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DatLayer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            getAllAule.close();
            getAulaByID.close();
            getAuleByGruppo.close();
            getAuleByName.close();
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
                aule.add(importAula(rs));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le Aula", ex);
        }
        return aule;
    }

    public Aula getAulaById(int id) throws DataException {
        Aula aula = null;
        try {
            getAulaByID.setInt(1, id);
            try ( ResultSet rs = getAulaByID.executeQuery()) {
                if (rs.next()) {
                    aula = importAula(rs);
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
            try ( ResultSet rs = getAuleByGruppo.executeQuery()) {
                while (rs.next()) {
                    Aula aula = importAula(rs);
                    aule.add(aula);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le aule di questo gruppo (ID gruppo: " + id_gruppo + ")", ex);
        }

        return aule;
    }

    @Override
    public Aula importAula() {
        return new AulaProxy(getDataLayer());
    }

    private AulaProxy importAula(ResultSet rs) throws DataException {
        AulaProxy aula = (AulaProxy) importAula();
        try {
            aula.setKey(rs.getInt("ID"));
            aula.setNome(rs.getString("nome"));
            aula.setLuogo(rs.getString("luogo"));
            aula.setEdificio(rs.getString("edificio"));
            aula.setPiano(rs.getInt("piano"));
            aula.setCapienza(rs.getInt("capienza"));
            aula.setPrese_rete(rs.getInt("prese_rete"));
            aula.setPrese_elettriche(rs.getInt("prese_elettriche"));
            aula.setNote(rs.getString("note"));
            aula.setId_responsabile(rs.getInt("id_responsabile"));
            aula.setVersion(rs.getLong("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore l'importazione dell'oggetto Aula", ex);
        }
        return aula;
    }

    @Override
    public List<Aula> getAuleByName(String filter) throws DataException {
        List<Aula> aule = new ArrayList<>();
        try {
            getAuleByName.setString(1, "%" + filter + "%");
            try ( ResultSet rs = getAuleByName.executeQuery()) {
                while (rs.next()) {
                    Aula aula = importAula(rs);
                    aule.add(aula);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le aule con questo filtro : '" + filter + "'", ex);
        }

        return aule;
    }

}
