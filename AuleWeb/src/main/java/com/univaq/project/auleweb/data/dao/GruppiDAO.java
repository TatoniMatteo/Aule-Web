package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface GruppiDAO {

    List<Gruppo> getAllGruppi() throws DataException;

    Gruppo getGruppoByID(int id) throws DataException;

    Gruppo importGruppo();

    List<Gruppo> getGruppiByAula(Integer key) throws DataException;

    List<Gruppo> getGruppiByName(String filter) throws DataException;

    Gruppo getGruppoByName(String nome) throws DataException;

    void updateAulaGruppo(List<Integer> keys, int aulaId) throws DataException;

    Integer insertGruppo(Gruppo gruppo) throws DataException;

    Integer updateGruppo(Gruppo gruppo) throws DataException;

    void deleteGruppoById(int gruppoId, long versione) throws DataException;

    Integer storeGruppo(Gruppo gruppo) throws DataException;
}
