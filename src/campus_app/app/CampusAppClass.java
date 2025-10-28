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

        if(this.currentBounds == null) {
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
        if(!(serviceObj instanceof LodgingService home)) {
            throw new NoSuchElementOfGivenType();
        }
        Student student = currentBounds.getStudent(name);
        if(student != null) {
            throw new AlreadyExistsException(student.getName());
        }
        switch(studentType) {
            case StudentType.BOOKISH -> {
                student = new BookishStudent(name);
            }
            case THRIFTY -> {
                student = new ThriftyStudent(name);
            }
            case OUTGOING -> {
                student = new OutgoingStudent(name);
            }
            default -> {
                throw new InvalidTypeException();
            }
        }
        student.setCountry(country);
        student.setHome(home);

        this.currentBounds.addStudent(student);
    }

    @Override
    public Student getStudent(String student) throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getStudent(student);
    }

    @Override
    public Service getService(String serviceName) throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getService(serviceName);
    }

    @Override
    public Student removeStudent(String studentName) throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.removeStudent(studentName);
    }

    @Override
    public boolean updateStudentPosition(Student student, Service service) throws BoundsNotDefined, InvalidTypeException, StudentAlreadyThereException, ServiceIsFullException{

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
            throw new StudentAlreadyThereException();
        }
        try {
            currentBounds.updateStudentLocation(student, service);
        }catch (ThriftyStudentIsDistracted e){
            return true;
        }
        return false;
    }

    @Override
    public void moveHome(String studentName, String newHome) throws ServiceIsFullException, MoveNotAcceptable, NoSuchElementOfGivenType, NoSuchElementException, SameHomeException, BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Service newHomeService;
        try {
            newHomeService = this.getService(newHome);
        } catch(NoSuchElementException e) {
            throw new NoSuchElementOfGivenType();
        }
        if(!(newHomeService instanceof LodgingService lodging)) {
            throw new NoSuchElementOfGivenType();
        }
        currentBounds.moveHome(studentName, lodging);
    }

    @Override
    public void rateService(int rate, String serviceName, String description) throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }

    }

    @Override
    public TwoWayIterator<Student> getUsersByService(String serviceName, Order order) throws InvalidOrderException, BoundsNotDefined, InvalidTypeException{
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if(order == null)throw new InvalidOrderException();
        Service service = this.getService(serviceName);
        if(service == null) {
            throw new NoSuchElementException();
        }
        if(!(service instanceof StudentStoringService storingService)) {
            throw new InvalidTypeException();
        }
        return storingService.getUsers();
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
    public Iterator<Service> listVisitedServices(Student student) throws StudentDoesntStoreVisitedServicesException, BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.listVisitedServices(student);
    }

    @Override
    public Iterator<Service> listServicesByRanking() throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return null;
    }

    @Override
    public Iterator<Service> listServicesByTag(String tagName) throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return new FilterIterator<>(currentBounds.listAllServices(), new ServiceTagPredicate(tagName));
    }

    @Override
    public Service findBestService(String studentName, ServiceType type) throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.findBestService(studentName, new FilterIterator<>(currentBounds.listAllServices(), new ServiceTypePredicate(type)));
    }

    @Override
    public Iterator<Service> listClosestServicesByStudent(int rate, String type, String studentName) throws BoundsNotDefined, InvalidTypeException, NoSuchElementException, IllegalArgumentException, NoSuchElementOfGivenType, NoSuchServiceWithGivenRate {
        if(currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if(rate < 0 || rate > 5) throw new IllegalArgumentException();

        if(this.currentBounds.getStudent(studentName) == null){
            throw new NoSuchElementException();
        }
        ServiceType serviceType = ServiceType.getType(type);
        if(serviceType == null){
            throw new InvalidTypeException();
        }
        Iterator<Service> byType = new FilterIterator<>(this.currentBounds.listAllServices(), new ServiceTypePredicate(serviceType));
        if(!byType.hasNext())throw new NoSuchElementOfGivenType();
        //TODO: if byTYpe is null than throw exception
        Iterator<Service> byTypeAndRate = new FilterIterator<>(byType, new ServiceRatePredicate(rate));
        if(!byTypeAndRate.hasNext())throw new NoSuchServiceWithGivenRate();
        //TODO: if no service of given type with given average exists, throw exception
        return this.currentBounds.findClosestService(studentName, byTypeAndRate);
    }

}
