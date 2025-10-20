package campus_app.app;
import campus_app.entity.service.*;
import campus_app.exceptions.*;
import dataStructures.*;
import campus_app.entity.student.Student;

import java.nio.file.Files;
import java.nio.file.Path;

public class CampusAppClass implements CampusApp {
    StudentStorage students;
    ServiceStorage services;
    Bounds currentBounds;
    public CampusAppClass() {
        this.students = new StudentStorage();
        this.services = new ServiceStorage();
        this.currentBounds = null;
    }

    @Override
    public void createBounds(String name, long topLatitude, long topLongitude, long bottomLatitude, long bottomLongitude) throws BoundNameExists, InvalidBoundPoints {
        if((this.currentBounds != null && name.equalsIgnoreCase(this.currentBounds.getName())) || Files.exists(Path.of(Bounds.getBoundFilename(name)))) {
            throw new BoundNameExists();
        }
        this.currentBounds = new BoundsClass(name, new Position(topLatitude, topLongitude), new Position(bottomLatitude, bottomLongitude));
    }

    @Override
    public void saveCurrentArea() throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        this.currentBounds.save();
    }

    @Override
    public void loadArea(String areaName) {

    }

    @Override
    public void createService(String type, String serviceName, long latitude, long longitude, int price, int value) throws InvalidTypeException, BoundsNotDefined, OutsideBoundsException, InvalidPriceException, InvalidValueException, AlreadyExistsException {
        if(currentBounds == null) {
            throw new BoundsNotDefined();
        }
        ServiceType actualType;
        try {
            actualType = ServiceType.getType(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException();
        }
        Position pos = new Position(latitude, longitude);
        if(!currentBounds.isInside(pos)) {
            throw new OutsideBoundsException();
        }
        if(price == 0) {
            throw new InvalidPriceException(actualType);
        }
        Service service;
        switch(actualType) {
            case LEISURE -> {
                service = new LeisureService(serviceName, pos, price, value);
            }
            case EATING -> {
                service = new EatingService(serviceName, pos, price, value);
            }
            case LODGING -> {
                service = new LodgingService(serviceName, pos, price, value);
            }
            default -> {
                // Compiler complains without default case
                throw new RuntimeException("Invalid service type");
            }
        }
        this.services.addService(service);
    }

    @Override
    public void createStudent(String type, String name, String lodging, String country) {

    }

    @Override
    public Student getStudent(String student) {
        return students.getStudent(student);
    }

    @Override
    public Service getService(String serviceName) {
        return null;
    }

    @Override
    public Student removeStudent(String studentName) {
        return students.removeStudent(studentName);
    }

    @Override
    public boolean updateStudentPosition(String studentName, String service) {
        Service newLocation = this.getService(service);

        return students.updateStudentLocation(studentName, newLocation);
    }

    @Override
    public void moveHome(String studentName, String newHome) {
        Service newHomeService = this.getService(newHome);

        students.moveHome(studentName, newHomeService);
    }

    @Override
    public void rateService(int rate, String serviceName, String description) {

    }

    @Override
    public TwoWayIterator<Student> getUsersByService(String serviceName) {
        return null;
    }

    @Override
    public Iterator<Student> listAllStudents() {
        return students.getAllStudents();
    }

    @Override
    public Iterator<Student> listStudentsByCountry(String country) {
        return students.getStudentsByCountry(country);
    }

    @Override
    public Iterator<Service> listAllServices() {
        return services.listAllServices();
    }

    @Override
    public Iterator<Service> listVisitedServices(String studentName) {
        return students.listVisitedServices(studentName);
    }

    @Override
    public Iterator<Service> listServicesByRanking() {
        return null;
    }

    @Override
    public Iterator<Service> listClosestServicesByStudent(int rate, ServiceType type, String studentName) {
        // TODO add to student storage
        return null;
    }

    @Override
    public Iterator<Service> listServicesByTag(String tagName) {
        return new FilterIterator<>(services.listAllServices(), new ServiceTagPredicate(tagName));
    }

    @Override
    public Service findBestService(String studentName, ServiceType type) {
        return students.findBestService(studentName, new FilterIterator<>(services.listAllServices(), new ServiceTypePredicate(type)));
    }
}
