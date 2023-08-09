package com.univaq.project.auleweb.data.proxy;

import com.univaq.project.auleweb.data.dao.CategorieDAO;
import com.univaq.project.auleweb.data.implementation.GruppoImpl;
import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataItemProxy;
import com.univaq.project.framework.data.DataLayer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GruppoProxy extends GruppoImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;
    protected int categoriaId;

    public GruppoProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.categoriaId = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
        this.modified = true;
    }

    @Override
    public Categoria getCategoria() {
        if (super.getCategoria() == null && this.categoriaId > 0) {
            try {
                super.setCategoria(((CategorieDAO) dataLayer.getDAO(Categoria.class)).getCategoriaById(categoriaId));
            } catch (DataException ex) {
                Logger.getLogger(GruppoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getCategoria();
    }

    @Override
    public void setCategoria(Categoria categoria) {
        super.setCategoria(categoria);
        if (categoria != null) {
            this.categoriaId = categoria.getKey();
        } else {
            this.categoriaId = 0;
        }
        this.modified = true;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

}
