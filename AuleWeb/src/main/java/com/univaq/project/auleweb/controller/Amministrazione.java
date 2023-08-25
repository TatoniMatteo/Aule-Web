package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.exportData.CSVExporter;
import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.importData.CSVImporter;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.result.StreamResult;
import com.univaq.project.framework.result.TemplateManagerException;
import com.univaq.project.framework.result.TemplateResult;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public class Amministrazione extends AuleWebController {

    private DataLayerImpl dataLayer;
    private Amministratore amministratore;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            dataLayer = (DataLayerImpl) request.getAttribute("datalayer");
            amministratore = getLoggedAdminstrator(dataLayer, request);
            if (amministratore == null) {
                response.sendRedirect("homepage");
            }

            boolean error = false;

            String action = request.getParameter("action");
            if (action != null) {
                switch (action) {
                    case "export" ->
                        error = action_export(request, response);
                    case "import" ->
                        error = action_import(request, response);
                    default ->
                        throw new DataException("Operazione sconosciuta: " + action);
                }
            }

            if (!error) {
                String page = request.getParameter("page");
                if (page != null) {
                    switch (page) {
                        case "dashboard" ->
                            action_dashboard(request, response);
                        case "aule" ->
                            action_aule(request, response);
                        case "aule_filtered" ->
                            action_aule(request, response);
                        case "attrezzature" ->
                            action_attrezzature(request, response);
                        case "gruppi" ->
                            action_gruppi(request, response);
                        case "eventi" ->
                            action_eventi(request, response);
                        case "corsi" ->
                            action_corsi(request, response);
                        case "responsabili" ->
                            action_responsabili(request, response);
                        case "dati" ->
                            action_dati(request, response);
                        default ->
                            action_dashboard(request, response);
                    }
                } else {
                    action_dashboard(request, response);
                }
            }
        } catch (IOException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_dashboard(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"administration/dashboard"};
            List<Statistica> statistiche = new ArrayList<>();
            statistiche.add(new Statistica("Eventi", "calendar-alt", dataLayer.getEventiDAO().getEventiNumber()));
            statistiche.add(new Statistica("Aule", "chalkboard", dataLayer.getAuleDAO().getAuleNumber()));
            statistiche.add(new Statistica("Attrezzature", "tools", dataLayer.getAttrezzatureDAO().getAttrezzatureNumber()));
            statistiche.add(new Statistica("Responsabili", "user", dataLayer.getResponsabiliDAO().getResponsabiliNumber()));
            statistiche.add(new Statistica("Corsi", "book", dataLayer.getCorsiDAO().getCorsiNumber()));
            statistiche.add(new Statistica("Eventi in corso", "flag", dataLayer.getEventiDAO().getActiveEventiNumber()));
            statistiche.add(new Statistica("Attrezzature Disponibili", "tools", dataLayer.getAttrezzatureDAO().getAttrezzatureDisponibiliNumber()));
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("statistiche", statistiche);
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/dashboard.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_aule(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"administration/administrationButtons", "search", "simpleTable"};
            String filter = request.getParameter("filter");

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("searchLink", "amministrazione?page=aule");
            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("aule", dataLayer.getAuleDAO().getAuleByName(filter));
            } else {
                data.put("aule", dataLayer.getAuleDAO().getAllAule());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/aule.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_attrezzature(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"search", "simpleTable", "administration/administrationButtons"};
            String filter = request.getParameter("filter");

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("searchLink", "amministrazione?page=attrezzature");
            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("attrezzature", dataLayer.getAttrezzatureDAO().getAttrezzatureByNameOrCode(filter));
            } else {
                data.put("attrezzature", dataLayer.getAttrezzatureDAO().getAllAttrezzature());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/attrezzature.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_gruppi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"search", "simpleTable", "administration/administrationButtons"};
            String filter = request.getParameter("filter");

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("searchLink", "amministrazione?page=gruppi");
            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("gruppi", dataLayer.getGruppiDAO().getGruppiByName(filter));
            } else {
                data.put("gruppi", dataLayer.getGruppiDAO().getAllGruppi());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/gruppi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_eventi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/eventi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_corsi(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"search", "simpleTable", "administration/administrationButtons"};
            String filter = request.getParameter("filter");

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("searchLink", "amministrazione?page=corsi");
            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("corsi", dataLayer.getCorsiDAO().getCorsiByName(filter));
            } else {
                data.put("corsi", dataLayer.getCorsiDAO().getAllCorsi());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/corsi.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_responsabili(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"search", "simpleTable", "administration/administrationButtons"};
            String filter = request.getParameter("filter");

            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("searchLink", "amministrazione?page=responsabili");
            if (filter != null && !filter.isEmpty()) {
                data.put("filter", filter);
                data.put("responsabili", dataLayer.getResponsabiliDAO().getResponsabileByName(filter));
            } else {
                data.put("responsabili", dataLayer.getResponsabiliDAO().getAllResponsabili());
            }
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/responsabili.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_dati(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] styles = {"importExport"};
            String[] scripts = {"fileInput"};
            Map data = new HashMap<>();
            data.put("styles", styles);
            data.put("scripts", scripts);
            data.put("amministratore", getLoggedAdminstrator(dataLayer, request));
            data.put("outline_tpl", "base/outline_administration.ftl.html");
            data.put("gruppi", dataLayer.getGruppiDAO().getAllGruppi());
            TemplateResult templateResult = new TemplateResult(getServletContext());
            templateResult.activate("administration/dati.ftl.html", data, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private boolean action_export(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer gruppoId = SecurityHelpers.checkNumeric(request.getParameter("gruppo"));
            String outputName = request.getParameter("outputName");

            StreamResult downloader = new StreamResult(getServletContext());
            downloader.setResource(createFile(gruppoId, outputName));
            downloader.activate(request, response);

            return false;
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
            return true;
        }

    }

    private File createFile(Integer gruppoId, String outputName) throws DataException {
        List<Aula> aule;
        Map<Aula, List<Attrezzatura>> attrezzature = new HashMap<>();
        Map<Aula, List<Gruppo>> gruppi = new HashMap<>();

        if (gruppoId >= 0) {
            aule = dataLayer.getAuleDAO().getAuleByGruppoID(gruppoId);
        } else {
            aule = dataLayer.getAuleDAO().getAllAule();
        }

        for (Aula aula : aule) {
            List<Attrezzatura> attrezzatureList = dataLayer.getAttrezzatureDAO().getAttrezzaturaByAula(aula.getKey());
            List<Gruppo> gruppiList = dataLayer.getGruppiDAO().getGruppiByAula(aula.getKey());
            attrezzature.put(aula, attrezzatureList);
            gruppi.put(aula, gruppiList);
        }

        outputName = outputName != null && !outputName.isBlank() ? (outputName.endsWith(".csv") ? outputName : outputName + ".csv") : "aule.csv";
        return CSVExporter.exportAuleToCsv(aule, attrezzature, gruppi, getServletContext().getRealPath(outputName));

    }

    private boolean action_import(HttpServletRequest request, HttpServletResponse response) {
        try {
            Path tempFilePath = Files.createTempFile("import_aule", ".csv");
            try ( InputStream input = request.getPart("inputfile").getInputStream();  OutputStream output = Files.newOutputStream(tempFilePath)) {

                byte[] buffer = new byte[1024];
                int read;
                while ((read = input.read(buffer)) > 0) {
                    output.write(buffer, 0, read);
                }

                CSVImporter.importAuleFromCSV(tempFilePath.toFile(), dataLayer);
                Files.deleteIfExists(tempFilePath);
                return false;
            }
        } catch (DataException | IOException | ServletException ex) {
            handleError(ex, request, response);
            return true;
        }
    }

    public static class Statistica {

        private final String titolo;
        private final String icona;
        private final int valore;

        public Statistica(String titolo, String icona, int valore) {
            this.titolo = titolo;
            this.icona = icona;
            this.valore = valore;
        }

        public String getTitolo() {
            return titolo;
        }

        public String getIcona() {
            return icona;
        }

        public int getValore() {
            return valore;
        }
    }
}
