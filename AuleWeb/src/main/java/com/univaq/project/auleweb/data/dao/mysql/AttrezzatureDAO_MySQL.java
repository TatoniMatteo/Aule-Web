package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.AttrezzatureDAO;
import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.proxy.AttrezzaturaProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttrezzatureDAO_MySQL extends DAO implements AttrezzatureDAO {

    private PreparedStatement getAttrezzaturaByAulaId, getAttrezzaturaDisponibile, getAttrezzatureNumber, getAttrezzatureDisponibiliNumber, getAllAttrezzature, getAttrezzatureByNameOrCode, getAttrezzaturaById;
    private PreparedStatement deleteAttrezzaturaById;

    public AttrezzatureDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            getAttrezzaturaByAulaId = this.connection.prepareStatement("SELECT * FROM Attrezzatura WHERE id_aula=?");
            getAttrezzaturaDisponibile = this.connection.prepareStatement("SELECT * FROM Attrezzatura WHERE id_aula IS NULL");
            getAttrezzatureNumber = this.connection.prepareStatement("SELECT COUNT(*) AS numero_attrezzature FROM Attrezzatura");
            getAttrezzatureDisponibiliNumber = this.connection.prepareStatement("SELECT COUNT(*) AS numero_attrezzature FROM Attrezzatura WHERE id_aula IS NULL");
            getAllAttrezzature = this.connection.prepareStatement("SELECT * FROM attrezzatura ORDER BY nome");
            deleteAttrezzaturaById = this.connection.prepareStatement("DELETE FROM attrezzatura WHERE ID = ?");
            getAttrezzatureByNameOrCode = this.connection.prepareStatement("SELECT * FROM attrezzatura WHERE nome LIKE ? OR numero_serie LIKE ? ORDER BY nome");
            getAttrezzaturaById = this.connection.prepareStatement("SELECT * FROM Attrezzatura WHERE id=?");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            getAttrezzaturaByAulaId.close();
            getAttrezzaturaDisponibile.close();
            getAttrezzatureNumber.close();
            getAttrezzatureDisponibiliNumber.close();
            getAllAttrezzature.close();
            deleteAttrezzaturaById.close();
            getAllAttrezzature.close();
            getAttrezzaturaById.close();

        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public Attrezzatura importAttrezzatura() {
        return new AttrezzaturaProxy(getDataLayer());
    }

    private AttrezzaturaProxy importAttrezzatura(ResultSet rs) throws DataException {
        AttrezzaturaProxy attrezzatura = (AttrezzaturaProxy) importAttrezzatura();
        try {
            attrezzatura.setKey(rs.getInt("ID"));
            attrezzatura.setId_aula(rs.getInt("id_aula"));
            attrezzatura.setNome(rs.getString("nome"));
            attrezzatura.setNumeroSerie(rs.getString("numero_serie"));
            attrezzatura.setVersion(rs.getInt("versione"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto Attrezzatura", ex);
        }
        return attrezzatura;
    }

    @Override
    public List<Attrezzatura> getAttrezzaturaByAula(int aulaId) throws DataException {
        List<Attrezzatura> attrezzature = new ArrayList<>();
        try {
            getAttrezzaturaByAulaId.setInt(1, aulaId);
            try ( ResultSet rs = getAttrezzaturaByAulaId.executeQuery()) {
                while (rs.next()) {
                    Attrezzatura attrezzatura = importAttrezzatura(rs);
                    attrezzature.add(attrezzatura);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le attrezzature per l'aula con ID : " + aulaId, ex);
        }

        return attrezzature;
    }

    @Override
    public List<Attrezzatura> getAttrezzaturaDisponibile() throws DataException {
        List<Attrezzatura> attrezzature = new ArrayList();
        try ( ResultSet rs = getAttrezzaturaDisponibile.executeQuery()) {
            while (rs.next()) {
                Attrezzatura attrezzatura = importAttrezzatura(rs);
                attrezzature.add(attrezzatura);

            }

        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista di attrezzature disponibili", ex);
        }
        return attrezzature;
    }

    @Override
    public int getAttrezzatureNumber() throws DataException {
        try ( ResultSet rs = getAttrezzatureNumber.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("numero_attrezzature");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile calcolare il numero di attrezzature", ex);
        }
    }

    @Override
    public int getAttrezzatureDisponibiliNumber() throws DataException {
        try ( ResultSet rs = getAttrezzatureDisponibiliNumber.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("numero_attrezzature");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile calcolare il numero di attrezzature", ex);
        }
    }

    @Override
    public List<Attrezzatura> getAllAttrezzature() throws DataException {
        List<Attrezzatura> attrezzature = new ArrayList<>();
        try ( ResultSet rs = getAllAttrezzature.executeQuery()) {
            while (rs.next()) {
                attrezzature.add(importAttrezzatura(rs));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le Attrezzature", ex);
        }
        return attrezzature;
    }

    @Override
    public void deleteAttrezzaturaById(int id) throws DataException {
        try {

            deleteAttrezzaturaById.setInt(1, id);
            deleteAttrezzaturaById.execute();

        } catch (SQLException ex) {
            throw new DataException("Non è stato possibile eliminare l'attrezzatura", ex);
        }
    }

    @Override
    public List<Attrezzatura> getAttrezzatureByNameOrCode(String filter) throws DataException {
        List<Attrezzatura> attrezzature = new ArrayList<>();
        try {
            getAttrezzatureByNameOrCode.setString(1, "%" + filter + "%");
            getAttrezzatureByNameOrCode.setString(2, "%" + filter + "%");
            try ( ResultSet rs = getAttrezzatureByNameOrCode.executeQuery()) {
                while (rs.next()) {
                    Attrezzatura attrezzatura = importAttrezzatura(rs);
                    attrezzature.add(attrezzatura);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le attrezzature con nome : " + filter, ex);
        }

        return attrezzature;
    }

    @Override
    public Attrezzatura getAttrezzaturaById(int id) throws DataException {
        Attrezzatura attrezzatura = null;
        try {
            getAttrezzaturaById.setInt(1, id);

            try ( ResultSet rs = getAttrezzaturaById.executeQuery()) {
                if (rs.next()) {
                    attrezzatura = importAttrezzatura(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare l'attrezzatura con Id: " + id, ex);
        }
        return attrezzatura;
    }

}
