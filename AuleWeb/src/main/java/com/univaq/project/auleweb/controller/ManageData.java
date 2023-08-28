package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.exportData.CSVExporter;
import com.univaq.project.auleweb.data.implementation.AulaImpl;
import com.univaq.project.auleweb.data.implementation.CorsoImpl;
import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.implementation.EventoImpl;
import com.univaq.project.auleweb.data.implementation.GruppoImpl;
import com.univaq.project.auleweb.data.implementation.ResponsabileImpl;
import com.univaq.project.auleweb.data.implementation.enumType.Laurea;
import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.importData.CSVImporter;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.StreamResult;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public class ManageData extends AuleWebController {

    /*
    OPERAZIONI (type):
    - 1 = insert/update
    - 2 = remove
    - 3 = import
    - 4 = export
 
    ENTITÀ (object):
    - 1 = aule
    - 2 = attrezzature
    - 3 = corsi
    - 4 = responsabili
    - 5 = gruppi
    - 6 = eventi
     */
    private DataLayerImpl dataLayer;
    private Amministratore amministratore;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            amministratore = getLoggedAdminstrator(dataLayer, request);
            if (amministratore == null) {
                response.sendRedirect("homepage");
            }

            String typeParam = request.getParameter("type");
            String objectParam = request.getParameter("object");

            if (typeParam == null || objectParam == null) {
                // Parametri mancanti, gestisci l'errore
                handleError("Richiesta non valida (parametri -> type: " + typeParam + ", object: " + objectParam + ")", request, response);
                return;
            }

            int type = SecurityHelpers.checkNumeric(typeParam);
            int object = SecurityHelpers.checkNumeric(objectParam);

            switch (type) {
                case 1 -> {
                    switch (object) {
                        case 1 ->
                            auleInsertOrUpdate(request, response);
                        case 2 ->
                            attrezzatureInsert(request, response);
                        case 3 ->
                            corsiInsertOrUpdate(request, response);
                        case 4 ->
                            responsabiliInsertOrUpdate(request, response);
                        case 5 ->
                            gruppiInsertOrUpdate(request, response);
                        case 6 ->
                            eventiInsertOrUpdate(request, response);
                        default ->
                            handleError("Richiesta non valida (parametri -> type: " + type + ", object: " + object + ")", request, response);
                    }
                }
                case 2 -> {
                    // remove
                    switch (object) {
                        case 1 ->
                            auleRemove(request, response);
                        case 2 ->
                            attrezzatureRemove(request, response);
                        case 3 ->
                            corsoRemove(request, response);
                        case 4 ->
                            responsabiliRemove(request, response);
                        case 5 ->
                            gruppiRemove(request, response);
                        case 6 ->
                            eventiRemove(request, response);
                        default ->
                            handleError("Richiesta non valida (parametri -> type: " + type + ", object: " + object + ")", request, response);
                    }
                }

                case 3 ->
                    action_import(request, response);

                case 4 ->
                    action_export(request, response);

                default ->
                    handleError("Richiesta non valida (parametri -> type: " + type + ", object: " + object + ")", request, response);

            }
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void errorPage(String destination, String message, HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"administration/formError"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("message", message);
            data.put("destination", destination);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/formError.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void successPage(String destination, String message, HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"administration/formSuccess"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("message", message);
            data.put("destination", destination);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/formSuccess.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void auleInsertOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
            int id = SecurityHelpers.checkNumeric(request.getParameter("id"));

            String nome = request.getParameter("nome");
            String luogo = request.getParameter("luogo");
            String edificio = request.getParameter("edificio");
            int piano = SecurityHelpers.checkNumeric(request.getParameter("piano"));
            int capienza = SecurityHelpers.checkNumeric(request.getParameter("capienza"));
            int preseRete = SecurityHelpers.checkNumeric(request.getParameter("preseRete"));
            int preseElettriche = SecurityHelpers.checkNumeric(request.getParameter("preseElettriche"));
            String note = request.getParameter("note");
            int responsabile = SecurityHelpers.checkNumeric(request.getParameter("responsabile"));

            // Recupera attrezzature selezionate
            String attrezzatureString = request.getParameter("attrezzature");
            List<Integer> attrezzature = attrezzatureString.isEmpty() ? new ArrayList<>() : Arrays.asList(attrezzatureString.split(","))
                    .stream()
                    .map(SecurityHelpers::checkNumeric)
                    .collect(Collectors.toList());

            // Recupera gruppi selezionati
            List<Integer> gruppi = Arrays.asList(request.getParameter("gruppi").split(","))
                    .stream()
                    .map(SecurityHelpers::checkNumeric)
                    .collect(Collectors.toList());

            Aula aula;
            if (id != -1) {
                aula = dataLayer.getAuleDAO().getAulaById(id);
            } else {
                aula = new AulaImpl();
            }

            aula.setNome(nome);
            aula.setCapienza(capienza);
            aula.setLuogo(luogo);
            aula.setEdificio(edificio);
            aula.setPiano(piano);
            aula.setPreseElettriche(preseElettriche);
            aula.setPreseRete(preseRete);
            aula.setResponsabile(dataLayer.getResponsabiliDAO().getResponsabileById(responsabile));
            aula.setNote(note);
            aula.setVersion(versione);

            dataLayer.getAuleDAO().storeAula(aula, gruppi, attrezzature);

            successPage("amministrazione?page=aule", "Operazione completata con successo", request, response);

        } catch (DataException | NumberFormatException ex) {
            errorPage("amministrazione?page=aule", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void auleRemove(HttpServletRequest request, HttpServletResponse response) {
        int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
        int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
        try {
            dataLayer.getAuleDAO().deleteAulaById(id, versione);
            successPage("amministrazione?page=aule", "L'aula è stata eliminata con successo", request, response);
        } catch (DataException ex) {
            errorPage("amministrazione?page=aule", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void attrezzatureRemove(HttpServletRequest request, HttpServletResponse response) {
        int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
        int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
        try {
            dataLayer.getAttrezzatureDAO().deleteAttrezzaturaById(id, versione);
            successPage("amministrazione?page=attrezzature", "L'attrezzatura è stata eliminata con successo", request, response);
        } catch (DataException ex) {
            errorPage("amministrazione?page=attrezzature", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void attrezzatureInsert(HttpServletRequest request, HttpServletResponse response) {
        try {
            String nome = request.getParameter("nome");
            String codice = request.getParameter("codice");

            dataLayer.getAttrezzatureDAO().insertAttrezzatura(nome, codice);

            successPage("amministrazione?page=attrezzatura", "Operazione completata con successo", request, response);

        } catch (DataException ex) {
            errorPage("amministrazione?page=attrezzature", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void corsiInsertOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
            int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            String laureaString = request.getParameter("laurea");

            Corso corso;
            Laurea laurea = null;

            for (Laurea l : Laurea.values()) {
                if (l.toString().equals(laureaString)) {
                    laurea = l;
                    break;
                }
            }

            if (laurea == null) {
                throw new DataException("Corso di laurea non valido");
            }

            if (id >= 0) {
                corso = dataLayer.getCorsiDAO().getCorsoById(id);
            } else {
                corso = new CorsoImpl();
            }

            corso.setVersion(versione);
            corso.setNome(nome);
            corso.setDescrizione(descrizione);
            corso.setCorsoLaurea(laurea);

            dataLayer.getCorsiDAO().storeCorso(corso);

            successPage("amministrazione?page=corsi", "Operazione completata con successo", request, response);

        } catch (DataException ex) {
            errorPage("amministrazione?page=corsi", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void corsoRemove(HttpServletRequest request, HttpServletResponse response) {
        int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
        int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
        try {
            dataLayer.getCorsiDAO().deleteCorsoById(id, versione);
            successPage("amministrazione?page=corsi", "L'attrezzatura è stata eliminata con successo", request, response);
        } catch (DataException ex) {
            errorPage("amministrazione?page=corsi", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void responsabiliInsertOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
            int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String email = request.getParameter("email");

            Responsabile responsabile;

            if (id >= 0) {
                responsabile = dataLayer.getResponsabiliDAO().getResponsabileById(id);
            } else {
                responsabile = new ResponsabileImpl();
            }

            responsabile.setVersion(versione);
            responsabile.setNome(nome);
            responsabile.setCognome(cognome);
            responsabile.setEmail(email);

            dataLayer.getResponsabiliDAO().storeResponsabile(responsabile);

            successPage("amministrazione?page=responsabili", "Operazione completata con successo", request, response);

        } catch (DataException ex) {
            errorPage("amministrazione?page=responsabili", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void responsabiliRemove(HttpServletRequest request, HttpServletResponse response) {
        int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
        int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
        try {
            dataLayer.getResponsabiliDAO().deleteResponsabileById(id, versione);
            successPage("amministrazione?page=responsabili", "L'attrezzatura è stata eliminata con successo", request, response);
        } catch (DataException ex) {
            errorPage("amministrazione?page=responsabili", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void gruppiInsertOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
            int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            String categoriaName = request.getParameter("categoriaName");
            Integer categoriaKey = SecurityHelpers.checkNumeric(request.getParameter("categoriaKey"));

            if (categoriaKey < 0) {
                categoriaKey = dataLayer.getCategorieDAO().insertCategoria(categoriaName);
            }

            Categoria categoria = dataLayer.getCategorieDAO().getCategoriaById(categoriaKey);
            Gruppo gruppo;

            if (id >= 0) {
                gruppo = dataLayer.getGruppiDAO().getGruppoByID(id);
            } else {
                gruppo = new GruppoImpl();
            }

            gruppo.setVersion(versione);
            gruppo.setNome(nome);
            gruppo.setDescrizione(descrizione);
            gruppo.setCategoria(categoria);

            dataLayer.getGruppiDAO().storeGruppo(gruppo);

            successPage("amministrazione?page=gruppi", "Operazione completata con successo", request, response);

        } catch (DataException ex) {
            errorPage("amministrazione?page=gruppi", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void gruppiRemove(HttpServletRequest request, HttpServletResponse response) {
        int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
        int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
        try {
            dataLayer.getGruppiDAO().deleteGruppoById(id, versione);
            successPage("amministrazione?page=responsabili", "L'attrezzatura è stata eliminata con successo", request, response);
        } catch (DataException ex) {
            errorPage("amministrazione?page=responsabili", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void action_export(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer gruppoId = SecurityHelpers.checkNumeric(request.getParameter("gruppo"));
            String outputName = request.getParameter("outputName");

            StreamResult downloader = new StreamResult(getServletContext());
            downloader.setResource(createFile(gruppoId, outputName));
            downloader.activate(request, response);

            successPage("amministrazione?page=aule", "L'esportazione è andata a buon fine", request, response);
        } catch (DataException | IOException ex) {
            errorPage("amministrazione?page=dati", "Si è verificato un errore durante l'esportazione: " + ex.getMessage(), request, response);
        }

    }

    private File createFile(Integer gruppoId, String outputName) throws DataException {
        List<Aula> aule;
        Map<Aula, List<Attrezzatura>> attrezzature = new HashMap<>();
        Map<Aula, List<Gruppo>> gruppi = new HashMap<>();

        if (gruppoId >= 0) {
            aule = dataLayer.getAuleDAO().getAuleByGruppoID(gruppoId);
        } else {
            aule = dataLayer.getAuleDAO().getAllAule();
        }

        for (Aula aula : aule) {
            List<Attrezzatura> attrezzatureList = dataLayer.getAttrezzatureDAO().getAttrezzaturaByAula(aula.getKey());
            List<Gruppo> gruppiList = dataLayer.getGruppiDAO().getGruppiByAula(aula.getKey());
            attrezzature.put(aula, attrezzatureList);
            gruppi.put(aula, gruppiList);
        }

        outputName = outputName != null && !outputName.isBlank() ? (outputName.endsWith(".csv") ? outputName : outputName + ".csv") : "aule.csv";
        return CSVExporter.exportAuleToCsv(aule, attrezzature, gruppi, getServletContext().getRealPath(outputName));

    }

    private void action_import(HttpServletRequest request, HttpServletResponse response) {
        try {
            Path tempFilePath = Files.createTempFile("import_aule", ".csv");
            try ( InputStream input = request.getPart("inputfile").getInputStream();  OutputStream output = Files.newOutputStream(tempFilePath)) {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = input.read(buffer)) > 0) {
                    output.write(buffer, 0, read);
                }
                output.close();
            }
            CSVImporter.importAuleFromCSV(tempFilePath.toFile(), dataLayer);
            Files.deleteIfExists(tempFilePath);

            successPage("amministrazione?page=aule", "L'importazione è andata a buon fine", request, response);
        } catch (DataException | IOException | ServletException ex) {
            errorPage("amministrazione?page=dati", "Si è verificato un errore durante l'importazione: " + ex.getMessage(), request, response);
        }
    }

    private void eventiInsertOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
            int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
            String tipoRicorrenzaString = request.getParameter("tipoRicorrenza");
            Integer tipoRicorrenza = tipoRicorrenzaString == null ? null : SecurityHelpers.checkNumeric(tipoRicorrenzaString);
            String fineRicorrenza = request.getParameter("fineRicorrenza");

            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            String giorno = request.getParameter("giorno");
            String oraInizio = request.getParameter("oraInizio");
            String oraFine = request.getParameter("oraFine");

            int idAula = SecurityHelpers.checkNumeric(request.getParameter("aula"));
            String corso = request.getParameter("corso");
            Integer corsoId = corso == null ? null : SecurityHelpers.checkNumeric(request.getParameter("corso"));
            int responsabileId = SecurityHelpers.checkNumeric(request.getParameter("responsabile"));
            String tipoString = request.getParameter("tipo");

            boolean tutti = Boolean.getBoolean(request.getParameter("tutti"));

            Evento evento;
            Tipo tipo = null;
            DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");

            for (Tipo t : Tipo.values()) {
                if (t.toString().equals(tipoString)) {
                    tipo = t;
                    break;
                }
            }

            if (tipo == null) {
                throw new DataException("Tipo di evento non valido");
            }

            if (id >= 0) {
                evento = dataLayer.getEventiDAO().getEventoById(id);
            } else {
                evento = new EventoImpl();
            }

            evento.setVersion(versione);
            evento.setNome(nome);
            evento.setDescrizione(descrizione);
            evento.setData(new Date(dateFormat.parse(giorno).getTime()));
            evento.setOraInizio(new Time(timeFormat.parse(oraInizio).getTime()));
            evento.setOraFine(new Time(timeFormat.parse(oraFine).getTime()));
            evento.setAula(dataLayer.getAuleDAO().getAulaById(idAula));
            evento.setTipoEvento(tipo);
            if (tipo == Tipo.LEZIONE || tipo == Tipo.ESAME || tipo == Tipo.PARZIALE) {
                evento.setCorso(dataLayer.getCorsiDAO().getCorsoById(corsoId));
            } else {
                evento.setCorso(null);
            }
            evento.setResponsabile(dataLayer.getResponsabiliDAO().getResponsabileById(responsabileId));

            dataLayer.getEventiDAO().storeEvento(evento, tutti, tipoRicorrenza, fineRicorrenza);

            successPage("amministrazione?page=eventi", "Operazione completata con successo", request, response);
        } catch (ParseException | DataException ex) {
            errorPage("amministrazione?page=eventi", "Si è verificato un errore: " + ex.getMessage(), request, response);
        }
    }

    private void eventiRemove(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
