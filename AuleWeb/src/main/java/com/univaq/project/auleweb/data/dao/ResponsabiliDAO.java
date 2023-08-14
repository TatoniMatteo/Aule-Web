package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;

public interface ResponsabiliDAO {

    Responsabile importResponsabile();

    Responsabile getResponsabileById(int id) throws DataException;

    int getResponsabiliNumber() throws DataException;

}
