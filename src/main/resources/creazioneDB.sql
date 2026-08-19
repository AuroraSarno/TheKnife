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

CREATE TABLE Ristoranti
(
    id_ristorante serial PRIMARY KEY ,
    nome varchar(100) NOT NULL,
    indirizzo varchar(255) NOT NULL,
    latitudine double precision NOT NULL,
    longitudine double precision NOT NULL,
    prezzoMedio int NOT NULL,
    delivery boolean NOT NULL,
    prenotazione boolean NOT NULL,
    tipoCucina varchar(50) NOT NULL,
    stelle double precision,
    usernameRistoratore varchar(50) REFERENCES Utenti(username) ON DELETE CASCADE,
    id_citta integer REFERENCES Citta(id_citta)
)

CREATE TABLE Recensioni
(
    id_recensione SERIAL PRIMARY KEY NOT NULL,
    testo         VARCHAR(250),
    stelle        INT             NOT NULL CHECK (stelle >= 1 AND stelle <= 5),
    risposta      VARCHAR(250),
    id_ristorante INT             NOT NULL,
    username      VARCHAR(50)     NOT NULL,
    FOREIGN KEY (id_ristorante) REFERENCES Ristoranti (id_ristorante) ON DELETE CASCADE,
    FOREIGN KEY (username) REFERENCES Utenti (username) ON DELETE CASCADE
)

CREATE TABLE Preferiti
(
    id_ristorante INT,
    username VARCHAR(50),
    PRIMARY KEY (id_ristorante, username),
    FOREIGN KEY (id_ristorante) REFERENCES Ristoranti(id_ristorante) ON DELETE CASCADE,
    FOREIGN KEY (username) REFERENCES Utenti(username) ON DELETE CASCADE
)

CREATE TABLE Citta(
    id_citta serial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    nazione varchar(100) NOT NULL,
    latitudine double precision NOT NULL,
    longitudine double precision NOT NULL
)