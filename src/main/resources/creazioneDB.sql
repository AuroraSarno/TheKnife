CREATE TABLE Utenti (
    username varchar(50) PRIMARY KEY,
    nome varchar(30),
    cognome varchar(30),
    password varchar(300),
    domicilio varchar(30),
    data_nascita date,
    ruolo varchar (11) CHECK (ruolo IN ('Cliente', 'Ristoratore'))
)