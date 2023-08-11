package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

public interface Amministratore extends DataItem<Integer> {

    String getUsername();

    void setUsername(String username);

    String getEmail();

    void setEmail(String email);

}
