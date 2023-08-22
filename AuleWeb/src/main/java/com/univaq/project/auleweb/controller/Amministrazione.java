package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Amministrazione extends AuleWebController {

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

            String page = request.getParameter("page");
            if (page != null) {
                switch (page) {
                    case "dashboard" ->
                        action_dashboard(request, response);
                    case "aule" ->
                        action_aule(request, response);
                    case "aule_filtered" ->
                        action_aule(request, response);
                    case "attrezzature" ->
                        action_attrezzature(request, response);
                    case "gruppi" ->
                        action_gruppi(request, response);
                    case "eventi" ->
                        action_eventi(request, response);
                    case "corsi" ->
                        action_corsi(request, response);
                    case "responsabili" ->
                        action_responsabili(request, response);
                    default ->
                        action_dashboard(request, response);
                }
            } else {
                action_dashboard(request, response);
            }
        } catch (IOException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_dashboard(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"administration/dashboard"};
            List<Statistica> statistiche = new ArrayList<>();
            statistiche.add(new Statistica("Eventi", "calendar-alt", dataLayer.getEventiDAO().getEventiNumber()));
            statistiche.add(new Statistica("Aule", "chalkboard", dataLayer.getAuleDAO().getAuleNumber()));
            statistiche.add(new Statistica("Attrezzature", "tools", dataLayer.getAttrezzatureDAO().getAttrezzatureNumber()));
            statistiche.add(new Statistica("Responsabili", "user", dataLayer.getResponsabiliDAO().getResponsabiliNumber()));
            statistiche.add(new Statistica("Corsi", "book", dataLayer.getCorsiDAO().getCorsiNumber()));
            statistiche.add(new Statistica("Eventi in corso", "flag", dataLayer.getEventiDAO().getActiveEventiNumber()));
            statistiche.add(new Statistica("Attrezzature Disponibili", "tools", dataLayer.getAttrezzatureDAO().getAttrezzatureDisponibiliNumber()));
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("statistiche", statistiche);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/dashboard.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"administration/administrationButtons", "search", "simpleTable"};
            String filter = request.getParameter("filter");

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("searchLink", "amministrazione?page=aule");
            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("aule", dataLayer.getAuleDAO().getAuleByName(filter));
            } else {
                data.put("aule", dataLayer.getAuleDAO().getAllAule());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_attrezzature(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"search", "simpleTable", "administration/administrationButtons"};
            String filter = request.getParameter("filter");

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("searchLink", "amministrazione?page=attrezzature");
            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("attrezzature", dataLayer.getAttrezzatureDAO().getAttrezzatureByName(filter));
            } else {
                data.put("attrezzature", dataLayer.getAttrezzatureDAO().getAllAttrezzature());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/attrezzature.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_gruppi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/gruppi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_eventi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/eventi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_corsi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/corsi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_responsabili(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/responsabili.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    public static class Statistica {

        private final String titolo;
        private final String icona;
        private final int valore;

        public Statistica(String titolo, String icona, int valore) {
            this.titolo = titolo;
            this.icona = icona;
            this.valore = valore;
        }

        public String getTitolo() {
            return titolo;
        }

        public String getIcona() {
            return icona;
        }

        public int getValore() {
            return valore;
        }
    }
}
