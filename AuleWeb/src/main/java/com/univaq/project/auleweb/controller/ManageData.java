package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.AulaImpl;
import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageData extends AuleWebController {

    /*
    OPERAZIONI:
    - 1 = insert/update
    - 2 = remove
    
    OGGETTI:
    - 1 = aule
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
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
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
                    default ->
                        handleError("Richiesta non valida (parametri -> type: " + type + ", object: " + object + ")", request, response);
                }
            }
            default ->
                handleError("Richiesta non valida (parametri -> type: " + type + ", object: " + object + ")", request, response);

        }
    }

    private void ErrorPage(String destination, String message, HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {""};
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

    private void SuccessPage(String destination, String message, HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {""};
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
            // Recupera i dati dal modulo inviati tramite FormData
            int id = Integer.parseInt(request.getParameter("id"));
            int versione = Integer.parseInt(request.getParameter("versione"));

            String nome = request.getParameter("nome");
            String luogo = request.getParameter("luogo");
            String edificio = request.getParameter("edificio");
            int piano = Integer.parseInt(request.getParameter("piano"));
            int capienza = Integer.parseInt(request.getParameter("capienza"));
            int preseRete = Integer.parseInt(request.getParameter("prese_rete"));
            int preseElettriche = Integer.parseInt(request.getParameter("prese_elettriche"));
            String note = request.getParameter("note");
            int responsabile = Integer.parseInt(request.getParameter("responsabile"));

            // Recupera attrezzature selezionate (array di stringhe)
            String[] attrezzatureArray = request.getParameterValues("attrezzaturaTable[]");
            int[] attrezzature = Arrays.stream(attrezzatureArray)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            // Recupera gruppi selezionati (array di stringhe)
            String[] gruppiArray = request.getParameterValues("gruppi[]");
            int[] gruppi = Arrays.stream(gruppiArray)
                    .mapToInt(Integer::parseInt)
                    .toArray();

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

        } catch (DataException ex) {
            handleError(ex, request, response);
        }
    }

    private void auleRemove(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
