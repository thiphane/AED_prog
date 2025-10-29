/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;
import campus_app.entity.service.*;
import campus_app.entity.student.*;
import campus_app.exceptions.*;
import dataStructures.*;
import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.nio.file.Files;
import java.nio.file.Path;

public class CampusAppClass implements CampusApp {
    Bounds currentBounds;
    public CampusAppClass() {
        this.currentBounds = null;
    }

    @Override
    public void createBounds(String name, long topLatitude, long topLongitude, long bottomLatitude, long bottomLongitude) throws BoundNameExists, InvalidBoundPoints {
        if((this.currentBounds != null && name.equalsIgnoreCase(this.currentBounds.getName())) || Files.exists(Path.of(Bounds.getBoundFilename(name)))) {
            throw new BoundNameExists();
        }
        if(this.currentBounds != null) {
            try {
                saveCurrentArea();
            } catch (BoundsNotDefined e) {
                throw new RuntimeException(e);
            }
        }
        this.currentBounds = new BoundsClass(name, new Position(topLatitude, topLongitude), new Position(bottomLatitude, bottomLongitude));
    }

    @Override
    public Bounds saveCurrentArea() throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        this.currentBounds.save();
        return this.currentBounds;
    }

    @Override
    public Bounds loadArea(String areaName) throws BoundsNotDefined {
        if(this.currentBounds != null) {
            saveCurrentArea();
        }
        this.currentBounds = BoundsClass.loadBounds(areaName);
        return this.currentBounds;
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
        this.currentBounds.addService(service);
    }

    @Override
    public void createStudent(String type, String name, String lodging, String country) throws InvalidTypeException, AlreadyExistsException, NoSuchElementOfGivenType, ServiceIsFullException, BoundsNotDefined {

        if (this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        StudentType studentType;
        try {
            studentType = StudentType.getType(type);
        } catch (Exception e) {
            throw new InvalidTypeException();
        }
        Service serviceObj;
        try {
            serviceObj = currentBounds.getService(lodging);
        } catch (Exception e) {
            throw new NoSuchElementOfGivenType();
        }
        if (!(serviceObj instanceof LodgingService home)) {
            throw new NoSuchElementOfGivenType();
        }
        Student student = null;
            try{
                student = currentBounds.getStudent(name);
            } catch (StudentDoesNotExistException ignored) {
            }

        if(student != null) {
            throw new AlreadyExistsException(student.getName());
        }
        switch(studentType) {
            case BOOKISH -> {
                student = new BookishStudent(name, country, home);
            }
            case THRIFTY -> {
                student = new ThriftyStudent(name, country, home);
            }
            case OUTGOING -> {
                student = new OutgoingStudent(name, country, home);
            }
            default -> {
                throw new InvalidTypeException();
            }
        }

        this.currentBounds.addStudent(student);
    }

    @Override
    public Student getStudent(String student) throws BoundsNotDefined, StudentDoesNotExistException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getStudent(student);
    }

    @Override
    public Service getService(String serviceName) throws BoundsNotDefined, ServiceDoesNotExistException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Service service = currentBounds.getService(serviceName);
        if (service == null) {
            throw new ServiceDoesNotExistException();
        }
        return service;
    }

    @Override
    public Student removeStudent(String studentName) throws BoundsNotDefined, StudentDoesNotExistException {
        if(this.currentBounds == null)
            throw new BoundsNotDefined();
        return currentBounds.removeStudent(studentName);
    }

    @Override
    public boolean updateStudentPosition(Student student, Service service) throws BoundsNotDefined, InvalidTypeException, StudentAlreadyThereException, ServiceIsFullException {

        if(currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if(service == null) {
            throw new NoSuchElementException();
        }
        if(!service.getType().equals(ServiceType.LEISURE) && !service.getType().equals(ServiceType.EATING)) {
            throw new InvalidTypeException();
        }
        if(student.getLocation().equals(service)) {
            throw new StudentAlreadyThereException(student);
        }
        try {
            currentBounds.updateStudentLocation(student, service);
        }catch (ThriftyStudentIsDistracted e){
            return true;
        }
        return false;
    }

    @Override
    public void moveHome(String studentName, String newHome) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException, BoundsNotDefined, ServiceDoesNotExistException, StudentDoesNotExistException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Service newHomeService;
        newHomeService = this.getService(newHome);
        currentBounds.moveHome(studentName, (LodgingService) newHomeService);
    }

    @Override
    public void rateService(int rate, String serviceName, String description) throws BoundsNotDefined, ServiceDoesNotExistException, InvalidRating {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if (rate < 1 || rate > 5) {
            throw new InvalidRating();
        }
        currentBounds.addRating(rate, serviceName, description);
    }

    @Override
    public TwoWayIterator<Student> getUsersByService(String serviceName, Order order) throws InvalidOrderException, BoundsNotDefined, ServiceDoesNotExistException, CantShowUsersException, NoStudentsException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if(order == null)throw new InvalidOrderException();
        Service service = this.getService(serviceName);
        if(service == null) {
            throw new NoSuchElementException();
        }
        if(!(service instanceof StudentStoringService storingService)) {
            throw new CantShowUsersException(service);
        }
        TwoWayIterator<Student> students = storingService.getUsers();
        if(!students.hasNext()) {
            throw new NoStudentsException(storingService);
        }
        return students;
    }

    @Override
    public Iterator<Student> listAllStudents() throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getAllStudents();
    }

    @Override
    public Iterator<Student> listStudentsByCountry(String country) throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getStudentsByCountry(country);
    }

    @Override
    public Iterator<Service> listAllServices() throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.listAllServices();
    }

    @Override
    public Iterator<Service> listVisitedServices(Student student) throws StudentDoesntStoreVisitedServicesException, BoundsNotDefined, NoVisitedServicesException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Iterator<Service> it = currentBounds.listVisitedServices(student);
        if(!it.hasNext()){
            throw new NoVisitedServicesException(student);
        }
        return it;
    }

    @Override
    public Iterator<Service> listServicesByRanking() throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }return currentBounds.listServicesByRating();
    }

    @Override
    public Iterator<Service> listServicesByTag(String tagName) throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return new FilterIterator<>(currentBounds.listAllServices(), new ServiceTagPredicate(tagName));
    }

    @Override
    public Service findBestService(String studentName, String type) throws BoundsNotDefined, StudentDoesNotExistException, InvalidTypeException {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        ServiceType serviceType = ServiceType.getType(type);
        if(serviceType == null)throw new InvalidTypeException();
        return currentBounds.findBestService(studentName, serviceType);
    }

    @Override
    public Iterator<Service> listClosestServicesByStudent(int rate, String type, String studentName) throws BoundsNotDefined, InvalidTypeException, IllegalArgumentException, NoSuchElementOfGivenType, NoSuchServiceWithGivenRate, StudentDoesNotExistException, InvalidRating {
        if(currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if(rate < 1 || rate > 5) throw new InvalidRating();

        if(this.currentBounds.getStudent(studentName) == null){
            throw new StudentDoesNotExistException();
        }
        ServiceType serviceType;
        try {
            serviceType = ServiceType.getType(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException();
        }
        Iterator<Service> byType = new FilterIterator<>(this.currentBounds.listAllServices(), new ServiceTypePredicate(serviceType));
        if(!byType.hasNext())throw new NoSuchElementOfGivenType();
        Iterator<Service> byTypeAndRate = new FilterIterator<>(byType, new ServiceRatePredicate(rate));
        if(!byTypeAndRate.hasNext())throw new NoSuchServiceWithGivenRate();
        return this.currentBounds.findClosestService(studentName, byTypeAndRate);
    }

}
