use auleweb;

-- Popolamento tabella Responsabile
INSERT INTO Responsabile (nome, cognome, email)
VALUES ('Mario', 'Rossi', 'mario.rossi@example.com'),
       ('Laura', 'Bianchi', 'laura.bianchi@example.com'),
       ('Luca', 'Verdi', 'luca.verdi@example.com'),
       ('Anna', 'Gialli', 'anna.gialli@example.com');

-- Popolamento tabella Corso
INSERT INTO Corso (nome, descrizione, corso_laurea)
VALUES ('Informatica', 'Corso di laurea in Informatica', 'Informatica'),
       ('Economia', 'Corso di laurea in Economia', 'Scienze Economiche'),
       ('Ingegneria', 'Corso di laurea in Ingegneria', 'Ingegneria'),
       ('Lettere', 'Corso di laurea in Lettere', 'Lettere');

-- Popolamento tabella Categoria
INSERT INTO Categoria (nome)
VALUES ('dipartimenti'),
       ('poli');

-- Popolamento tabella Gruppo
INSERT INTO Gruppo (nome, descrizione, id_categoria)
VALUES ('Informatica', 'Dipartimento di Informatica', 1),
       ('Economia', 'Dipartimento di Economia', 1),
       ('Ingegneria', 'Dipartimento di Ingegneria', 1),
       ('Scienze Umanistiche', 'Dipartimento di Scienze Umanistiche', 1),
       ('Polo Tecnologico', 'Polo di ricerca tecnologica', 2),
       ('Polo Culturale', 'Polo di ricerca culturale', 2);

-- Popolamento tabella Aula
INSERT INTO Aula (nome, luogo, edificio, piano, capienza, prese_elettriche, prese_rete, id_responsabile)
VALUES ('Aula 101', 'Via Roma 1', 'Edificio A', 1, 50, 20, 10, 1),
       ('Aula 201', 'Via Roma 1', 'Edificio A', 2, 40, 15, 8, 2),
       ('Aula 301', 'Via Milano 2', 'Edificio B', 3, 30, 10, 6, 3);

-- Popolamento tabella Aula_Gruppo
INSERT INTO Aula_Gruppo (id_aula, id_gruppo)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (1, 4),
       (2, 5),
       (3, 6);

-- Popolamento tabella Attrezzatura
INSERT INTO Attrezzatura (nome, numero_serie, id_aula)
VALUES ('Proiettore', 'P12345', 1),
       ('Lavagna', 'L54321', 2),
       ('Computer', 'C67890', 3),
       ('Microfono', 'M45678', 1);

-- Popolamento tabella Evento
INSERT INTO Evento (id_ricorrenza, nome, descrizione, data, ora_inizio, ora_fine, id_corso, id_responsabile, id_aula,
                    tipo_evento)
VALUES (NULL, 'Lezione 1', 'Prima lezione di Informatica', '2023-08-15', '09:00:00', '11:00:00', 1, 1, 1, 'Lezione'),
       (NULL, 'Seminario Finanza', 'Seminario sulla finanza aziendale', '2023-08-20', '14:00:00', '16:00:00', NULL, 2,
        2, 'Seminario'),
       (NULL, 'Lab Elettronica', 'Laboratorio di elettronica', '2023-08-18', '10:00:00', '12:00:00', 3, 3, 3,
        'Laboratorio'),
       (NULL, 'Conferenza Informatica', 'Conferenza su nuove tecnologie', '2023-08-25', '15:00:00', '18:00:00', NULL, 4,
        1, 'Conferenza');

-- Popolamento tabella Amministratore
INSERT INTO Amministratore (username, password, email)
VALUES ('admin', 'adminpassword', 'admin@example.com');
