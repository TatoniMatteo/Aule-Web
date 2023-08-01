/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.univaq.project.auleweb.data.model;

import com.univaq.project.framework.data.DataItem;

public interface Responsabile extends DataItem<Integer> {

    public String getNome();

    public void setNome(String nome);

    public String getCognome();

    public void setCognome(String cognome);

    public String getCodice_fiscale();

    public void setCodice_fiscale(String codice_fiscale);

    public String getEmail();

    public void setEmail(String email);

}
