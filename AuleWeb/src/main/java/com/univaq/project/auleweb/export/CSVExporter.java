package com.univaq.project.auleweb.export;

import com.univaq.project.auleweb.data.model.Evento;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CSVExporter {

    private static final String[] HEADERS_EVENTO = {"ID", "ID_ricorrenza", "nome", "descrizione", "data", "ora_inizio", "ora_fine", "id_corso", "corso", "id_aula", "aula", "tipo"};
    private static final String[] HEADERS_AULA = {};

    public static File exportEventiToCsv(List<Evento> eventi, String filePath) {
        CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader(HEADERS_EVENTO).build();
        try ( CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(filePath), csvFormat)) {
            for (Evento evento : eventi) {
                csvPrinter.printRecord(
                        evento.getKey(),
                        evento.getId_ricorrenza(),
                        evento.getNome(),
                        evento.getDescrizione(),
                        evento.getData(),
                        evento.getOraInizio(),
                        evento.getOraFine(),
                        evento.getCorso() != null ? evento.getCorso().getKey() : 0,
                        evento.getCorso() != null ? evento.getCorso().getNome() : "",
                        evento.getAula().getKey(),
                        evento.getAula().getNome(),
                        evento.getTipoEvento()
                );

            }
        } catch (IOException ex) {
            Logger.getLogger(CSVExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new File(filePath);
    }
}
