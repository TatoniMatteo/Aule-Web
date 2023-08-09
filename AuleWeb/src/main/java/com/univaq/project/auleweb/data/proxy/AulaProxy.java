package com.univaq.project.auleweb.data.proxy;

import com.univaq.project.auleweb.data.implementation.AulaImpl;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataItemProxy;
import com.univaq.project.framework.data.DataLayer;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.univaq.project.auleweb.data.dao.ResponsabiliDAO;

public class AulaProxy extends AulaImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;
    protected int id_responsabile;

    public AulaProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.id_responsabile = 0;
    }

    @Override
    public Responsabile getResponsabile() {
        if (super.getResponsabile() == null && this.id_responsabile > 0) {
            try {
                super.setResponsabile(((ResponsabiliDAO) dataLayer.getDAO(Responsabile.class)).getResponsabileById(id_responsabile));
            } catch (DataException ex) {
                Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return super.getResponsabile();
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        super.setResponsabile(responsabile);
        if (responsabile != null) {
            this.id_responsabile = responsabile.getKey();
        } else {
            this.id_responsabile = 0;
        }

        this.modified = true;
    }

    public int getId_responsabile() {
        return id_responsabile;
    }

    public void setId_responsabile(int id_responsabile) {
        this.id_responsabile = id_responsabile;
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
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

}
