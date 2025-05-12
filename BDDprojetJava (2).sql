-- Suppression des tables dans l'ordre inverse des dependances
DROP TABLE IF EXISTS AddressKnown;
DROP TABLE IF EXISTS RestrictionPerson;
DROP TABLE IF EXISTS RestrictionRoom;
DROP TABLE IF EXISTS Relationship;
DROP TABLE IF EXISTS Stay;
DROP TABLE IF EXISTS Address CASCADE;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Bed;
DROP TABLE IF EXISTS RestrictionType;
DROP TABLE IF EXISTS Room;

-- Room table
CREATE TABLE Room (
    Id SERIAL PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    NbBedsMax INT NOT NULL
);

-- RestrictionType table
CREATE TABLE RestrictionType (
    Id SERIAL PRIMARY KEY,
    Restriction VARCHAR(50) NOT NULL
);

-- Bed table
CREATE TABLE Bed (
    Id SERIAL PRIMARY KEY,
    NbPlacesMax INT NOT NULL,
    IdRoom INT NOT NULL,
    FOREIGN KEY (IdRoom) REFERENCES Room(Id) ON DELETE CASCADE
);

-- Person table
CREATE TABLE Person (
    Id SERIAL PRIMARY KEY,
    LastName VARCHAR(50) NOT NULL,
    FirstName VARCHAR(50) NOT NULL,
    Age INT CHECK (Age >= 0),
    Gender CHAR(1) CHECK (Gender IN ('M', 'F')),
    PlaceOfBirth VARCHAR(50),
    SocialSecurityNumber BIGINT UNIQUE
);

-- Address table
CREATE TABLE Address (
    Id SERIAL PRIMARY KEY,
    StreetNumber INT,
    StreetName VARCHAR(50),
    PostalCode INT,
    CityName VARCHAR(50),
    DepartmentName VARCHAR(50),
    Region VARCHAR(50)
);

-- Stay table (many-to-many between Bed and Person)
CREATE TABLE Stay (
    IdBed INT NOT NULL,
    IdPerson INT NOT NULL,
    DateArrival DATE NOT NULL,
    DateDeparture DATE,
    PRIMARY KEY (IdBed, IdPerson),
    FOREIGN KEY (IdBed) REFERENCES Bed(Id) ON DELETE CASCADE,
    FOREIGN KEY (IdPerson) REFERENCES Person(Id) ON DELETE CASCADE
);

-- Relationship table (self-reference on Person)
CREATE TABLE Relationship (
    IdPerson1 INT NOT NULL,
    IdPerson2 INT NOT NULL,
    RelationType VARCHAR(50),
    PRIMARY KEY (IdPerson1, IdPerson2),
    FOREIGN KEY (IdPerson1) REFERENCES Person(Id) ON DELETE CASCADE,
    FOREIGN KEY (IdPerson2) REFERENCES Person(Id) ON DELETE CASCADE
);

-- RestrictionRoom table (many-to-many)
CREATE TABLE RestrictionRoom (
    IdRoom INT NOT NULL,
    IdRestriction INT NOT NULL,
    PRIMARY KEY (IdRoom, IdRestriction),
    FOREIGN KEY (IdRoom) REFERENCES Room(Id) ON DELETE CASCADE,
    FOREIGN KEY (IdRestriction) REFERENCES RestrictionType(Id) ON DELETE CASCADE
);

-- RestrictionPerson table (many-to-many)
CREATE TABLE RestrictionPerson (
    IdPerson INT NOT NULL,
    IdRestriction INT NOT NULL,
    PRIMARY KEY (IdPerson, IdRestriction),
    FOREIGN KEY (IdPerson) REFERENCES Person(Id) ON DELETE CASCADE,
    FOREIGN KEY (IdRestriction) REFERENCES RestrictionType(Id) ON DELETE CASCADE
);

-- AddressKnown table (many-to-many between Person and Address)
CREATE TABLE AddressKnown (
    IdPerson INT NOT NULL,
    IdAddress INT NOT NULL,
    PRIMARY KEY (IdPerson, IdAddress),
    FOREIGN KEY (IdPerson) REFERENCES Person(Id) ON DELETE CASCADE,
    FOREIGN KEY (IdAddress) REFERENCES Address(Id) ON DELETE CASCADE
);
