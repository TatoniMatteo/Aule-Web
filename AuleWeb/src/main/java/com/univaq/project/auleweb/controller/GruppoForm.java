package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Gruppo;
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

public class GruppoForm extends AuleWebController {

    private DataLayerImpl dataLayer;
    private Amministratore amministratore;
    private Gruppo gruppo;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            amministratore = getLoggedAdminstrator(dataLayer, request);
            gruppo = null;
            if (amministratore == null) {
                response.sendRedirect("homepage");
            }

            String id = request.getParameter("id");
            if (id != null && !id.isBlank()) {
                gruppo = dataLayer.getGruppiDAO().getGruppoByID(SecurityHelpers.checkNumeric(id));
            }

            String[] styles = {"simpleForm", "administration/gruppoForm"};
            String[] scripts = {"gruppoForm"};

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("scripts", scripts);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("gruppo", gruppo);
            data.put("categorie", dataLayer.getCategorieDAO().getAllCategorie());
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/gruppoForm.ftl.html", data, response);
        } catch (IOException | DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
