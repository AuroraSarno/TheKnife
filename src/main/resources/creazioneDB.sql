CREATE TABLE Utenti
(
    username     varchar(50) PRIMARY KEY,
    nome         varchar(30),
    cognome      varchar(30),
    password     varchar(300),
    domicilio    varchar(30),
    data_nascita date,
    ruolo        varchar(11) CHECK (ruolo IN ('Cliente', 'Ristoratore'))
)

CREATE TABLE Recensioni
(
    id_recensione INT PRIMARY KEY NOT NULL,
    testo         VARCHAR(250),
    stelle        INT             NOT NULL CHECK (stelle >= 1 AND stelle <= 5),
    risposta      VARCHAR(250),
    id_ristorante INT             NOT NULL,
    username      VARCHAR(50)     NOT NULL,
    FOREIGN KEY (id_ristorante) REFERENCES Ristoranti (id_ristorante),
    FOREIGN KEY (username) REFERENCES Clienti (username)
)