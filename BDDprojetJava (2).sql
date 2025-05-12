
DROP TABLE IF EXISTS AdressKnows;
DROP TABLE IF EXISTS RestrictionPerson;
DROP TABLE IF EXISTS RestrictionRoom;
DROP TABLE IF EXISTS Relationship;
DROP TABLE IF EXISTS Stay;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Bed;
DROP TABLE IF EXISTS RestrictionType;
DROP TABLE IF EXISTS Room;

-- Room table
CREATE TABLE Room (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    nbBedsMax INT NOT NULL
);

-- RestrictionType table
CREATE TABLE RestrictionType (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    restriction VARCHAR(50) NOT NULLS
);

-- Bed table
CREATE TABLE Bed (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    nbPlacesMax INT NOT NULL,
    idRoom INT NOT NULL,
    FOREIGN KEY (idRoom) REFERENCES Room(Id)
);

-- Person table
CREATE TABLE Person (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    lastName VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    age INT,
    gender CHAR(1) CHECK (gender IN ('M', 'F')),
    placeOfBirth VARCHAR(50),
    socialSecurityNumber INT
);

-- AddressKnown table
CREATE TABLE Address (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    streetNumber INT,
    streetName VARCHAR(50),
    PostalCode INT,
    cityName VARCHAR(50),
    departmentName VARCHAR(50),
    region VARCHAR(50)
);

-- Stay table
CREATE TABLE Stay (
    idBed INT NOT NULL,
    idPerson INT NOT NULL,
    DateArrival DATE NOT NULL,
    DateDeparture DATE,
    PRIMARY KEY (idBed, idPerson),
    FOREIGN KEY (idBed) REFERENCES Bed(Id),
    FOREIGN KEY (idPerson) REFERENCES Person(Id)
);

-- Relationship table
CREATE TABLE Relationship (
    IdPerson1 INT NOT NULL,
    IdPerson2 INT NOT NULL,
    relationType VARCHAR(50),
    PRIMARY KEY (IdPerson1, IdPerson2),
    FOREIGN KEY (IdPerson1) REFERENCES Person(Id),
    FOREIGN KEY (IdPerson2) REFERENCES Person(Id)
);

-- RestrictionRoom table 
CREATE TABLE RestrictionRoom (
    IdRoom INT NOT NULL,
    IdRestriction INT NOT NULL,
    PRIMARY KEY (IdRoom, IdRestriction),
    FOREIGN KEY (IdRoom) REFERENCES Room(Id),
    FOREIGN KEY (IdRestriction) REFERENCES RestrictionType(Id)
);

-- RestrictionPerson table
CREATE TABLE RestrictionPerson (
    IdPerson INT NOT NULL,
    IdRestriction INT NOT NULL,
    PRIMARY KEY (IdPerson, IdRestriction),
    FOREIGN KEY (IdPerson) REFERENCES Person(Id),
    FOREIGN KEY (IdRestriction) REFERENCES RestrictionType(Id)
);

-- Knows table
CREATE TABLE AdressKnows (
    IdPerson INT NOT NULL,
    IdAddressKnown INT NOT NULL,
    PRIMARY KEY (IdPerson, IdAddressKnown),
    FOREIGN KEY (IdPerson) REFERENCES Person(Id),
    FOREIGN KEY (IdAddressKnown) REFERENCES Address(Id)
);