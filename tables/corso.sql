create table corso
(
    id           int auto_increment
        primary key,
    nome         varchar(255)  not null,
    descrizione  text          null,
    corso_laurea varchar(255)  null,
    versione     int default 0 null
)
    charset = utf8mb4;

INSERT INTO auleweb.corso (id, nome, descrizione, corso_laurea, versione) VALUES (1, 'Base di Dati', 'Corso di Base di Dati', 'Informatica', 0);
INSERT INTO auleweb.corso (id, nome, descrizione, corso_laurea, versione) VALUES (2, 'Ingegneria del Web', 'Corso di Ingegneria del Web', 'Informatica', 0);
INSERT INTO auleweb.corso (id, nome, descrizione, corso_laurea, versione) VALUES (3, 'Anatomia 1', 'Questo corso introduttivo di Anatomia 1 offre agli studenti della facoltÃ  di medicina una panoramica dettagliata dell\'anatomia umana. Gli studenti esploreranno la struttura e la funzione del corpo umano attraverso una combinazione di lezioni teoriche, disegni anatomici, esami di laboratorio e progetti di ricerca. Il corso copre una vasta gamma di argomenti, tra cui l\'anatomia degli organi interni, del sistema scheletrico e muscolare, e del sistema nervoso. Gli studenti avranno l\'opportunitÃ  di acquisire una conoscenza approfondita dell\'anatomia attraverso l\'analisi di preparati anatomici, modelli tridimensionali e tecnologie avanzate come le scansioni MRI. Al termine del corso, gli studenti saranno in grado di applicare le loro conoscenze anatomiche alla pratica medica e alla diagnosi.', 'Medicina', 2);
INSERT INTO auleweb.corso (id, nome, descrizione, corso_laurea, versione) VALUES (4, 'Farmacologia', 'Corso di Farmacologia', 'Medicina', 0);
INSERT INTO auleweb.corso (id, nome, descrizione, corso_laurea, versione) VALUES (5, 'Web Engeneering', 'Il corso si propone di fornire una preparazione completa sulle tecnologie necessarie allo sviluppo di applicazioni web moderne, conformi agli standard e sicure, con particolare riguardo anche per le problematiche di accessibilitÃ  e usabilitÃ ', 'Informatica', 0);
