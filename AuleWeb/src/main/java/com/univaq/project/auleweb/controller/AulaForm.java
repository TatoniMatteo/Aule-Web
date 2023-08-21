package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AulaForm extends AuleWebController {

    private DataLayerImpl dataLayer;
    private Amministratore amministratore;
    private Aula aula;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            amministratore = getLoggedAdminstrator(dataLayer, request);
            aula = null;
            if (amministratore == null) {
                response.sendRedirect("homepage");
            }

            String id = request.getParameter("id");
            if (id != null && !id.isBlank()) {
                aula = dataLayer.getAuleDAO().getAulaById(Integer.parseInt(id));
            }

            String[] styles = {"administration/aulaForm", "simpleTable", "simpleForm"};
            String[] scripts = {"selectResponsabile", "selectAttrezzatura"};

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("scripts", scripts);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("aula", aula);
            data.put("responsabili", dataLayer.getResponsabiliDAO().getAllResponsabili());
            data.put("attrezzatureAule", aula != null ? dataLayer.getAttrezzatureDAO().getAttrezzaturaByAula(aula.getKey()) : new ArrayList<Attrezzatura>());
            data.put("attrezzatureDisponibili", dataLayer.getAttrezzatureDAO().getAttrezzaturaDisponibile());
            data.put("gruppi", dataLayer.getGruppiDAO().getAllGruppi());
            data.put("gruppiAula", dataLayer.getGruppiDAO().getGruppiByAula(aula.getKey()));
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/aulaForm.ftl.html", data, response);
        } catch (IOException | DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

}
