package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.dao.AuleDAO;
import com.univaq.project.auleweb.data.dao.CategorieDAO;
import com.univaq.project.auleweb.data.dao.CorsiDAO;
import com.univaq.project.auleweb.data.dao.GruppiDAO;
import com.univaq.project.auleweb.data.dao.ResponsabiliDAO;
import com.univaq.project.auleweb.data.dao.mysql.AuleDAO_MySQL;
import com.univaq.project.auleweb.data.dao.mysql.CategorieDAO_MySQL;
import com.univaq.project.auleweb.data.dao.mysql.CorsiDAO_MySQL;
import com.univaq.project.auleweb.data.dao.mysql.GruppiDAO_MySQL;
import com.univaq.project.auleweb.data.dao.mysql.ResponsabiliDAO_MySQL;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DataLayerImpl extends DataLayer {

    public DataLayerImpl(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
        registerDAO(Categoria.class, new CategorieDAO_MySQL(this));
        registerDAO(Gruppo.class, new GruppiDAO_MySQL(this));
        registerDAO(Aula.class, new AuleDAO_MySQL(this));
        registerDAO(Responsabile.class, new ResponsabiliDAO_MySQL(this));
        registerDAO(Corso.class, new CorsiDAO_MySQL(this));
    }

    public CategorieDAO getCategorieDAO() {
        return (CategorieDAO) getDAO(Categoria.class);
    }

    public GruppiDAO getGruppiDAO() {
        return (GruppiDAO) getDAO(Gruppo.class);
    }

    public AuleDAO getAuleDAO() {
        return (AuleDAO) getDAO(Aula.class);
    }

    public ResponsabiliDAO getResponsabiliDAO() {
        return (ResponsabiliDAO) getDAO(Responsabile.class);
    }
    
    public CorsiDAO getCorsiDAO(){
        return (CorsiDAO) getDAO(Corso.class);
    }
}
