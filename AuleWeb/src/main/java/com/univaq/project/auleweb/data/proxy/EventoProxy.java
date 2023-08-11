package com.univaq.project.auleweb.data.proxy;

import com.univaq.project.auleweb.data.dao.AuleDAO;
import com.univaq.project.auleweb.data.dao.CorsiDAO;
import com.univaq.project.auleweb.data.dao.ResponsabiliDAO;
import com.univaq.project.auleweb.data.implementation.EventoImpl;
import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataItemProxy;
import com.univaq.project.framework.data.DataLayer;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventoProxy extends EventoImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;

    protected int id_corso;
    protected int id_responsabile;
    protected int id_aula;

    public EventoProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.id_corso = 0;
        this.id_aula = 0;
        this.id_responsabile = 0;
    }

    @Override
    public Corso getCorso() {
        if (super.getCorso() == null && this.id_corso > 0) {
            try {
                super.setCorso(((CorsiDAO) dataLayer.getDAO(Corso.class)).getCorsoById(this.id_corso));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getCorso();
    }

    @Override
    public Aula getAula() {
        if (super.getAula() == null && this.id_aula > 0) {
            try {
                super.setAula(((AuleDAO) dataLayer.getDAO(Aula.class)).getAulaById(this.id_aula));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAula();
    }

    @Override
    public Responsabile getResponsabile() {
        if (super.getResponsabile() == null && this.id_responsabile > 0) {
            try {
                super.setResponsabile(((ResponsabiliDAO) dataLayer.getDAO(Responsabile.class)).getResponsabileById(this.id_responsabile));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getResponsabile();
    }

    @Override
    public void setId_ricorrenza(Integer id_ricorrenza) {
        super.setId_ricorrenza(id_ricorrenza);
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
        this.modified = true;
    }

    @Override
    public void setData(Date data) {
        super.setData(data);
        this.modified = true;
    }

    @Override
    public void setOraInizio(Time ora_inizio) {
        super.setOraInizio(ora_inizio);
        this.modified = true;
    }

    @Override
    public void setOraFine(Time ora_fine) {
        super.setOraFine(ora_fine);
        this.modified = true;
    }

    @Override
    public void setCorso(Corso corso) {
        super.setCorso(corso);
        if (corso != null) {
            this.id_corso = corso.getKey();
        } else {
            this.id_corso = 0;
        }
        this.modified = true;
    }

    @Override
    public void setTipoEvento(Tipo tipo_evento) {
        super.setTipoEvento(tipo_evento);
        this.modified = true;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        super.setResponsabile(responsabile);
        if (responsabile != null) {
            this.id_responsabile = responsabile.getKey();
        } else {
            this.id_responsabile = 0;
        }
        this.modified = true;
    }

    @Override
    public void setAula(Aula aula) {
        super.setAula(aula);
        if (aula != null) {
            this.id_aula = aula.getKey();
        } else {
            this.id_aula = 0;
        }
        this.modified = true;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    public void setResponsabileId(int id) {
        this.id_responsabile = id;
        super.setResponsabile(null);
    }

    public void setAulaId(int id) {
        this.id_aula = id;
        super.setAula(null);
    }

    public void setCorsoId(int id) {
        this.id_corso = id;
        super.setCorso(null);
    }

    public int getResponsabileId() {
        return this.id_responsabile;
    }

    public int getAulaId() {
        return this.id_aula;
    }

    public int getCorsoId() {
        return this.id_corso;
    }

    @Override
    public String toString() {
        return this.getKey() + "," + this.getNome() + "," + this.getDescrizione() + "," + this.getData() + "," + this.getOraFine() + "," + this.getOraInizio() + "," + this.getAulaId() + "," + this.getResponsabileId() + "," + this.getCorsoId();
    }

    @Override
    public boolean equals(Object obj) {
        EventoProxy evento = (EventoProxy) obj;
        if (super.equals(evento)) {
            if (this.id_corso != evento.id_corso) {
                return false;
            }
            if (this.id_responsabile != evento.id_responsabile) {
                return false;
            }
            return this.id_aula == evento.id_aula;
        } else {
            return false;
        }
    }
}
