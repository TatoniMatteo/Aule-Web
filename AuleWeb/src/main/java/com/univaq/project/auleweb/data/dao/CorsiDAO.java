package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface CorsiDAO {

    Corso importCorso();

    Corso getCorsoById(int id) throws DataException;

    List<Corso> getAllCorsi() throws DataException;

    List<Corso> getCorsiByName(String nome) throws DataException;

    int getCorsiNumber() throws DataException;

}
