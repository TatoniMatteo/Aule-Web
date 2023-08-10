package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends AuleWebController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String action = request.getParameter("loginAction");
            if (action == null || action.isEmpty()) {
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                action_default(request, response);
            } else if (action.equals("loginAction")) {
                action_login(request, response);
            } else {
                action_error(request, response);
            }

        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        Map data = new HashMap<>();
        String[] styles = {"login"};
        data.put("styles", styles);
        data.put("username", SecurityHelpers.checkSession(request));
        TemplateResult templateResult = new TemplateResult(getServletContext());
        templateResult.activate("login.ftl.html", data, response);
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!username.isBlank() && !password.isBlank()) {
            try {
                //Amministratore a = ((DataLayerImpl) request.getAttribute("datalayer")).getAmministratoreDAO().getAmministratoreByUsername(username);
                Amministratore a = null;
                if (a != null && SecurityHelpers.checkPasswordHashPBKDF2(password, a.getPassword())) {
                    SecurityHelpers.createSession(request, username, a.getKey());
                    response.sendRedirect("administration");

                } else {
                    action_error(request, response);
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
