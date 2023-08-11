package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.data.DataException;

public interface AmministratoriDAO {

    Amministratore importAmministratore();

    Amministratore getAmministratoreById(int id) throws DataException;

    Amministratore getAmministratoreByUsernamePassword(String username, String password) throws DataException;

}
