package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.EventiDAO;
import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.proxy.EventoProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


//COMPLETARE
public class EventiDAO_MySQL extends DAO implements EventiDAO {

    private PreparedStatement getAllEeventiNext3Hours;

    public EventiDAO_MySQL(DataLayer d) {
        super(d);

    }

    public void init() throws DataException {

        try {
            super.init();
            getAllEeventiNext3Hours = this.dataLayer.getConnection().prepareStatement("SELECT *\n"
                    + "FROM evento\n"
                    + "WHERE data = CURDATE()\n"
                    + "AND (ora_inizio >= CURTIME() OR ADDTIME(data, ora_inizio) >= NOW())\n"
                    + "AND ADDTIME(data, ora_inizio) <= ADDTIME(NOW(), '03:00:00');");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }

    }

    public void destroy() throws DataException {
        try {
            getAllEeventiNext3Hours.close();

        } catch (SQLException ex) {

        }
        super.destroy();
    }
    @Override
    public Evento importEvento() {
        return new EventoProxy(this.getDataLayer());
    }
    
    private EventoProxy importEvento(ResultSet rs) throws DataException {
        EventoProxy e = (EventoProxy) this.importEvento();
        try {
            e.setKey(rs.getInt("ID"));
            e.setNome(rs.getString("nome"));
            e.setDescrizione(rs.getString("descrizione"));
            
            
            //COMPLETARE
            
            for (Tipo t : Tipo.values()) {
                if (t.toString().equals(rs.getString("tipo_evento"))) {
                    e.setTipo_evento(t);
                    break;
                }
            }
            
            e.setVersion(rs.getInt("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore nel DataLayer", ex);
        }
        
        return e;
    }

    
    //COMPLETARE
    @Override
    public List<Evento> getAllEeventiNext3Hours() throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
