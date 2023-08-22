package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface AuleDAO {

    Aula getAulaById(int id) throws DataException;

    List<Aula> getAllAule() throws DataException;

    List<Aula> getAuleByGruppoID(int id_gruppo) throws DataException;

    Aula importAula();

    List<Aula> getAuleByName(String filter) throws DataException;

    int getAuleNumber() throws DataException;

    int insertAula(Aula aula) throws DataException;

    void updateAula(Aula aula) throws DataException;

    void updateAttrezzatura(int aulaId, Attrezzatura attrezzatura) throws DataException;

    void assignGruppo(int aulaId, List<Integer> gruppiId) throws DataException;

    void removeAssignGruppo(int aulaId, List<Integer> gruppiId) throws DataException;
}
