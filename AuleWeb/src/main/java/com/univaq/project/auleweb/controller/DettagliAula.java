package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DettagliAula extends AuleWebController {

    private DataLayerImpl dataLayer;
    private Aula aula;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            aula = dataLayer.getAuleDAO().getAulaById(id);
            String[] styles = {"dettagliAula", "info"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("options", true);
            data.put("aula", aula);
            data.put("attrezzature", dataLayer.getAttrezzatureDAO().getAttrezzaturaByAula(id));
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("dettagliAula.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
