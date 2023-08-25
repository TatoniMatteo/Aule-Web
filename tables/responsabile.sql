create table responsabile
(
    id       int auto_increment
        primary key,
    nome     varchar(255)  not null,
    cognome  varchar(255)  not null,
    email    varchar(255)  null,
    versione int default 0 null
)
    charset = utf8mb4;

INSERT INTO auleweb.responsabile (id, nome, cognome, email, versione) VALUES (1, 'Mario', 'Rossi', 'mario.rossi@example.com', 0);
INSERT INTO auleweb.responsabile (id, nome, cognome, email, versione) VALUES (2, 'Laura', 'Bianchi', 'laura.bianchi@example.com', 0);
INSERT INTO auleweb.responsabile (id, nome, cognome, email, versione) VALUES (3, 'Luca', 'Verdi', 'luca.verdi@example.com', 0);
INSERT INTO auleweb.responsabile (id, nome, cognome, email, versione) VALUES (4, 'Anna', 'Gialli', 'anna.gialli@example.com', 1);
INSERT INTO auleweb.responsabile (id, nome, cognome, email, versione) VALUES (5, 'Piero', 'Pelu', 'pieropelu@example.com', 0);
