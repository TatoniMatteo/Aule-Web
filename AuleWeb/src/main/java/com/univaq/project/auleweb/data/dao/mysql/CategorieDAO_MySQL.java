package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.CategorieDAO;
import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.auleweb.data.proxy.CategoriaProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO_MySQL extends DAO implements CategorieDAO {

    public CategorieDAO_MySQL(DataLayer d) {
        super(d);
    }

    private PreparedStatement getAllCategorie, getCategoriaByID;
    private PreparedStatement insertCategoria, deleteCategoriaById;

    public void init() throws DataException {

        try {
            super.init();
            getAllCategorie = connection.prepareStatement("SELECT * FROM Categoria");
            getCategoriaByID = connection.prepareStatement("SELECT * FROM Categoria WHERE ID = ?");
            insertCategoria = connection.prepareStatement("INSERT INTO categoria(nome) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            deleteCategoriaById = connection.prepareStatement("DELETE FROM categoria WHERE ID=? AND versione=?");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DatLayer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            getAllCategorie.close();
            getCategoriaByID.close();
            insertCategoria.close();
            super.destroy();
        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
        }

    }

    @Override
    public List<Categoria> getAllCategorie() throws DataException {
        List<Categoria> categorie = new ArrayList<>();

        try ( ResultSet rs = getAllCategorie.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = importCategoria(rs);
                categorie.add(categoria);
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le categorie", ex);
        }

        return categorie;
    }

    @Override
    public Categoria getCategoriaById(int id) throws DataException {
        Categoria categoria = null;
        try {
            getCategoriaByID.setInt(1, id);
            try ( ResultSet rs = getCategoriaByID.executeQuery()) {
                if (rs.next()) {
                    categoria = importCategoria(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la categoria richiesta", ex);
        }
        return categoria;
    }

    @Override
    public Categoria importCategoria() {
        return new CategoriaProxy(getDataLayer());
    }

    private CategoriaProxy importCategoria(ResultSet rs) throws DataException {
        CategoriaProxy categoria = (CategoriaProxy) importCategoria();
        try {
            categoria.setKey(rs.getInt("ID"));
            categoria.setNome(rs.getString("nome"));
            categoria.setVersion(rs.getLong("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore l'importazione dell'oggetto Categoria", ex);
        }
        return categoria;
    }

    @Override
    public Integer insertCategoria(String nome) throws DataException {
        int categoriaId = -1;
        try {
            insertCategoria.setString(1, nome);
            insertCategoria.executeUpdate();
            
            try ( ResultSet generatedKeys = insertCategoria.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoriaId = generatedKeys.getInt(1);
                }
            }
            return categoriaId;

        } catch (SQLException ex) {
            throw new DataException("Impossibile aggiungere la categoria", ex);
        }
    }

    @Override
    public void deleteCategoriaById(int categoriaId, long versione) throws DataException {
         try {

            deleteCategoriaById.setInt(1, categoriaId);
            deleteCategoriaById.setLong(2, versione);
            deleteCategoriaById.execute();

        } catch (SQLException ex) {
            throw new DataException("Non è stato possibile eliminare la categoria", ex);
        }
    }

}
