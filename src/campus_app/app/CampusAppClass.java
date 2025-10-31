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

    /**
     * Creates new bounds to store students and service
     * O(1) if no area is loaded, O(n) if it is and needs to be saved
     * @param name the name of the bounds
     * @param topLatitude     the latitude of the top left point
     * @param topLongitude    the longitude of the top left point
     * @param bottomLatitude  the latitude of the bottom right point
     * @param bottomLongitude the longitude of the bottom right point
     * @throws BoundNameExists    if a bounds file with the given name already exists
     * @throws InvalidBoundPoints if the positions given are not valid when used as top left and bottom right points
     */
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

    /**
     * Saves the current loaded bounds
     * O(n)
     * @return the saved bounds
     * @throws BoundsNotDefined if no bounds are defined
     */
    @Override
    public Bounds saveCurrentArea() throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        this.currentBounds.save();
        return this.currentBounds;
    }

    /**
     * Loads an area from a file
     * O(n)
     * @param areaName the name of the area to load
     * @return the loaded area
     * @throws BoundsNotDefined if no area with the given name exists
     */
    @Override
    public Bounds loadArea(String areaName) throws BoundsNotDefined {
        if(this.currentBounds != null) {
            saveCurrentArea(); // O(n)
        }
        this.currentBounds = BoundsClass.loadBounds(areaName); // O(n)
        return this.currentBounds;
    }

    /**
     * Creates a new service
     * O(n) time
     * @param type the type of service
     * @param serviceName the name of the service
     * @param latitude    the latitude of the service's position
     * @param longitude   the longitude of the service's position
     * @param price       the price of the service
     * @param value       the value of the service
     * @throws InvalidTypeException   if an invalid type is given
     * @throws BoundsNotDefined       if no bounds are defined
     * @throws OutsideBoundsException if the service's position is out of the loaded bounds
     * @throws InvalidPriceException  if the price is invalid for the given service type
     * @throws InvalidValueException  if the value is invalid for the given service type
     * @throws AlreadyExistsException if a service with the given name already exists
     */
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
        this.currentBounds.addService(service); // O(n)
    }

    /**
     * Creates a new student
     * O(n) time
     * @param type the type of student to create
     * @param name the name of the student
     * @param lodging the service the student will live in
     * @param country the country of origin of the student
     * @throws InvalidTypeException if the given student type doesn't exist
     * @throws AlreadyExistsException if a student with the given name already exists
     * @throws NoSuchElementOfGivenType if the lodging doesn't exist
     * @throws ServiceIsFullException if the lodging is full
     * @throws BoundsNotDefined if no bounds are defined
     */
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
        switch(studentType) { // Since lodging services store students, all students will take O(n) time to create
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

        this.currentBounds.addStudent(student); // O(n)
    }

    /**
     * Gets a student by name
     * O(n)
     * @param student the name of a student
     * @return the student with the given name
     * @throws BoundsNotDefined if no bounds are defined
     * @throws StudentDoesNotExistException if no user with the given name exists
     */
    @Override
    public Student getStudent(String student) throws BoundsNotDefined, StudentDoesNotExistException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getStudent(student); // O(n)
    }

    /**
     * Gets the service with the given name
     * O(n)
     * @param serviceName the name of the service to get
     * @return the service with the given name
     * @throws BoundsNotDefined if no bounds are defined
     * @throws ServiceDoesNotExistException if no service with the given name exists
     */
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

    /**
     * Removes a student from the bounds
     * O(n^2)
     * @param studentName the name of the student to remove
     * @return the removed student
     * @throws BoundsNotDefined if no bounds are defined
     * @throws StudentDoesNotExistException if no student with the given name exists
     */
    @Override
    public Student removeStudent(String studentName) throws BoundsNotDefined, StudentDoesNotExistException {
        if(this.currentBounds == null)
            throw new BoundsNotDefined();
        return currentBounds.removeStudent(studentName); // O(n^2)
    }

    /**
     * Changes the user's current position
     * O(1) time
     * @param student the student to change the position of
     * @param service the service to change the position of
     * @return whether the student got distracted by changing their position
     * @throws BoundsNotDefined if no bounds are defined
     * @throws InvalidTypeException if the given service can't receive students
     * @throws StudentAlreadyThereException if the student is already at the service
     * @throws ServiceIsFullException if the service is full
     */
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
            /*
             * Best case: O(1), if the studen't doesn't store services of this type, the service they're moving to doesn't store users,
             * and neither does the service they're moving from
             * Worst case: O(n), if any of those apply
             */
            currentBounds.updateStudentLocation(student, service);
        }catch (ThriftyStudentIsDistracted e){
            return true;
        }
        return false;
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
    public void moveHome(String studentName, String newHome) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException, BoundsNotDefined, ServiceDoesNotExistException, StudentDoesNotExistException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Service newHomeService;
        newHomeService = this.getService(newHome); // O(n)
        currentBounds.moveHome(studentName, (LodgingService) newHomeService); // O(n)
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
        currentBounds.addRating(rate, serviceName, description); // O(n)
    }

    // TODO a app não faz nada com a ordem, não devia sair da main
    /**
     * Gets all the users in a given service
     * O(n) time
     * @param serviceName the name of the service
     * @param order the order of the elements
     * @return a two-way iterator over all the students in the given service
     * @throws InvalidOrderException if the order is null
     * @throws BoundsNotDefined if no bounds are defined
     * @throws ServiceDoesNotExistException if no service with the given name exists
     * @throws CantShowUsersException if the given service doesn't store the users in it
     * @throws NoStudentsException if the given service is empty
     */
    @Override
    public TwoWayIterator<Student> getUsersByService(String serviceName, Order order) throws InvalidOrderException, BoundsNotDefined, ServiceDoesNotExistException, CantShowUsersException, NoStudentsException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if(order == null)throw new InvalidOrderException();
        Service service = this.getService(serviceName); // O(n)
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
        return students;
    }

    /**
     * List all students in alphabetical order
     * O(1) to create the iterator, O(n) to traverse it
     * @return an iterator over all students in alphabetical order
     * @throws BoundsNotDefined if no bounds are defined
     */
    @Override
    public Iterator<Student> listAllStudents() throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getAllStudents();
    }

    /**
     * List all students from a specific country in order of insertion
     * O(n) time to both create and traverse the iterator
     * @param country the country to get students of
     * @return an iterator over all students from a specific country in order of insertion
     * @throws BoundsNotDefined if no bounds are defined
     */
    @Override
    public Iterator<Student> listStudentsByCountry(String country) throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.getStudentsByCountry(country); // O(n)
    }

    /**
     * Creating the iterator is O(1), traversing it is O(n)
     * @return an iterator through all services in order of insertion
     * @throws BoundsNotDefined if no bounds are defined
     */
    @Override
    public Iterator<Service> listAllServices() throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return currentBounds.listAllServices(); // O(n)
    }

    /**
     * Gets the services visited by a student
     * O(1) time to create the iterator, O(n) to traverse it
     * @param student the student
     * @return an iterator through all the services visited by a student, in visiting order
     * @throws StudentDoesntStoreVisitedServicesException if the student doesn't remember the services they visited
     * @throws BoundsNotDefined if no bounds are not defined
     * @throws NoVisitedServicesException if the student never visited a service
     */
    @Override
    public Iterator<Service> listVisitedServices(Student student) throws StudentDoesntStoreVisitedServicesException, BoundsNotDefined, NoVisitedServicesException {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        Iterator<Service> it = currentBounds.listVisitedServices(student); // O(1)
        if(!it.hasNext()){
            throw new NoVisitedServicesException(student);
        }
        return it;
    }

    /**
     * Lists all the services in order of their ranking
     * O(1)
     * @return an iterator through all the services in order of their rating and when they received it
     * @throws BoundsNotDefined if no bounds are defined
     */
    @Override
    public Iterator<Service> listServicesByRanking() throws BoundsNotDefined {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }return currentBounds.listServicesByRating(); // O(1)
    }

    /**
     * Lists all services with a comment with the given tag
     * O(1) to create, O(n^2) to traverse, since for each service,
     * it also has to check if any of its comments has the tag
     * @param tagName the tag to find
     * @return an iterator through all services with the given tag
     * @throws BoundsNotDefined if no bounds are defined
     */
    @Override
    public Iterator<Service> listServicesByTag(String tagName) throws BoundsNotDefined {
        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        return new FilterIterator<>(currentBounds.listAllServices(), new ServiceTagPredicate(tagName)); // O(1), traversing it is O(n^2)
    }

    /**
     * Finds the best service of the given type for the given student
     * O(n) for thrifty students, O(1) otherwise
     * @param studentName the name of the student
     * @param type the type of service
     * @return the best service of the given type for the student
     * @throws BoundsNotDefined if no bounds are defined
     * @throws StudentDoesNotExistException if the student does not exist
     * @throws InvalidTypeException if the given type does not exist
     */
    @Override
    public Service findBestService(String studentName, String type) throws BoundsNotDefined, StudentDoesNotExistException, InvalidTypeException {

        if(this.currentBounds == null) {
            throw new BoundsNotDefined();
        }
        ServiceType serviceType = ServiceType.getType(type);
        if(serviceType == null)throw new InvalidTypeException();
        return currentBounds.findBestService(studentName, serviceType); // O(n) for thrifty students, O(1) otherwise
    }


    /**
     * Lists the services tied to be closest to the student with a given rating and type
     * O(n) time
     * @param rate the rating of the services to get
     * @param type the type of services to get
     * @param studentName the name of the student to get services closest to
     * @return an iterator through the services tied to be the closest to the student
     * @throws BoundsNotDefined if no bounds are defined
     * @throws InvalidTypeException if the given service type doesn't exist
     * @throws NoSuchElementOfGivenType if no student with the given name exists
     * @throws NoSuchServiceWithGivenRate
     * @throws StudentDoesNotExistException
     * @throws InvalidRating
     */
    @Override
    public Iterator<Service> listClosestServicesByStudent(int rate, String type, String studentName) throws BoundsNotDefined, InvalidTypeException, NoSuchElementOfGivenType, NoSuchServiceWithGivenRate, StudentDoesNotExistException, InvalidRating {
        if(currentBounds == null) {
            throw new BoundsNotDefined();
        }
        if(rate < 1 || rate > 5) throw new InvalidRating();

        if(this.currentBounds.getStudent(studentName) == null){ // O(n)
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
        return this.currentBounds.findClosestService(studentName, byTypeAndRate);
    }

}
