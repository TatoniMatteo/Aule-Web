/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;

/**
 *
 * @author david
 */
public interface ResponsabiliDAO {
    
    public Responsabile getResponsabileById(int id)  throws DataException;
    
}
