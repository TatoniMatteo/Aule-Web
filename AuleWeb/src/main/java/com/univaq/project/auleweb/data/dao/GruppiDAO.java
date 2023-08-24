package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface GruppiDAO {

    List<Gruppo> getAllGruppi() throws DataException;

    Gruppo getGruppoByID(int id) throws DataException;

    Gruppo importGruppo();

    List<Gruppo> getGruppiByAula(Integer key) throws DataException;

    List<Gruppo> getGruppiByName(String name, String filter) throws DataException;

    void updateAulaGruppo(List<Integer> keys, int aulaId) throws DataException;

    Integer insertGruppo(String nome, String descrizione, int idCategoria) throws DataException;

    void deleteGruppoById(int gruppoId) throws DataException;
}
