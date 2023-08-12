package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DettagliGruppo extends AuleWebController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DataLayerImpl dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            String day = request.getParameter("day") != null
                    ? request.getParameter("day")
                    : LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String[] styles = {"dettagliGruppo", "simpleTable"};
            Gruppo gruppo = dataLayer.getGruppiDAO().getGruppoByID(id);
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("gruppo", gruppo);
            data.put("aule", dataLayer.getAuleDAO().getAuleByGruppoID(id));
            data.put("eventi", dataLayer.getEventiDAO().getEventiByGruppoIdAndDate(id, day));
            data.put("day", day);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("dettagliGruppo.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
