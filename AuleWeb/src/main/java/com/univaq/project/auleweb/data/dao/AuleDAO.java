package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface AuleDAO {

    Aula getAulaById(int id) throws DataException;

    List<Aula> getAllAule() throws DataException;

    List<Aula> getAuleByGruppoID(int id_gruppo) throws DataException;

    Aula importAula();

    List<Aula> getAuleByName(String filter) throws DataException;
}
