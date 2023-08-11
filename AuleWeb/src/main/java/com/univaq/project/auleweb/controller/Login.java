package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends AuleWebController {

    private DataLayerImpl datalayer;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        datalayer = (DataLayerImpl) request.getAttribute("datalayer");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if  (username == null || password == null) {
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            action_default(request, response);
        } else {
            action_login(request, response, username, password);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map data = new HashMap<>();
            String[] styles = {"login"};
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("login.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        if (!username.isBlank() && !password.isBlank()) {
            try {
                Amministratore amministratore = datalayer.getAmministratoriDAO().getAmministratoreByUsernamePassword(username, password);
                if (amministratore != null) {
                    SecurityHelpers.createSession(request, amministratore.getUsername(), amministratore.getKey());
                    response.sendRedirect("amministrazione");
                } else {
                    action_error(request, response);
                }
            } catch (IOException | DataException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map data = new HashMap<>();
            String[] styles = {"login"};
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("error", true);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("login.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
