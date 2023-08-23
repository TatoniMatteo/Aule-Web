package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface GruppiDAO {

    List<Gruppo> getAllGruppi() throws DataException;

    Gruppo getGruppoByID(int id) throws DataException;

    Gruppo importGruppo();

    List<Gruppo> getGruppiByAula(Integer key) throws DataException;

    void updateAula(List<Integer> keys, int aulaId) throws DataException;
}
