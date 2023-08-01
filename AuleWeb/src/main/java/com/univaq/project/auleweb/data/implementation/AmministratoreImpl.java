/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.data.DataItemImpl;

/**
 *
 * @author david
 */
public class AmministratoreImpl extends DataItemImpl<Integer> implements Amministratore {

    private String username;
    private String password;
    private String email;

    public AmministratoreImpl(String username, String password, String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
