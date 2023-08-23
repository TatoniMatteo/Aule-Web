package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.AulaImpl;
import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.IOException;
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
    OPERAZIONI:
    - 1 = insert/update
    - 2 = remove
    
    OGGETTI:
    - 1 = aule
    - 2 = attrezzature
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
                        case 1 -> // aule
                            auleInsertOrUpdate(request, response);
                        default ->
                            handleError("Richiesta non valida (parametri -> type: " + type + ", object: " + object + ")", request, response);
                    }
                }
                case 2 -> {
                    // remove
                    switch (object) {
                        case 1 -> // aule
                            auleRemove(request, response);
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
            List<Attrezzatura> attrezzature = Arrays.stream(request.getParameter("attrezzature").split(","))
                    .mapToInt(SecurityHelpers::checkNumeric)
                    .mapToObj(key =  > dataLayer.getAttrezzatureDAO().getAttrezzaturaById(key))
                    .collect(Collectors.toList());

            // Recupera gruppi selezionati
            List<Gruppo> gruppi = Arrays.stream(request.getParameter("gruppi").split(","))
                    .mapToInt(SecurityHelpers::checkNumeric)
                    .mapToObj(key -> dataLayer.getGruppiDAO().getGruppoByID(key))
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

            System.out.println("Oggetto Aula: " + aula);
        } catch (DataException | NumberFormatException ex) {
            handleError(ex, request, response);
        }
    }

    private void auleRemove(HttpServletRequest request, HttpServletResponse response) {

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
}
