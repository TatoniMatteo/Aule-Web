USE auleweb;

-- Inserimento dati casuali nella tabella Responsabile
INSERT INTO Responsabile (nome, cognome, email)
VALUES ('Mario', 'Rossi', 'mario.rossi@example.com'),
       ('Laura', 'Bianchi', 'laura.bianchi@example.com'),
       ('Luigi', 'Verdi', 'luigi.verdi@example.com'),
       ('Anna', 'Russo', 'anna.russo@example.com'),
       ('Giovanni', 'Ferrari', 'giovanni.ferrari@example.com'),
       ('Elena', 'Martini', 'elena.martini@example.com'),
       ('Alessio', 'Gallo', 'alessio.gallo@example.com'),
       ('Valentina', 'Conti', 'valentina.conti@example.com'),
       ('Marco', 'Greco', 'marco.greco@example.com'),
       ('Chiara', 'Fontana', 'chiara.fontana@example.com');

-- Inserimento dati casuali nella tabella Corso
INSERT INTO Corso (nome, descrizione, corso_laurea)
VALUES ('Programmazione Web', 'Corso di programmazione web avanzata', 'Informatica'),
       ('Marketing Digitale', 'Corso di marketing digitale e social media', 'Economia'),
       ('Design Grafico', 'Corso di design grafico e UX/UI', 'Design'),
       ('Lingua Inglese', 'Corso di lingua inglese livello intermedio', 'Lingue'),
       ('Analisi dei Dati', 'Corso di analisi dei dati e data science', 'Informatica'),
       ('Gestione Progetti', 'Corso di gestione progetti e project management', 'Economia'),
       ('Fotografia', 'Corso di fotografia digitale e ritocco', 'Arte'),
       ('Diritto Commerciale', 'Corso di diritto commerciale e legge', 'Giurisprudenza'),
       ('Scrittura Creativa', 'Corso di scrittura creativa e storytelling', 'Lettere'),
       ('Gestione Risorse Umane', 'Corso di gestione delle risorse umane', 'Psicologia');

-- Inserimento dati casuali nella tabella Aula
INSERT INTO Aula (nome, luogo, edificio, piano, capienza, prese_elettriche, prese_rete, note, id_responsabile)
VALUES ('Aula 101', 'Campus Universitario', 'Edificio A', 1, 50, 15, 5, 'Aula dotata di proiettore', 1),
       ('Aula 102', 'Campus Universitario', 'Edificio A', 2, 40, 12, 4, 'Aula con lavagna interattiva', 2),
       ('Aula 201', 'Campus Universitario', 'Edificio B', 1, 30, 10, 3, 'Aula con aria condizionata', 3),
       ('Aula 202', 'Campus Universitario', 'Edificio B', 2, 35, 12, 4, 'Aula con postazioni computer', 4),
       ('Aula 301', 'Campus Universitario', 'Edificio C', 1, 25, 8, 2, 'Aula adatta per lezioni teoriche', 5),
       ('Aula 302', 'Campus Universitario', 'Edificio C', 2, 45, 14, 5, 'Aula con accesso a internet', 6),
       ('Aula 401', 'Campus Universitario', 'Edificio D', 1, 60, 20, 6, 'Aula con proiettore HD', 7),
       ('Aula 402', 'Campus Universitario', 'Edificio D', 2, 55, 18, 5, 'Aula con sistema audio', 8),
       ('Aula 501', 'Campus Universitario', 'Edificio E', 1, 20, 6, 2, 'Aula riservata per workshop', 9),
       ('Aula 502', 'Campus Universitario', 'Edificio E', 2, 30, 10, 3, 'Aula attrezzata per videoconferenze', 10);

-- Inserimento dati casuali nella tabella Gruppo
INSERT INTO Gruppo (nome, descrizione)
VALUES ('Gruppo Studio', 'Gruppo di studio per materie scientifiche'),
       ('Gruppo Sport', 'Gruppo dedicato agli sport universitari'),
       ('Gruppo Fotografia', 'Gruppo di appassionati di fotografia'),
       ('Gruppo Coding', 'Gruppo di programmatori'),
       ('Gruppo Teatro', 'Gruppo teatrale per recite universitarie'),
       ('Gruppo Lingue', 'Gruppo per l\'apprendimento delle lingue straniere'),
       ('Gruppo Arte', 'Gruppo di pittura e arte'),
       ('Gruppo Cinema', 'Gruppo dedicato alla visione di film e cinema'),
       ('Gruppo Musica', 'Gruppo di appassionati di musica'),
       ('Gruppo Danza', 'Gruppo di ballo e danza');

-- Inserimento dati casuali nella tabella Aula_Gruppo
INSERT INTO Aula_Gruppo (id_aula, id_gruppo)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- Inserimento dati casuali nella tabella Attrezzatura
INSERT INTO Attrezzatura (nome, numero_serie, id_aula)
VALUES ('Proiettore', 'PS1001', 1),
       ('Lavagna Luminosa', 'LI2002', 2),
       ('Computer', 'CM3003', 4),
       ('Computer', 'PC4004', 5),
       ('Proiettore', 'PH5005', 7),
       ('Impianto Audio', 'SA6006', 8),
       ('Proiettore', 'PS7007', 10),
       ('Lavagna Luminosa', 'LI8008', 10);


-- Inserimento dati casuali nella tabella Evento
INSERT INTO Evento (nome, descrizione, data, ora_inizio, ora_fine, id_corso, id_responsabile, id_aula, tipo_evento)
VALUES ('Lezione di Programmazione Web', 'Lezione di programmazione avanzata', '2023-08-10', '09:00', '11:00', 1, 1, 1,
        'Lezione'),
       ('Lezione di Marketing Digitale', 'Lezione di marketing e social media', '2023-08-11', '14:00', '16:00', 2, 2,
        2, 'Lezione'),
       ('Lezione di Design Grafico', 'Lezione di design grafico e UX/UI', '2023-08-12', '10:00', '12:00', 3, 3, 3,
        'Lezione'),
       ('Lezione di Lingua Inglese', 'Lezione di inglese livello intermedio', '2023-08-13', '11:00', '13:00', 4, 4,
        4, 'Lezione'),
       ('Lezione di Analisi dei Dati', 'Lezione di analisi dei dati e data science', '2023-08-14', '15:00', '17:00',
        5, 5, 5, 'Lezione'),
       ('Lezione di Gestione Progetti', 'Lezione di project management', '2023-08-15', '10:00', '12:00', 6, 6, 6,
        'Lezione'),
       ('Corso di Fotografia', 'Corso di fotografia digitale e ritocco', '2023-08-16', '16:00', '18:00', 7, 7, 7,
        'Corso'),
       ('Corso di Diritto Commerciale', 'Corso di legge e diritto commerciale', '2023-08-17', '09:00', '11:00', 8, 8,
        8, 'Corso'),
       ('Corso di Scrittura Creativa', 'Corso di storytelling e scrittura creativa', '2023-08-18', '14:00', '16:00',
        9, 9, 9, 'Corso'),
       ('Lezione di Gestione Risorse Umane', 'Lezione di gestione del personale', '2023-08-19', '13:00', '15:00', 10,
        10, 10, 'Lezione');
