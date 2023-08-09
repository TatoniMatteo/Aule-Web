package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Homepage extends AuleWebController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String page = request.getParameter("page");
        if (page != null) {
            switch (page) {
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
                default:
                    action_gruppi(request, response);
            }
        } else {
            action_gruppi(request, response);
        }
    }

    private void action_gruppi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"gruppi"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("categorie", ((DataLayerImpl) request.getAttribute("datalayer")).getCategorieDAO().getAllCategorie());
            data.put("gruppi", ((DataLayerImpl) request.getAttribute("datalayer")).getGruppiDAO().getAllGruppi());
            data.put("options", true);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("gruppi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_corsi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"corsi"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("aule", ((DataLayerImpl) request.getAttribute("datalayer")).getAuleDAO().getAllAule());
            data.put("options", true);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("corsi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"aule", "search"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("aule", ((DataLayerImpl) request.getAttribute("datalayer")).getAuleDAO().getAllAule());
            data.put("options", true);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule_filterd(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filter = request.getParameter("filter");
            String[] styles = {"aule", "search"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("aule", ((DataLayerImpl) request.getAttribute("datalayer")).getAuleDAO().getAuleByName(filter));
            data.put("options", true);
            data.put("filter", filter);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
