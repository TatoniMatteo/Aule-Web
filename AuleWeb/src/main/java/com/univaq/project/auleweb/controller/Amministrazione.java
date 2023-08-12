package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import java.io.IOException;
import java.util.HashMap;
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
                    /*
                    case "gruppi" ->
                        action_gruppi(request, response);
                    case "aule" ->
                        action_aule(request, response);
                    case "corsi" ->
                        action_corsi(request, response);
                    case "aule_filtered" ->
                        action_aule_filterd(request, response);
                    case "corsi_filtered" ->
                        action_corsi_filterd(request, response);
                     */
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
            String[] styles = {"gruppi"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/test.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
