package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.dao.CategorieDAO;
import com.univaq.project.auleweb.data.dao.GruppiDAO;
import com.univaq.project.auleweb.data.dao.mysql.CategorieDAO_MySQL;
import com.univaq.project.auleweb.data.dao.mysql.GruppiDAO_MySQL;
import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author taton
 */
public class DataLayerImpl extends DataLayer {

    public DataLayerImpl(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
        registerDAO(Categoria.class, new CategorieDAO_MySQL(this));
        registerDAO(Gruppo.class, new GruppiDAO_MySQL(this));
    }

    public CategorieDAO getCategorieDAO() {
        return (CategorieDAO) getDAO(Categoria.class);
    }

    public GruppiDAO getGruppiDAO() {
        return (GruppiDAO) getDAO(Gruppo.class);
    }
}
