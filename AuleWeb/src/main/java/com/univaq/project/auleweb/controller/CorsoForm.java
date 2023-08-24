package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.implementation.enumType.Laurea;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsoForm extends AuleWebController {

    private DataLayerImpl dataLayer;
    private Amministratore amministratore;
    private Corso corso;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            amministratore = getLoggedAdminstrator(dataLayer, request);
            corso = null;
            if (amministratore == null) {
                response.sendRedirect("homepage");
            }

            String id = request.getParameter("id");
            if (id != null && !id.isBlank()) {
                corso = dataLayer.getCorsiDAO().getCorsoById(SecurityHelpers.checkNumeric(id));
            }

            String[] styles = {"simpleForm"};

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("corso", corso);
            data.put("lauree", Laurea.values());
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/aulaForm.ftl.html", data, response);
        } catch (IOException | DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
