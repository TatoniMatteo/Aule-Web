package com.univaq.project.auleweb.data.exportData;

import com.univaq.project.auleweb.data.model.Evento;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

public class ICalendarExporter {

    public static File exportEventiToICalendar(List<Evento> eventi, String filePath) {
        try ( FileOutputStream fout = new FileOutputStream(filePath)) {
            Calendar calendar = new Calendar();

            calendar.getProperties().add(Version.VERSION_2_0);
            calendar.getProperties().add(CalScale.GREGORIAN);
            calendar.getProperties().add(Method.PUBLISH);

            for (Evento evento : eventi) {
                VEvent vEvent = new VEvent();
                vEvent.getProperties().add(new Uid(evento.getKey().toString()));
                vEvent.getProperties().add(new Summary(evento.getNome()));
                vEvent.getProperties().add(new Description(evento.getDescrizione()));
                vEvent.getProperties().add(new Location(evento.getAula().getNome()));
                vEvent.getProperties().add(new Organizer("mailto:" + evento.getResponsabile().getEmail()));

                DateTime start = new DateTime(evento.getData().getTime() + evento.getOraInizio().getTime());
                DateTime end = new DateTime(evento.getData().getTime() + evento.getOraFine().getTime());
                vEvent.getProperties().add(new DtStart(start));
                vEvent.getProperties().add(new DtEnd(end));

                vEvent.getProperties().add(new net.fortuna.ical4j.model.property.TzId(TimeZone.getTimeZone("Europe/Rome").getID()));

                calendar.getComponents().add(vEvent);
            }

            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);
            return new File(filePath);

        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ICalendarExporter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
