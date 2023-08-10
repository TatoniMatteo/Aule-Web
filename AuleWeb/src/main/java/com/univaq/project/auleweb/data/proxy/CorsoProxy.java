package com.univaq.project.auleweb.data.proxy;

import com.univaq.project.auleweb.data.implementation.CorsoImpl;
import com.univaq.project.auleweb.data.implementation.enumType.Laurea;
import com.univaq.project.framework.data.DataItemProxy;
import com.univaq.project.framework.data.DataLayer;

public class CorsoProxy extends CorsoImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;

    public CorsoProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
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
    public void setCorsoLaurea(Laurea corso_laurea) {
        super.setCorsoLaurea(corso_laurea);
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
