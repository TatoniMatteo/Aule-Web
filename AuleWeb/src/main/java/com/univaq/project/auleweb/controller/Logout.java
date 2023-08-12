package com.univaq.project.auleweb.controller;

import com.univaq.project.framework.security.SecurityHelpers;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends AuleWebController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            SecurityHelpers.disposeSession(request);
            String referer = request.getHeader("referer");
            if (referer != null && !referer.isBlank() && !referer.contains("amministrazione")) {
                response.sendRedirect(referer);
            }
            response.sendRedirect("homepage");
        } catch (IOException ex) {
            handleError(ex, request, response);
        }
    }

}
