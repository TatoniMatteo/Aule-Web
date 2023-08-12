package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.export.CSVExporter;
import com.univaq.project.auleweb.export.ICalendarExporter;
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
        dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
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
                case "aule_filtered" ->
                    action_aule_filterd(request, response);
                case "corsi_filtered" ->
                    action_corsi_filterd(request, response);
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
            data.put("username", SecurityHelpers.checkSession(request));
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
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("corsi", dataLayer.getCorsiDAO().getAllCorsi());
            data.put("options", true);
            data.put("searchLink", "homepage?page=corsi");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("corsi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_corsi_filterd(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filter = request.getParameter("filter");
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("corsi", dataLayer.getCorsiDAO().getCorsiByName(filter));
            data.put("options", true);
            data.put("filter", filter);
            data.put("searchLink", "homepage?page=corsi");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("corsi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("aule", dataLayer.getAuleDAO().getAllAule());
            data.put("options", true);
            data.put("searchLink", "homepage?page=aule");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule_filterd(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filter = request.getParameter("filter");
            String[] styles = {"corsi", "search", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("aule", dataLayer.getAuleDAO().getAuleByName(filter));
            data.put("options", true);
            data.put("filter", filter);
            data.put("searchLink", "homepage?page=aule");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_home(HttpServletRequest request, HttpServletResponse response) {
        try {
            String tipo = request.getParameter("format");
            String corsoValue = request.getParameter("corso");
            Integer corso = corsoValue != null && !corsoValue.isBlank() ? Integer.valueOf(request.getParameter("corso")) : null;
            String dataInizio = request.getParameter("startDate");
            String dataFine = request.getParameter("endDate");
            String outputName = request.getParameter("outputName");

            if (tipo != null && dataInizio != null && dataFine != null) {
                StreamResult downloader = new StreamResult(getServletContext());
                downloader.setResource(createFile(tipo, corso, dataInizio, dataFine, outputName));
                downloader.activate(request, response);
            }

            String[] styles = {"home", "simpleTable"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("username", SecurityHelpers.checkSession(request));
            data.put("options", true);
            data.put("eventi", dataLayer.getEventiDAO().getAllEeventiNext3Hours());
            data.put("corsi", dataLayer.getCorsiDAO().getAllCorsi());
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("home.ftl.html", data, response);

        } catch (IOException | DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private File createFile(String tipo, Integer corso, String dataInizio, String dataFine, String outputName) throws DataException {
        List<Evento> eventi;
        String path;
        try {
            outputName = outputName != null && !outputName.isBlank() ? (outputName.endsWith(".csv") ? outputName : outputName + ".csv") : "eventi.csv";
            path = getServletContext().getRealPath(outputName);

            if (corso != null) {
                eventi = dataLayer.getEventiDAO().getEventiByCorsoAndDateRange(corso, dataInizio, dataFine);
            } else {
                eventi = dataLayer.getEventiDAO().getEventiByDateRange(dataInizio, dataFine);
            }
        } catch (DataException ex) {
            throw new DataException("Non ci sono eventi nel range di date selezionate");
        }

        switch (tipo) {
            case "csv" -> {
                return CSVExporter.exportEventiToCsv(eventi, path);
            }
            case "ical" -> {
                return ICalendarExporter.exportEventiToICalendar(eventi, path);
            }
            default ->
                throw new DataException("tipo di file non valido!\nTipo specificato '" + tipo + "'");
        }
    }
}
