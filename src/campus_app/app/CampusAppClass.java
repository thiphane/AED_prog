/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;
import campus_app.entity.service.*;
import campus_app.entity.student.*;
import campus_app.exceptions.*;
import dataStructures.*;
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
            saveCurrentArea(); // O(n)
        }
        this.currentBounds = BoundsClass.loadBounds(areaName); // O(n)
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
        switch(actualType) { // O(1)
            case LEISURE -> service = new LeisureService(serviceName, pos, price, value);
            case EATING -> service = new EatingService(serviceName, pos, price, value);
            case LODGING -> service = new LodgingService(serviceName, pos, price, value);
            default -> throw new RuntimeException("Invalid service type"); // Compiler complains without default case, as service may be null
        }
        this.currentBounds.addService(service); // O(1)
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
        } catch (ServiceDoesNotExistException e) {
            throw new NoSuchElementOfGivenType();
        }
        if (!(serviceObj instanceof LodgingService home)) {
            throw new NoSuchElementOfGivenType();
        }

        try {
            Student s = currentBounds.getStudent(name);
            throw new AlreadyExistsException(s.getName());
        } catch (StudentDoesNotExistException ignored) {}

        Student student = null;
        switch(studentType) { // Since lodging services store students, all students will take O(n) time to create
            case BOOKISH -> student = new BookishStudent(name, country, home);
            case THRIFTY -> student = new ThriftyStudent(name, country, home);
            case OUTGOING -> student = new OutgoingStudent(name, country, home);
        }
        this.currentBounds.addStudent(student); // O(log n)
    }

    @Override
    public Student getStudent(String student) throws BoundsNotDefined, StudentDoesNotExistException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getStudent(student); // O(log n)
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
        return currentBounds.removeStudent(studentName); // O(n^2)
    }

    @Override
    public UpdatePositionResult updateStudentPosition(String studentName, String serviceName) throws BoundsNotDefined, InvalidTypeException, StudentAlreadyThereException, ServiceIsFullException, StudentDoesNotExistException, ServiceDoesNotExistException {
        if(currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Student student = this.currentBounds.getStudent(studentName);
        Service service = this.currentBounds.getService(serviceName);
        if(!service.getType().equals(ServiceType.LEISURE) && !service.getType().equals(ServiceType.EATING)) {
            throw new InvalidTypeException();
        }
        /*
         * Best case: O(1), if the student doesn't store services of this type, the service they're moving to doesn't store users,
         * and neither does the service they're moving from
         * Worst case: O(n), if any of those apply
         */
        boolean distracted = (student).updatePosition(service);
        return new UpdatePositionResult(student, service, distracted);
    }

    /**
     * Moves the student's home with a given name to the service with the given name
     * O(n) time
     * @param studentName the name of the student
     * @param newHome the name of the lodging service
     * @throws ServiceIsFullException if the given service is full
     * @throws MoveNotAcceptable      if the student refuses to move to the given lodging
     * @throws SameHomeException      if the student tries to move into his own home
     * @throws BoundsNotDefined       if no bounds are defined
     * @throws ServiceDoesNotExistException if the given service does not exist
     * @throws StudentDoesNotExistException if the given student does not exist
     */
    @Override
    public UpdatePositionResult moveHome(String studentName, String newHome) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException, BoundsNotDefined, ServiceDoesNotExistException, StudentDoesNotExistException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Service newHomeService = this.getService(newHome); // O(n)
        if(!(newHomeService instanceof LodgingService lodging)) {
            throw new ServiceDoesNotExistException();
        }
        Student student = this.currentBounds.getStudent(studentName); // O(n)
        student.moveHome(lodging); // O(n)
        return new UpdatePositionResult(student, newHomeService, false);
    }

    /**
     * Adds a new evaluation to the given service
     * O(n)
     * @param rate the rating
     * @param serviceName the name of the service
     * @param description the description of the evaluation
     * @throws BoundsNotDefined if no bounds are defined
     * @throws ServiceDoesNotExistException if no service with the given name exists
     * @throws InvalidRating if rating < 1 or rating > 5
     */
    @Override
    public void rateService(int rate, String serviceName, String description) throws BoundsNotDefined, ServiceDoesNotExistException, InvalidRating {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if (rate < 1 || rate > 5) {
            throw new InvalidRating();
        }
        Service service = currentBounds.getService(serviceName);
        int oldRating = service.getRating();
        service.addRating(rate, description.toLowerCase()); // O(1)
        if(service.getRating() != oldRating) {
            currentBounds.updateServiceRating(service, oldRating); // O(n)
        }
    }

    @Override
    public TwoWayIterator<StudentRead> getUsersByService(String serviceName) throws BoundsNotDefined, ServiceDoesNotExistException, CantShowUsersException, NoStudentsException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Service service = this.getService(serviceName); // O(1)
        if(service == null) {
            throw new NoSuchElementException();
        }
        if(!(service instanceof StudentStoringService storingService)) {
            throw new CantShowUsersException(service);
        }
        TwoWayIterator<Student> students = storingService.getUsers(); // O(1)
        if(!students.hasNext()) {
            throw new NoStudentsException(storingService);
        }
        return new TransformerTwoWayIterator<>(students);
    }

    @Override
    public Iterator<StudentRead> listAllStudents() throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return new TransformerIterator<>(currentBounds.getAllStudents());
    }

    @Override
    public Iterator<StudentRead> listStudentsByCountry(String country) throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return new TransformerIterator<>(currentBounds.getStudentsByCountry(country)); // O(n)
    }

    @Override
    public Iterator<ServiceRead> listAllServices() throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return new TransformerIterator<>(currentBounds.listAllServices()); // O(1)
    }

    @Override
    public Iterator<ServiceRead> listServicesByRanking() throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }return new TransformerIterator<>(currentBounds.listServicesByRating()); // O(1)
    }

    @Override
    public Iterator<ServiceRead> listServicesByTag(String tagName) throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return new TransformerIterator<>(new FilterIterator<>(currentBounds.listAllServices(), new ServiceTagPredicate(tagName)));
    }

    @Override
    public ServiceRead findBestService(String studentName, String type) throws BoundsNotDefined, StudentDoesNotExistException, InvalidTypeException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        ServiceType serviceType = ServiceType.getType(type);
        if(serviceType == null)throw new InvalidTypeException();
        return currentBounds.findBestService(studentName, serviceType); // O(n) for thrifty students, O(1) otherwise
    }

    @Override
    public Iterator<ServiceRead> listClosestServicesByStudent(int rate, String type, String studentName) throws BoundsNotDefined, InvalidTypeException, NoSuchElementOfGivenType, NoSuchServiceWithGivenRate, StudentDoesNotExistException, InvalidRating {
        if(currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if(rate < 1 || rate > 5) throw new InvalidRating();
        Student s = this.currentBounds.getStudent(studentName); // O(log n)
        if(s == null){ // O(1)
            throw new StudentDoesNotExistException();
        }
        ServiceType serviceType;
        try {
            serviceType = ServiceType.getType(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException();
        }
        Iterator<Service> byType = new FilterIterator<>(this.currentBounds.listAllServices(), new ServiceTypePredicate(serviceType)); // O(1)
        if(!byType.hasNext())throw new NoSuchElementOfGivenType();
        Iterator<Service> byTypeAndRate = new FilterIterator<>(byType, new ServiceRatePredicate(rate)); // O(1)
        if(!byTypeAndRate.hasNext())throw new NoSuchServiceWithGivenRate();
        return new TransformerIterator<>(s.findClosestServices(byTypeAndRate)); // O(n) to create
    }

}
