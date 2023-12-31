package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface AttrezzatureDAO {

    Attrezzatura importAttrezzatura();

    List<Attrezzatura> getAttrezzaturaByAula(int id) throws DataException;

    List<Attrezzatura> getAttrezzaturaDisponibile() throws DataException;

    int getAttrezzatureNumber() throws DataException;

    int getAttrezzatureDisponibiliNumber() throws DataException;

    List<Attrezzatura> getAllAttrezzature() throws DataException;

    Attrezzatura getAttrezzaturaByCode(String codice) throws DataException;

    List<Attrezzatura> getAttrezzatureByNameOrCode(String filter) throws DataException;

    Attrezzatura getAttrezzaturaById(int id) throws DataException;

    void updateAulaAttrezzatura(List<Integer> keys, int aulaId) throws DataException;

    Integer insertAttrezzatura(String nome, String codice) throws DataException;

    void deleteAttrezzaturaById(int attrezzaturaId, long versione) throws DataException;

    void removeAulaFromAttrezzature(int aulaId) throws DataException;

}
