package com.univaq.project.auleweb.data.implementation;

import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author taton
 */
public class DataLayerImpl extends DataLayer {

    public DataLayerImpl(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
        /*
        registerDAO(Amministratore.class, new AmministratoreDAO_MySQL(this));
        registerDAO(Attrezzatura.class, new AttrezzaturaDAO_MySQL(this));
        registerDAO(Aula.class, new AulaDAO_MySQL(this));
        registerDAO(Corso.class, new CorsoDAO_MySQL(this));
        registerDAO(Evento.class, new EventoDAO_MySQL(this));
        registerDAO(EventoRicorrente.class, new EventoRicorrenteDAO_MySQL(this));
        registerDAO(Gruppo.class, new GruppoDAO_MySQL(this));
        registerDAO(Responsabile.class, new ResponsabileDAO_MySQL(this));
         */
    }

    /*   
    public AmministratoreDAO getAmministratoreDAO() {
        return (AmministratoreDAO) getDAO(Amministratore.class);
    }

    public AttrezzaturaDAO getAttrezzaturaDAO() {
        return (AttrezzaturaDAO) getDAO(Attrezzatura.class);
    }

    public AulaDAO getAulaDAO() {
        return (AulaDAO) getDAO(Aula.class);
    }

    public CorsoDAO getCorsoDAO() {
        return (CorsoDAO) getDAO(Corso.class);
    }

    public EventoDAO getEventoDAO() {
        return (EventoDAO) getDAO(Evento.class);
    }

    public EventoRicorrenteDAO getEventoRicorrenteDAO() {
        return (EventoRicorrenteDAO) getDAO(EventoRicorrente.class);
    }

    public GruppoDAO getGruppoDAO() {
        return (GruppoDAO) getDAO(Gruppo.class);
    }

    public ResponsabileDAO getResponsabileDAO() {
        return (ResponsabileDAO) getDAO(Responsabile.class);
    }
     */
}
