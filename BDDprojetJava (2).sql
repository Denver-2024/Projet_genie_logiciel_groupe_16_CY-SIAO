-- Suppression des tables dans l'ordre inverse des dependances
DROP TABLE IF EXISTS Knows;
DROP TABLE IF EXISTS RestrictionRoom;
DROP TABLE IF EXISTS Relationship;
DROP VIEW Planning;
DROP TABLE IF EXISTS Stay;
DROP TABLE IF EXISTS Bed;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS RestrictionType;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS Person;

-- Creation des tables

CREATE TABLE Address (
                         Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY CHECK (Id > 0),
                         streetNumber INT CHECK (streetNumber BETWEEN 1 AND 10000) NOT NULL,
                         streetName VARCHAR(50) NOT NULL,
                         postalCode INT CHECK (postalCode > 0 AND LENGTH(postalCode::TEXT) = 5),
                         cityName VARCHAR(50) NOT NULL
);

CREATE TABLE Person (
                        Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY CHECK (Id > 0),
                        lastName VARCHAR(50) NOT NULL,
                        firstName VARCHAR(50) NOT NULL,
                        age INT CHECK (age BETWEEN 0 AND 200),
                        gender CHAR(1) CHECK (gender IN ('F', 'M')) NOT NULL,
                        placeOfBirth VARCHAR(50),
                        socialSecurityNumber BIGINT CHECK (socialSecurityNumber > 0 AND LENGTH(socialSecurityNumber::TEXT) = 13)
);

CREATE TABLE RestrictionType (
                                 Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY CHECK (Id > 0),
                                 label VARCHAR(50) NOT NULL,
                                 minAge INT CHECK (minAge BETWEEN 0 AND 200),
                                 maxAge INT CHECK (maxAge BETWEEN 0 AND 200),
                                 CHECK(minAge <= maxAge),
                                 genderRestriction CHAR(1) CHECK (genderRestriction IN ('F', 'M'))
);

CREATE TABLE Room (
                      Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY CHECK (Id > 0),
                      name VARCHAR(50),
                      nbBedsMax INT CHECK (nbBedsMax >= 0 AND nbBedsMax <= 10) DEFAULT 0
);

CREATE TABLE Bed (
                     Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY CHECK (Id > 0),
                     nbPlacesMax INT CHECK (nbPlacesMax IN (1, 2)) DEFAULT 1,
                     IdRoom INT REFERENCES Room(Id)
);

CREATE TABLE RestrictionRoom (
                                 IdRoom INT REFERENCES Room(Id),
                                 IdRestrictionType INT REFERENCES RestrictionType(Id),
                                 logicOperator VARCHAR(3) CHECK (logicOperator IN ('AND', 'OR')),
                                 PRIMARY KEY (IdRoom, IdRestrictionType)
);

CREATE TABLE Relationship (
                              IdPerson1 INT REFERENCES Person(Id),
                              IdPerson2 INT REFERENCES Person(Id),
                              relationType VARCHAR(50) DEFAULT 'Not Specified',
                              PRIMARY KEY (IdPerson1, IdPerson2)
);

CREATE TABLE Knows (
                       IdPerson INT REFERENCES Person(Id),
                       IdAddress INT REFERENCES Address(Id),
                       PRIMARY KEY (IdPerson, IdAddress)
);

CREATE TABLE Stay (
                      Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY CHECK (Id > 0),
                      IdPerson INT REFERENCES Person(Id),
                      IdBed INT REFERENCES Bed(Id),
                      dateArrival DATE CHECK (dateArrival >= CURRENT_DATE - INTERVAL '2 years'),
                      dateDeparture DATE CHECK (dateDeparture > dateArrival),
                      hasLeft BOOLEAN DEFAULT FALSE
);

CREATE VIEW Planning AS
SELECT
    s.Id AS idStay,
    b.Id AS idBed,
    r.name AS roomName,
    p.firstName || ' ' || p.lastName AS personName,
    s.dateArrival,
    s.dateDeparture,
    s.hasLeft
FROM Stay s
         JOIN Bed b ON s.IdBed = b.Id
         JOIN Room r ON b.IdRoom = r.Id
         JOIN Person p ON s.IdPerson = p.Id;

-- Insertion des donnees

INSERT INTO Address (streetNumber, streetName, postalCode, cityName) VALUES
                                                                         (12, 'Rue des Lilas', 75001, 'Paris'),
                                                                         (45, 'Avenue Victor Hugo', 69006, 'Lyon'),
                                                                         (89, 'Boulevard Haussmann', 13008, 'Marseille');

INSERT INTO Person (lastName, firstName, age, gender, placeOfBirth, socialSecurityNumber) VALUES
                                                                                              ('Dupont', 'Alice', 30, 'F', 'Paris', 2901012345678),
                                                                                              ('Martin', 'Jean', 45, 'M', 'Lyon', 1804012345678),
                                                                                              ('Durand', 'Sophie', 12, 'F', 'Marseille', 1212012345678),
                                                                                              ('Petit', 'Marc', 80, 'M', 'Nice', 4403012345678);

INSERT INTO Knows (IdPerson, IdAddress) VALUES
                                            (1, 1),
                                            (2, 2),
                                            (3, 1),
                                            (4, 3);

INSERT INTO RestrictionType (label, minAge, maxAge, genderRestriction) VALUES
                                                                           ('Femmes uniquement', 0, 200, 'F'),
                                                                           ('Hommes uniquement', 18, 65, 'M'),
                                                                           ('Mineurs', 0, 17, 'F');

INSERT INTO Room (name, nbBedsMax) VALUES
                                       ('Chambre 101', 2),
                                       ('Chambre 102', 3),
                                       ('Chambre 103', 1);

INSERT INTO Bed (nbPlacesMax, IdRoom) VALUES
                                          (1, 1),
                                          (2, 1),
                                          (1, 2),
                                          (1, 2),
                                          (1, 3);

INSERT INTO RestrictionRoom (IdRoom, IdRestrictionType, logicOperator) VALUES
                                                                           (1, 1, 'AND'),
                                                                           (2, 2, 'AND'),
                                                                           (3, 3, 'AND');

INSERT INTO Relationship (IdPerson1, IdPerson2, relationType) VALUES
                                                                  (1, 3, 'Mere'),
                                                                  (2, 4, 'Frere'),
                                                                  (3, 4, 'Ami');

-- Sejours (passes, presents et futurs)

INSERT INTO Stay (IdPerson, IdBed, dateArrival, dateDeparture, hasLeft) VALUES
                                                                            (1, 1, CURRENT_DATE - INTERVAL '10 day', CURRENT_DATE - INTERVAL '5 day', TRUE),
                                                                            (2, 3, CURRENT_DATE - INTERVAL '7 day', CURRENT_DATE - INTERVAL '2 day', TRUE),
                                                                            (3, 5, CURRENT_DATE - INTERVAL '5 day', CURRENT_DATE + INTERVAL '1 day', TRUE),
                                                                            (4, 2, CURRENT_DATE - INTERVAL '1 day', CURRENT_DATE + INTERVAL '2 day', FALSE),
                                                                            (1, 4, CURRENT_DATE + INTERVAL '3 day', CURRENT_DATE + INTERVAL '10 day', FALSE);
