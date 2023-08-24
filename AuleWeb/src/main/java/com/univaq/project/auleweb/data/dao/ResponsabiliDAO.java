package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface ResponsabiliDAO {

    Responsabile importResponsabile();

    Responsabile getResponsabileById(int id) throws DataException;

    int getResponsabiliNumber() throws DataException;

    List<Responsabile> getAllResponsabili() throws DataException;

    List<Responsabile> getResponsabileByName( String filter) throws DataException;

}
