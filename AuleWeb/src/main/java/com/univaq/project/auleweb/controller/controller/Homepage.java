package com.univaq.project.auleweb.controller.controller;

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
        try {
            String[] styles = {"home"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("categorie", ((DataLayerImpl) request.getAttribute("datalayer")).getCategorieDAO().getAllCategorie());
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("home.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
