package com.univaq.project.auleweb.data.proxy;

import com.univaq.project.auleweb.data.implementation.AmministratoreImpl;
import com.univaq.project.framework.data.DataItemProxy;
import com.univaq.project.framework.data.DataLayer;

public class AmministratoreProxy extends AmministratoreImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;

    public AmministratoreProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        this.modified = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.modified = true;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
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
