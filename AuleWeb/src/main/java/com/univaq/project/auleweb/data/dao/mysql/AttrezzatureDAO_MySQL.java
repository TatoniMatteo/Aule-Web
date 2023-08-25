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
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AttrezzatureDAO_MySQL extends DAO implements AttrezzatureDAO {

    private PreparedStatement getAttrezzaturaByAulaId, getAttrezzaturaDisponibile, getAttrezzatureNumber, getAttrezzatureDisponibiliNumber, getAllAttrezzature, getAttrezzatureByNameOrCode, getAttrezzaturaById, getAttrezzaturaByCode;
    private PreparedStatement deleteAttrezzaturaById;
    private PreparedStatement setAula, removeAulaFromAttrezzature;

    private PreparedStatement insertAttrezzatura;

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
            getAttrezzaturaByCode = connection.prepareStatement("SELECT * FROM attrezzatura WHERE nome=?");
            deleteAttrezzaturaById = this.connection.prepareStatement("DELETE FROM attrezzatura WHERE ID = ? AND versione=?");
            getAttrezzatureByNameOrCode = this.connection.prepareStatement("SELECT * FROM attrezzatura WHERE nome LIKE ? OR numero_serie LIKE ? ORDER BY nome");
            getAttrezzaturaById = this.connection.prepareStatement("SELECT * FROM Attrezzatura WHERE id=?");
            setAula = this.connection.prepareStatement("UPDATE attrezzatura SET id_aula=?, versione=? WHERE id=? AND versione=?");
            insertAttrezzatura = this.connection.prepareStatement("INSERT INTO attrezzatura (nome,numero_serie) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            removeAulaFromAttrezzature = this.connection.prepareStatement("UPDATE attrezzatura SET id_aula = NULL WHERE id_aula = ?;");

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
            getAttrezzaturaByCode.close();
            deleteAttrezzaturaById.close();
            getAllAttrezzature.close();
            getAttrezzaturaById.close();
            setAula.close();
            insertAttrezzatura.close();
            deleteAttrezzaturaById.close();
            removeAulaFromAttrezzature.close();

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

    public Attrezzatura getAttrezzaturaByCode(String codice) throws DataException {
        Attrezzatura attrezzatura = null;
        try {
            getAttrezzaturaByCode.setString(1, codice);

            try ( ResultSet rs = getAttrezzaturaByCode.executeQuery()) {
                if (rs.next()) {
                    attrezzatura = importAttrezzatura(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare l'attrezzatura con codice : " + codice, ex);
        }

        return attrezzatura;
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

    @Override
    public void updateAulaAttrezzatura(List<Integer> keys, int aulaId) throws DataException {
        boolean autocommit = false;

        try {
            autocommit = connection.getAutoCommit();
            if (autocommit) {
                connection.setAutoCommit(false);
            }
            List<Attrezzatura> currentAttrezzatura = getAttrezzaturaByAula(aulaId);
            List<Attrezzatura> newAttrezzatura = new ArrayList<>();
            for (Integer key : keys) {
                newAttrezzatura.add(getAttrezzaturaById(key));
            }

            for (Attrezzatura a1 : currentAttrezzatura) {
                boolean isOld = true;
                for (Attrezzatura a2 : newAttrezzatura) {
                    if (a1.equals(a2)) {
                        newAttrezzatura.remove(a2);
                        isOld = false;
                        break;
                    }
                }
                if (isOld) {
                    setAula.setNull(1, Types.INTEGER);
                    setAula.setLong(2, a1.getVersion() + 1);
                    setAula.setInt(3, a1.getKey());
                    setAula.setLong(4, a1.getVersion());
                    setAula.executeUpdate();
                }
            }

            for (Attrezzatura a : newAttrezzatura) {
                setAula.setInt(1, aulaId);
                setAula.setLong(2, a.getVersion() + 1);
                setAula.setInt(3, a.getKey());
                setAula.setLong(4, a.getVersion());
                setAula.executeUpdate();
            }

            if (autocommit) {
                connection.commit();
            }
        } catch (SQLException ex) {
            if (autocommit) try {
                connection.rollback();
            } catch (SQLException ex1) {
                throw new DataException("Errore durante l'esecuzione del rollback (attrezzature)", ex1);
            }
            throw new DataException("Impossibile aggiornare l'aula delle attrezzature indicate", ex);
        } finally {
            if (autocommit) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new DataException("Errore durante il ripristino dell'autocommit (attrezzature)", ex);
                }
            }
        }
    }

    @Override
    public Integer insertAttrezzatura(String nome, String codice) throws DataException {
        int attrezzaturaId = -1;
        try {
            insertAttrezzatura.setString(1, nome);
            insertAttrezzatura.setString(2, codice);
            insertAttrezzatura.executeUpdate();

            try ( ResultSet generatedKeys = insertAttrezzatura.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    attrezzaturaId = generatedKeys.getInt(1);
                }
            }
            return attrezzaturaId;

        } catch (SQLException ex) {
            throw new DataException("Impossibile aggiungere l'attrezzatura", ex);
        }

    }

    @Override
    public void deleteAttrezzaturaById(int attrezzaturaId, long versione) throws DataException {
        try {

            deleteAttrezzaturaById.setInt(1, attrezzaturaId);
            deleteAttrezzaturaById.setLong(2, versione);
            deleteAttrezzaturaById.execute();

        } catch (SQLException ex) {
            throw new DataException("Non è stato possibile eliminare l'attrezzatura con id: " + attrezzaturaId, ex);
        }
    }

    @Override
    public void removeAulaFromAttrezzature(int aulaId) throws DataException {
        try {
            removeAulaFromAttrezzature.setInt(1, aulaId);
            removeAulaFromAttrezzature.executeUpdate();

        } catch (SQLException ex) {
            throw new DataException("Non è stato possibile rimuovere l'aula con id: " + aulaId + " dalle attrezzature", ex);
        }
    }

}
