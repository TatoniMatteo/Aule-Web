package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Corso;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DettagliCorso extends AuleWebController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DataLayerImpl dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            String[] settimana = getWeek(request.getParameter("week"));
            Corso corso = dataLayer.getCorsiDAO().getCorsoById(id);
            String[] styles = {"dettagliCorso", "info", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("corso", corso);
            data.put("settimana", settimana);
            data.put("eventi", dataLayer.getEventiDAO().getEventiByCorsoAndWeek(id, settimana[1]));
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("dettagliCorso.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private String[] getWeek(String inputWeek) {
        String[] settimana = new String[3];
        settimana[0] = inputWeek != null ? inputWeek : LocalDate.now().getYear() + "-W" + LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear());

        String[] parts = settimana[0].split("-W");
        int year = Integer.parseInt(parts[0]);
        int week = Integer.parseInt(parts[1]);

        TemporalField fieldISO = WeekFields.of(Locale.getDefault()).dayOfWeek();
        LocalDate firstDayOfWeek = LocalDate.of(year, 1, 1)
                .with(fieldISO, 1)
                .plusWeeks(week);
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);
        settimana[1] = firstDayOfWeek.toString();
        settimana[2] = lastDayOfWeek.toString();

        return settimana;
    }
}
