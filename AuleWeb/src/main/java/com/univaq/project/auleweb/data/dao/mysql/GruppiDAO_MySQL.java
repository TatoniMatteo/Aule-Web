package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.GruppiDAO;
import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.auleweb.data.proxy.GruppoProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GruppiDAO_MySQL extends DAO implements GruppiDAO {

    public GruppiDAO_MySQL(DataLayer d) {
        super(d);
    }

    private PreparedStatement getAllGruppi, getGruppoByID, getGruppiByAula, getGruppiByName;
    private PreparedStatement removeAulaGruppo, insertAulaGruppo;
    private PreparedStatement insertGruppo, deleteGruppoById;

    public void init() throws DataException {

        try {
            super.init();
            getAllGruppi = connection.prepareStatement("SELECT * FROM Gruppo ORDER BY nome");
            getGruppoByID = connection.prepareStatement("SELECT * FROM Gruppo WHERE ID = ?");
            getGruppiByAula = connection.prepareStatement(
                    "SELECT g.id, g.nome, g.descrizione, g.id_categoria, g.versione FROM gruppo g JOIN aula_gruppo ag ON g.id = ag.id_gruppo WHERE ag.id_aula =  ?"
            );
            removeAulaGruppo = connection.prepareStatement("DELETE FROM aula_gruppo WHERE id_aula = ?");
            insertAulaGruppo = connection.prepareStatement("INSERT INTO aula_gruppo(id_aula, id_gruppo) values (?,?)");
            insertGruppo = connection.prepareStatement("INSERT INTO gruppo(nome,descrizione,id_categoria) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            deleteGruppoById = connection.prepareStatement("DELETE FROM gruppo WHERE ID=?");
            getGruppiByName = connection.prepareStatement("SELECT * FROM gruppo WHERE nome=? ORDER BY nome");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DatLayer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            getAllGruppi.close();
            getGruppoByID.close();
            getGruppiByAula.close();
            getGruppiByName.close();
            removeAulaGruppo.close();
            insertAulaGruppo.close();
            insertGruppo.close();
            deleteGruppoById.close();

            super.destroy();
        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
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
    public List<Gruppo> getGruppiByName(String filter)throws DataException{
        List<Gruppo> gruppi = new ArrayList<>();
        try {
            getGruppiByName.setString(1, "%" + filter + "%");
            try ( ResultSet rs = getGruppiByName.executeQuery()) {
                while (rs.next()) {
                    Gruppo gruppo = importGruppo(rs);
                    gruppi.add(gruppo);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i gruppi con questo filtro : '" + filter + "'", ex);
        }

        return gruppi;
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

    @Override
    public List<Gruppo> getGruppiByAula(Integer id) throws DataException {
        List<Gruppo> gruppi = new ArrayList<>();
        try {
            getGruppiByAula.setInt(1, id);
            try ( ResultSet rs = getGruppiByAula.executeQuery()) {
                while (rs.next()) {
                    Gruppo gruppo = importGruppo(rs);
                    gruppi.add(gruppo);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il gruppo", ex);
        }
        return gruppi;
    }

    @Override
    public void updateAulaGruppo(List<Integer> keys, int aulaId) throws DataException {
        boolean autocommit = false;

        try {
            autocommit = connection.getAutoCommit();
            if (autocommit) {
                connection.setAutoCommit(false);
            }

            removeAulaGruppo.setInt(1, aulaId);
            removeAulaGruppo.executeUpdate();

            for (Integer key : keys) {
                insertAulaGruppo.setInt(1, aulaId);
                insertAulaGruppo.setInt(2, key);
                insertAulaGruppo.executeUpdate();
            }

            if (autocommit) {
                connection.commit();
            }
        } catch (SQLException ex) {
            if (autocommit) try {
                connection.rollback();
            } catch (SQLException ex1) {
                throw new DataException("Errore durante l'esecuzione del rollback  (gruppi)", ex1);
            }
            throw new DataException("Impossibile aggiornare l'aula dei gruppi indicati", ex);
        } finally {
            if (autocommit) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new DataException("Errore durante il ripristino dell'autocommit  (gruppi)", ex);
                }
            }
        }
    }

    @Override
    public Integer insertGruppo(String nome, String descrizione, int idCategoria) throws DataException {
        int gruppoId = -1;
        try {
            insertGruppo.setString(1, nome);
            insertGruppo.setString(2, descrizione);
            insertGruppo.setInt(3, idCategoria);
            insertGruppo.executeUpdate();
            try ( ResultSet generatedKeys = insertGruppo.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    gruppoId = generatedKeys.getInt(1);
                }
            }
            return gruppoId;

        } catch (SQLException ex) {
            throw new DataException("Impossibile aggiungere il gruppo", ex);
        }

    }
    
    @Override
    public void deleteGruppoById(int gruppoId) throws DataException{
         try {

            deleteGruppoById.setInt(1, gruppoId);
            deleteGruppoById.execute();

        } catch (SQLException ex) {
            throw new DataException("Non è stato possibile eliminare il gruppo", ex);
        }
    }

}
