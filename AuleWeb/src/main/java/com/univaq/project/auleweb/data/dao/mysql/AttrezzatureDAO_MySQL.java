package com.univaq.project.auleweb.data.dao.mysql;

import com.univaq.project.auleweb.data.dao.AttrezzatureDAO;
import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.proxy.AttrezzaturaProxy;
import com.univaq.project.framework.data.DAO;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttrezzatureDAO_MySQL extends DAO implements AttrezzatureDAO {

    private PreparedStatement getAttrezzaturaByAulaId, getAttrezzaturaDisponibile;

    public AttrezzatureDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            getAttrezzaturaByAulaId = this.connection.prepareStatement("SELECT * FROM Attrezzatura WHERE id_aula=?");
            getAttrezzaturaDisponibile = this.connection.prepareStatement("SELECT * FROM Attrezzatura WHERE id_aula IS NULL");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {

            getAttrezzaturaByAulaId.close();
            getAttrezzaturaDisponibile.close();

        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public Attrezzatura importAttrezzatura() {
        return new AttrezzaturaProxy(getDataLayer());
    }

    private AttrezzaturaProxy importAttrezzatura(ResultSet rs) throws DataException {
        AttrezzaturaProxy attrezzatura = (AttrezzaturaProxy) importAttrezzatura();
        try {
            attrezzatura.setKey(rs.getInt("ID"));
            attrezzatura.setId_aula(rs.getInt("id_aula"));
            attrezzatura.setNome(rs.getString("nome"));
            attrezzatura.setNumeroSerie(rs.getString("numero_serie"));
            attrezzatura.setVersion(rs.getInt("versione"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto Attrezzatura", ex);
        }
        return attrezzatura;
    }

    @Override
    public List<Attrezzatura> getAttrezzaturaByAula(int aulaId) throws DataException {
        List<Attrezzatura> attrezzature = new ArrayList<>();
        try {
            getAttrezzaturaByAulaId.setInt(1, aulaId);
            try ( ResultSet rs = getAttrezzaturaByAulaId.executeQuery()) {
                while (rs.next()) {
                    Attrezzatura attrezzatura = importAttrezzatura(rs);
                    attrezzature.add(attrezzatura);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le attrezzature per l'aula con ID : " + aulaId, ex);
        }

        return attrezzature;
    }

    @Override
    public List<Attrezzatura> getAttrezzaturaDisponibile() throws DataException {
        List<Attrezzatura> attrezzature = new ArrayList();
        try ( ResultSet rs = getAttrezzaturaDisponibile.executeQuery()) {
            while (rs.next()) {
                Attrezzatura attrezzatura = importAttrezzatura(rs);
                attrezzature.add(attrezzatura);

            }

        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista di attrezzature disponibili", ex);
        }
        return attrezzature;
    }

}
