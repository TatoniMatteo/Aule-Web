create table categoria
(
    id       int auto_increment
        primary key,
    nome     varchar(255)  not null,
    versione int default 0 null
)
    charset = utf8mb4;

INSERT INTO auleweb.categoria (id, nome, versione) VALUES (1, 'Dipartimenti', 0);
INSERT INTO auleweb.categoria (id, nome, versione) VALUES (2, 'Poli', 0);
INSERT INTO auleweb.categoria (id, nome, versione) VALUES (3, 'Test', 0);
INSERT INTO auleweb.categoria (id, nome, versione) VALUES (4, 'Test1', 0);
