package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.implementation.enumType.Tipo;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EventoForm extends AuleWebController {

    private DataLayerImpl dataLayer;
    private Amministratore amministratore;
    private Evento evento;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            amministratore = getLoggedAdminstrator(dataLayer, request);
            evento = null;
            if (amministratore == null) {
                response.sendRedirect("homepage");
            }

            String id = request.getParameter("id");
            if (id != null && !id.isBlank()) {
                evento = dataLayer.getEventiDAO().getEventoById(SecurityHelpers.checkNumeric(id));
            }

            String[] styles = {"simpleForm", "administration/aulaForm", "administration/eventoForm", "switch"};
            String[] scripts = {"eventoForm", "selectResponsabile", "switch"};

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("scripts", scripts);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("evento", evento);
            data.put("aule", dataLayer.getAuleDAO().getAllAule());
            data.put("responsabili", dataLayer.getResponsabiliDAO().getAllResponsabili());
            data.put("corsi", dataLayer.getCorsiDAO().getAllCorsi());
            data.put("tipi", Tipo.values());
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/eventoForm.ftl.html", data, response);
        } catch (IOException | DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
