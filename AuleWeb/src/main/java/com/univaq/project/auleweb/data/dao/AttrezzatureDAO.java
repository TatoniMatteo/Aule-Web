
package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface AttrezzatureDAO {

    Attrezzatura importAttrezzatura();

    List<Attrezzatura> getAttrezzaturaByAula(int id) throws DataException;

    List<Attrezzatura> getAttrezzaturaDisponibile() throws DataException;

}
