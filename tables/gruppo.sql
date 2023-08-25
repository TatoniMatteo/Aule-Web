create table gruppo
(
    id           int auto_increment
        primary key,
    nome         varchar(255)  not null,
    descrizione  text          null,
    id_categoria int           not null,
    versione     int default 0 null,
    constraint gruppo_ibfk_1
        foreign key (id_categoria) references categoria (id)
            on update cascade
)
    charset = utf8mb4;

create index id_categoria
    on gruppo (id_categoria);

INSERT INTO auleweb.gruppo (id, nome, descrizione, id_categoria, versione) VALUES (1, 'Informatica', 'Dipartimento di Informatica', 1, 0);
INSERT INTO auleweb.gruppo (id, nome, descrizione, id_categoria, versione) VALUES (2, 'Economia', 'Dipartimento di Economia', 1, 1);
INSERT INTO auleweb.gruppo (id, nome, descrizione, id_categoria, versione) VALUES (3, 'Ingegneria', 'Dipartimento di Ingegneria', 1, 0);
INSERT INTO auleweb.gruppo (id, nome, descrizione, id_categoria, versione) VALUES (4, 'Scienze Umanistiche', 'Dipartimento di Scienze Umanistiche', 1, 0);
INSERT INTO auleweb.gruppo (id, nome, descrizione, id_categoria, versione) VALUES (5, 'Polo Tecnologico', 'Polo di ricerca tecnologica', 2, 0);
INSERT INTO auleweb.gruppo (id, nome, descrizione, id_categoria, versione) VALUES (6, 'Polo Culturale', 'Polo di ricerca culturale', 2, 0);
INSERT INTO auleweb.gruppo (id, nome, descrizione, id_categoria, versione) VALUES (7, 'Test', 'Test', 4, 1);
