package com.univaq.project.auleweb.data.proxy;

import com.univaq.project.auleweb.data.dao.AuleDAO;
import com.univaq.project.auleweb.data.implementation.AttrezzaturaImpl;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataItemProxy;
import com.univaq.project.framework.data.DataLayer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttrezzaturaProxy extends AttrezzaturaImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;

    protected Integer id_aula;

    public AttrezzaturaProxy(DataLayer d) {
        super();
        this.modified = false;
        this.id_aula = 0;
        this.dataLayer = d;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public Aula getAula() {
        if (super.getAula() == null && this.id_aula > 0) {
            try {
                super.setAula(((AuleDAO) dataLayer.getDAO(Aula.class)).getAulaById(id_aula));
            } catch (DataException ex) {
                Logger.getLogger(AttrezzaturaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAula();
    }

    @Override
    public void setAula(Aula aula) {
        super.setAula(aula);
        if (aula != null) {
            this.id_aula = aula.getKey();
        } else {
            this.id_aula = 0;
        }

        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setNumeroSerie(String numero) {
        super.setNumeroSerie(numero);
        this.modified = true;
    }

    public void setId_aula(Integer id) {
        this.id_aula = id;
        super.setAula(null);
    }

    public int getId_aula() {
        return this.id_aula;
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
