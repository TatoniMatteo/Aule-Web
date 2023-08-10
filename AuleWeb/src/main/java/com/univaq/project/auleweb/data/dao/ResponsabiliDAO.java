
package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;


public interface ResponsabiliDAO {
    
     Responsabile getResponsabileById(int id)  throws DataException;
    
     Responsabile importResponsabile(); 
    
}
