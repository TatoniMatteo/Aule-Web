package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DettagliEvento extends AuleWebController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String back = request.getParameter("back");
            DataLayerImpl dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            String[] styles = {"dettagliEvento", "info"};
            Evento evento = dataLayer.getEventiDAO().getEventoById(id);
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("evento", evento);
            data.put("back", back);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("dettagliEvento.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
