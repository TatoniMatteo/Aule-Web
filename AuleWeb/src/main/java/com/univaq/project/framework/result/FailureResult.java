/*
 * FailureResult.java
 * 
 * Si tratta di una semplice classe che incapsula un TemplateResult per offrire
 * un comodo sistema di visualizzazione degli errori. Si basa su un template
 * il cui nome deve essere presente nella configurazione dell'applicazione 
 * (web.xml, parametro view.error_template). In mancanza di questo, degrada
 * a un errore http.
 * 
 * This simple class wraps TemplateResult to provide an easy error displaying
 * system. It uses a template whose name must be configured as a context
 * parameter (web.xml, view.error_template parameter). If no template is found,
 * the class uses simple http errors.
 * 
 */
package com.univaq.project.framework.result;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FailureResult {

    protected ServletContext context;
    private final TemplateResult template;

    public FailureResult(ServletContext context) {
        this.context = context;
        template = new TemplateResult(context);
    }

    public void activate(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        String message = "Unknown exception";
        if (exception != null && exception.getMessage() != null && !exception.getMessage().isEmpty()) {
            //message = exception.getStackTrace()[0].toString() + " " + exception.getStackTrace()[1].toString()+ " " + exception.getStackTrace()[2].toString()+ " " + exception.getStackTrace()[3].toString() ;
            message = exception.getMessage();
            //message=exception.getCause().getMessage();
        } else if (exception != null) {
            message = exception.getClass().getName();
        }
        activate(message, request, response);
    }

    public void activate(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") instanceof Exception exception) {
            activate(exception, request, response);
        } else {
            activate("Unknown error", request, response);
        }
    }

    public void activate(String message, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (context.getInitParameter("view.error_template") != null) {
                StringBuffer fullUrl = request.getRequestURL();
                String queryString = request.getQueryString();
                if (queryString != null) {
                    fullUrl.append("?").append(queryString);
                }
                request.setAttribute("error", message);
                request.setAttribute("backLink", fullUrl);
                request.setAttribute("outline_tpl", "");
                template.activate(context.getInitParameter("view.error_template"), request, response);
            } else {
                //altrimenti, inviamo un errore HTTP
                //otherwise, use HTTP errors
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);

            }

        } catch (TemplateManagerException | IOException ex) {
            message += ". In addition, the following exception was generated while trying to display the error page: " + ex.getMessage();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            } catch (IOException ex1) {
                Logger.getLogger(FailureResult.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
