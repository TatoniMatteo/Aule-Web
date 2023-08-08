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
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO_MySQL extends DAO implements CategorieDAO {

    public CategorieDAO_MySQL(DataLayer d) {
        super(d);
    }

    private PreparedStatement getAllCategorie, getCategoriaByID;

    public void init() throws DataException {

        try {
            super.init();
            getAllCategorie = connection.prepareStatement("SELECT * FROM Categoria");
            getCategoriaByID = connection.prepareStatement("SELECT * FROM Categoria WHERE ID = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del DatLayer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            getAllCategorie.close();
            getCategoriaByID.close();
            super.destroy();
        } catch (SQLException ex) {
            throw new DataException("Errore durante la chiusura del DatLayer", ex);
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
            throw new DataException("Errore creazione oggetto Gruppo", ex);
        }
        return categoria;
    }

}
