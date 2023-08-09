/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import java.util.List;

/**
 *
 * @author david
 */
public interface AulaDAO {
    
    public Aula getAulaById(int id) throws DataException;
    
     public List<Aula> getAllAule() throws DataException;
     
     public List<Aula> getAuleByGruppoID(int id_gruppo) throws DataException;
    
    
    
}
