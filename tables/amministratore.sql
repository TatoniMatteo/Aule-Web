create table amministratore
(
    id       int auto_increment
        primary key,
    username varchar(255)  not null,
    password varchar(255)  not null,
    email    varchar(255)  not null,
    versione int default 0 null
)
    charset = utf8mb4;

INSERT INTO auleweb.amministratore (id, username, password, email, versione) VALUES (1, 'Matteo', 'f8360d6f939bb6125a853f54ca17309eab1d892a4ec8ec4b90e7ab871443d289bb01a5ca3207d964f5c3be1c8df417dc', 'matteo.tatoni@student.univaq.it', 0);
INSERT INTO auleweb.amministratore (id, username, password, email, versione) VALUES (2, 'Davide', 'f8360d6f939bb6125a853f54ca17309eab1d892a4ec8ec4b90e7ab871443d289bb01a5ca3207d964f5c3be1c8df417dc', 'davide.dirocco@student.univaq.it', 0);
