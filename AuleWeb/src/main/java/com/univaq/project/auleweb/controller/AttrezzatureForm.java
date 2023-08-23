package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.implementation.enumType.AttrezzaturaTipo;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AttrezzatureForm extends AuleWebController {

    private Amministratore amministratore;
    private DataLayerImpl dataLayer;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            amministratore = getLoggedAdminstrator(dataLayer, request);
            if (amministratore == null) {
                response.sendRedirect("homepage");
            }

            String[] styles = {"simpleForm"};
            String[] scripts = {};

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("scripts", scripts);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("tipi", AttrezzaturaTipo.values());
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/attrezzaturaForm.ftl.html", data, response);
        } catch (IOException | DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
