package com.univaq.project.auleweb.data.proxy;

import com.univaq.project.auleweb.data.implementation.CategoriaImpl;
import com.univaq.project.framework.data.DataItemProxy;
import com.univaq.project.framework.data.DataLayer;

public class CategoriaProxy extends CategoriaImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;

    public CategoriaProxy(DataLayer d) {
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
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

}
