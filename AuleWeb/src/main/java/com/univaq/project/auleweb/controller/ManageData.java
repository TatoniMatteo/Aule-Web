package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.AulaImpl;
import com.univaq.project.auleweb.data.implementation.CorsoImpl;
import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.implementation.GruppoImpl;
import com.univaq.project.auleweb.data.implementation.ResponsabileImpl;
import com.univaq.project.auleweb.data.implementation.enumType.Laurea;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.IOException;
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
                    // insert/update
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
                        default ->
                            handleError("Richiesta non valida (parametri -> type: " + type + ", object: " + object + ")", request, response);
                    }
                }
                case 2 -> {
                    // remove
                    switch (object) {
                        case 2 ->
                            attrezzatureRemove(request, response);
                        default ->
                            handleError("Richiesta non valida (parametri -> type: " + type + ", object: " + object + ")", request, response);
                    }
                }
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

    private void attrezzatureRemove(HttpServletRequest request, HttpServletResponse response) {
        int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
        try {
            dataLayer.getAttrezzatureDAO().deleteAttrezzaturaById(id);
            successPage("amministrazione?page=attrezzature", "L'attrezzatura è stata eliminata con successo", request, response);
        } catch (DataException ex) {
            errorPage("amministrazione?page=attrezzature", ex.getMessage(), request, response);
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

    private void gruppiInsertOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = SecurityHelpers.checkNumeric(request.getParameter("id"));
            int versione = SecurityHelpers.checkNumeric(request.getParameter("versione"));
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            Categoria categoria = dataLayer
                    .getCategorieDAO()
                    .getCategoriaById(SecurityHelpers.checkNumeric(request.getParameter("categoria")));

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

}
