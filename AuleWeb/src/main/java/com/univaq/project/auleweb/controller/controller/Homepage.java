package com.univaq.project.auleweb.controller.controller;

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
            Map data = new HashMap<>();
            data.put("outline_tpl", "home.ftl.html");
            data.put("username", SecurityHelpers.checkSession(request));
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("home.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
