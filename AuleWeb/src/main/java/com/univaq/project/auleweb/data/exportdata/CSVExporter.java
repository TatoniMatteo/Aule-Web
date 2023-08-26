package com.univaq.project.auleweb.data.exportData;

import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Evento;
import com.univaq.project.auleweb.data.model.Gruppo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CSVExporter {

    private static final String[] HEADER_EVENTO = {"ID_ricorrenza", "nome", "descrizione", "data", "ora_inizio", "ora_fine", "corso", "aula", "tipo"};
    private static final String[] HEADER_AULA = {"nome", "luogo", "edificio", "piano", "capienza", "prese_elettriche", "prese_rete", "note", "responsabile", "attrezzature", "gruppi"};

    public static File exportEventiToCsv(List<Evento> eventi, String filePath) {
        CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader(HEADER_EVENTO).build();
        try ( CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(filePath), csvFormat)) {
            for (Evento evento : eventi) {
                csvPrinter.printRecord(
                        evento.getIdRicorrenza(),
                        evento.getNome(),
                        evento.getDescrizione(),
                        evento.getData(),
                        evento.getOraInizio(),
                        evento.getOraFine(),
                        evento.getCorso() != null ? evento.getCorso().getNome() : "",
                        evento.getAula().getNome(),
                        evento.getTipoEvento()
                );

            }
        } catch (IOException ex) {
            Logger.getLogger(CSVExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new File(filePath);
    }

    public static File exportAuleToCsv(List<Aula> aule, Map<Aula, List<Attrezzatura>> attrezzature, Map<Aula, List<Gruppo>> gruppi, String filePath) {
        CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader(HEADER_AULA).build();
        try ( CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(filePath), csvFormat)) {
            for (Aula aula : aule) {
                csvPrinter.printRecord(
                        aula.getNome(),
                        aula.getLuogo(),
                        aula.getEdificio(),
                        aula.getPiano(),
                        aula.getCapienza(),
                        aula.getPreseElettriche(),
                        aula.getPreseRete(),
                        aula.getNote(),
                        aula.getResponsabile().getEmail(),
                        attrezzature.get(aula)
                                .stream()
                                .map(Attrezzatura::getNumeroSerie) // Ottieni il numero di serie di ogni attrezzatura
                                .collect(Collectors.joining(", ")),
                        gruppi.get(aula)
                                .stream()
                                .map(Gruppo::getNome)
                                .collect(Collectors.joining(","))
                );
            }
        } catch (IOException ex) {
            Logger.getLogger(CSVExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new File(filePath);
    }
}
