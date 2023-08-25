package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.exportData.CSVExporter;
import com.univaq.project.auleweb.data.exportData.ICalendarExporter;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.StreamResult;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Homepage extends AuleWebController {

    private DataLayerImpl dataLayer;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        String page = request.getParameter("page");
        String action = request.getParameter("action");
        dataLayer = (DataLayerImpl) request.getAttribute("datalayer");

        if (action != null && "export".equals(action)) {
            action_export(request, response);
        }
        if (page != null) {
            switch (page) {
                case "home" ->
                    action_home(request, response);
                case "gruppi" ->
                    action_gruppi(request, response);
                case "aule" ->
                    action_aule(request, response);
                case "corsi" ->
                    action_corsi(request, response);
                default ->
                    action_home(request, response);
            }
        } else {
            action_home(request, response);
        }
    }

    private void action_gruppi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"gruppi"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("categorie", dataLayer.getCategorieDAO().getAllCategorie());
            data.put("gruppi", dataLayer.getGruppiDAO().getAllGruppi());
            data.put("options", true);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("gruppi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_corsi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filter = request.getParameter("filter");
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("options", true);
            data.put("searchLink", "homepage?page=corsi");

            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("corsi", dataLayer.getCorsiDAO().getCorsiByName(filter));
            } else {
                data.put("corsi", dataLayer.getCorsiDAO().getAllCorsi());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("corsi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"aule", "search", "simpleTable"};
            String filter = request.getParameter("filter");
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("options", true);
            data.put("searchLink", "homepage?page=aule");

            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("aule", dataLayer.getAuleDAO().getAuleByName(filter));
            } else {
                data.put("aule", dataLayer.getAuleDAO().getAllAule());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_home(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"importExport", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("options", true);
            data.put("eventi", dataLayer.getEventiDAO().getAllEeventiNext3Hours());
            data.put("corsi", dataLayer.getCorsiDAO().getAllCorsi());
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("home.ftl.html", data, response);

        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_export(HttpServletRequest request, HttpServletResponse response) {
        try {
            String tipo = request.getParameter("format");
            Integer corsoId = SecurityHelpers.checkNumeric(request.getParameter("corso"));
            String dataInizio = request.getParameter("startDate");
            String dataFine = request.getParameter("endDate");
            String outputName = request.getParameter("outputName");

            if (tipo != null && dataInizio != null && dataFine != null) {
                StreamResult downloader = new StreamResult(getServletContext());
                downloader.setResource(createFile(tipo, corsoId, dataInizio, dataFine, outputName));
                downloader.activate(request, response);
            }
        } catch (IOException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    private File createFile(String tipo, Integer corsoId, String dataInizio, String dataFine, String outputName) throws DataException {
        List<Evento> eventi;
        if (corsoId != -1) {
            eventi = dataLayer.getEventiDAO().getEventiByCorsoAndDateRange(corsoId, dataInizio, dataFine);
        } else {
            eventi = dataLayer.getEventiDAO().getEventiByDateRange(dataInizio, dataFine);
        }
        switch (tipo) {
            case "csv" -> {
                outputName = outputName != null && !outputName.isBlank() ? (outputName.endsWith(".csv") ? outputName : outputName + ".csv") : "eventi.csv";
                return CSVExporter.exportEventiToCsv(eventi, getServletContext().getRealPath(outputName));
            }
            case "ical" -> {
                outputName = outputName != null && !outputName.isBlank() ? (outputName.endsWith(".ics") ? outputName : outputName + ".ics") : "eventi.ics";
                return ICalendarExporter.exportEventiToICalendar(eventi, getServletContext().getRealPath(outputName));
            }
            default ->
                throw new DataException("tipo di file non valido!\nTipo specificato '" + tipo + "'");
        }
    }
}
