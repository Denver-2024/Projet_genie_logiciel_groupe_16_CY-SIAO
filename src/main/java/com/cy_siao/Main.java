package com.cy_siao;

import com.cy_siao.dao.AddressDao;
import com.cy_siao.dao.BedDao;
import com.cy_siao.dao.PersonDao;
import com.cy_siao.dao.RoomDao;
import com.cy_siao.dao.StayDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.Room;
import com.cy_siao.model.Stay;
import com.cy_siao.model.person.Address;
import com.cy_siao.model.person.Gender;
import com.cy_siao.model.person.Person;
import com.cy_siao.util.DatabaseUtil;

import com.cy_siao.controller.CLIController;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        CLIController cliController = new CLIController();

        DatabaseUtil dbUtil = new DatabaseUtil();
        dbUtil.getConnection();
        System.out.println("Connected");

        PersonDao personDao = new PersonDao();
        AddressDao addressDao = new AddressDao();
        BedDao bedDao = new BedDao();
        RoomDao roomDao = new RoomDao();
        StayDao stayDao = new StayDao();

        // Test PersonDao
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setGender(Gender.M);
        person.setAge(30);
        person.setPlaceOfBirth("Paris");
        personDao.createPerson(person);
        System.out.println("Created Person: " + person.getId());

        Person foundPerson = personDao.findById(person.getId());
        System.out.println("Found Person by ID: " + (foundPerson != null ? foundPerson.getFirstName() : "null"));

        List<Person> allPersons = personDao.findAll();
        System.out.println("All Persons count: " + allPersons.size());

        person.setAge(31);
        personDao.updatePerson(person);
        System.out.println("Updated Person age to: " + person.getAge());

        // Test AddressDao
        Address address = new Address();
        address.setStreetNumber(10);
        address.setStreetName("Main Street");
        address.setPostalCode(75001);
        address.setCityName("Paris");
        addressDao.create(address);
        System.out.println("Created Address: " + address.getId());

        Address foundAddress = addressDao.findById(address.getId());
        System.out.println("Found Address by ID: " + (foundAddress != null ? foundAddress.getStreetName() : "null"));

        List<Address> allAddresses = addressDao.findAll();
        System.out.println("All Addresses count: " + allAddresses.size());

        address.setCityName("Lyon");
        addressDao.update(address);
        System.out.println("Updated Address city to: " + address.getCityName());

        // Test RoomDao
        Room room = new Room();
        room.setName("Room A");
        room.setNbBedsMax(2);
        roomDao.create(room);
        System.out.println("Created Room: " + room.getId());

        Room foundRoom = roomDao.findById(room.getId());
        System.out.println("Found Room by ID: " + (foundRoom != null ? foundRoom.getName() : "null"));

        List<Room> allRooms = roomDao.findAll();
        System.out.println("All Rooms count: " + allRooms.size());

        room.setName("Room B");
        roomDao.update(room);
        System.out.println("Updated Room name to: " + room.getName());

        // Test BedDao
        Bed bed = new Bed();
        bed.isDouble();
        // No setter for occupied, so we skip setting it directly
        bed.setIdRoom(room.getId());
        System.out.println("Created Bed: " + bed.getId());

        Bed foundBed = bedDao.findById(bed.getId());
        System.out.println("Found Bed by ID: " + (foundBed != null ? foundBed.getId() : "null"));

        List<Bed> allBeds = bedDao.findAll();
        System.out.println("All Beds count: " + allBeds.size());

        // No setter for occupied, so we skip updating it directly
        bedDao.update(bed);
        System.out.println("Updated Bed occupied to: " + bed.isOccupied());

        // Test StayDao
        Stay stay = new Stay();
        stay.setPerson(person);
        stay.setBed(bed);
        stay.setDateArrival(java.time.LocalDate.now());
        stay.setDateDeparture(java.time.LocalDate.now().plusDays(5));
        stayDao.create(stay);
        System.out.println("Created Stay: " + stay.getId());

        Stay foundStay = stayDao.findById(stay.getId());
        System.out.println("Found Stay by ID: " + (foundStay != null ? foundStay.getId() : "null"));

        Stay stayByPerson = stayDao.findByPersonId(person.getId());
        System.out.println("Found Stay by Person ID: " + (stayByPerson != null ? stayByPerson.getId() : "null"));

        Stay stayByBed = stayDao.findByBedId(bed.getId());
        System.out.println("Found Stay by Bed ID: " + (stayByBed != null ? stayByBed.getId() : "null"));

        List<Stay> allStays = stayDao.findAll();
        System.out.println("All Stays count: " + (allStays != null ? allStays.size() : "null"));

        stay.setDateDeparture(java.time.LocalDate.now().plusDays(7));
        stayDao.update(stay);
        System.out.println("Updated Stay departure date to: " + stay.getDateDeparture());

        // Cleanup: delete created entities
        stayDao.delete(stay.getId());
        System.out.println("Deleted Stay: " + stay.getId());

        bedDao.delete(bed.getId());
        System.out.println("Deleted Bed: " + bed.getId());

        roomDao.delete(room.getId());
        System.out.println("Deleted Room: " + room.getId());

        addressDao.delete(address.getId());
        System.out.println("Deleted Address: " + address.getId());

        personDao.deletePerson(person.getId());
        System.out.println("Deleted Person: " + person.getId());

        cliController.start();
    }
}
