package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.data.DataItemImpl;

public class AmministratoreImpl extends DataItemImpl<Integer> implements Amministratore {

    private String username;
    private String email;

    public AmministratoreImpl() {
        this.username = "";
        this.email = "";
    }

    public AmministratoreImpl(String username, String email) {
        super();
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
