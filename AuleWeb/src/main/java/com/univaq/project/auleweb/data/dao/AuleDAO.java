package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface AuleDAO {

    public Aula getAulaById(int id) throws DataException;

    public List<Aula> getAllAule() throws DataException;

    public List<Aula> getAuleByGruppoID(int id_gruppo) throws DataException;

}
