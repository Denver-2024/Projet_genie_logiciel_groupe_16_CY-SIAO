--Suppression des tables dans l'ordre inverse des dependances
DROP TABLE IF EXISTS Knows;
DROP TABLE IF EXISTS RestrictionRoom;
DROP TABLE IF EXISTS Relationship;
DROP TABLE IF EXISTS Stay;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS RestrictionType;
DROP TABLE IF EXISTS Bed;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Room;

--Creation des tables

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
    Id INT REFERENCES Room(Id) NOT NULL
);


CREATE TABLE RestrictionRoom (
    IdRoom INT REFERENCES Room(Id),
    IdRestriction INT REFERENCES RestrictionType(Id),
    PRIMARY KEY (IdRoom, IdRestriction)
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
    dateArrival DATE CHECK (dateArrival >= CURRENT_DATE),
    dateDeparture DATE CHECK (dateDeparture > dateArrival)
);

