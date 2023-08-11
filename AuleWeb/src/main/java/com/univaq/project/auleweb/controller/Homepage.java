package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Homepage extends AuleWebController {

    private DataLayerImpl dataLayer;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        String page = request.getParameter("page");
        dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
        if (page != null) {
            switch (page) {
                case "home":
                    action_home(request, response);
                    break;
                case "gruppi":
                    action_gruppi(request, response);
                    break;
                case "aule":
                    action_aule(request, response);
                    break;
                case "corsi":
                    action_corsi(request, response);
                    break;
                case "aule_filtered":
                    action_aule_filterd(request, response);
                    break;
                case "corsi_filtered":
                    action_corsi_filterd(request, response);
                    break;
                default:
                    action_home(request, response);
            }
        } else {
            action_home(request, response);
        }
    }

    private void action_gruppi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"gruppi"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("categorie", dataLayer.getCategorieDAO().getAllCategorie());
            data.put("gruppi", dataLayer.getGruppiDAO().getAllGruppi());
            data.put("options", true);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("gruppi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_corsi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("corsi", dataLayer.getCorsiDAO().getAllCorsi());
            data.put("options", true);
            data.put("searchLink", "homepage?page=corsi");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("corsi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_corsi_filterd(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filter = request.getParameter("filter");
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("corsi", dataLayer.getCorsiDAO().getCorsiByName(filter));
            data.put("options", true);
            data.put("filter", filter);
            data.put("searchLink", "homepage?page=corsi");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("corsi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("aule", dataLayer.getAuleDAO().getAllAule());
            data.put("options", true);
            data.put("searchLink", "homepage?page=aule");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule_filterd(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filter = request.getParameter("filter");
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("aule", dataLayer.getAuleDAO().getAuleByName(filter));
            data.put("options", true);
            data.put("filter", filter);
            data.put("searchLink", "homepage?page=aule");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_home(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"home", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("options", true);
            data.put("eventi", dataLayer.getEventiDAO().getAllEeventiNext3Hours());
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("home.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
