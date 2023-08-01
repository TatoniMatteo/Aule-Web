/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

/**
 *
 * @author david
 */
public interface Amministratore extends DataItem<Integer> {

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getEmail();

    void setEmail(String email);

}
