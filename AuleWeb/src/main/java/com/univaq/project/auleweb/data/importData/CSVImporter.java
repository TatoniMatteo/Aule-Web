package com.univaq.project.auleweb.data.importData;

import com.univaq.project.auleweb.data.implementation.AulaImpl;
import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Attrezzatura;
import com.univaq.project.auleweb.data.model.Aula;
import com.univaq.project.auleweb.data.model.Gruppo;
import com.univaq.project.auleweb.data.model.Responsabile;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.security.SecurityHelpers;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVImporter {

    private static final String[] HEADER_AULA = {"nome", "luogo", "edificio", "piano", "capienza", "prese_elettriche", "prese_rete", "note", "responsabile", "attrezzature", "gruppi"};

    public static void importAuleFromCSV(File tempFile, DataLayerImpl dataLayer) throws DataException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADER_AULA)
                .setSkipHeaderRecord(true)
                .build();

        try ( FileReader fileReader = new FileReader(tempFile);  CSVParser csvParser = new CSVParser(fileReader, csvFormat)) {

            for (CSVRecord record : csvParser) {
                Aula aula = new AulaImpl();
                List<Integer> gruppiKeys = new ArrayList<>();
                List<Integer> attrezzatureKeys = new ArrayList<>();

                aula.setNome(record.get("nome"));
                aula.setLuogo(record.get("luogo"));
                aula.setEdificio(record.get("edificio"));
                aula.setPiano(SecurityHelpers.checkNumeric(record.get("piano")));
                aula.setCapienza(SecurityHelpers.checkNumeric(record.get("capienza")));
                aula.setPreseElettriche(SecurityHelpers.checkNumeric(record.get("prese_elettriche")));
                aula.setPreseRete(SecurityHelpers.checkNumeric(record.get("prese_rete")));
                aula.setNote(record.get("note"));

                Responsabile responsabile = dataLayer.getResponsabiliDAO().getResponsabileByEmail(record.get("responsabile"));
                if (responsabile == null) {
                    throw new DataException("Il responsabile con email " + record.get("responsabile") + " non è nel sistema!");
                }

                aula.setResponsabile(responsabile);

                for (String gruppoName : record.get("gruppi").split(",")) {
                    Gruppo gruppo = dataLayer.getGruppiDAO().getGruppoByName(gruppoName);
                    if (gruppo == null) {
                        throw new DataException("Il gruppo con nome " + gruppoName + " non è nel sistema!");
                    }
                    gruppiKeys.add(gruppo.getKey());
                }

                for (String attrezzaturaCode : record.get("attrezzature").split(",")) {
                    Attrezzatura attrezzatura = dataLayer.getAttrezzatureDAO().getAttrezzaturaByCode(attrezzaturaCode.trim());
                    if (attrezzatura == null) {
                        throw new DataException("L'attrezzatura con codice " + attrezzaturaCode + " non è nel sistema!");

                    } else if (attrezzatura.getAula() != null) {
                        throw new DataException("L'attrezzatura con codice " + attrezzaturaCode + " è già stata assegnata!");
                    }
                    attrezzatureKeys.add(attrezzatura.getKey());
                }
                dataLayer.getAuleDAO().storeAula(aula, gruppiKeys, attrezzatureKeys);
            }

        } catch (IOException ex) {
            throw new DataException("Impossibile importare una o più aule!");
        }
    }
}
