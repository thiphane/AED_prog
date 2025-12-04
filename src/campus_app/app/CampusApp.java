/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;
import campus_app.entity.service.ServiceRead;
import campus_app.entity.student.StudentRead;
import campus_app.exceptions.*;
import dataStructures.*;
import campus_app.entity.service.Service;

public interface CampusApp {
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
    void createBounds(String name, long topLatitude, long topLongitude, long bottomLatitude, long bottomLongitude ) throws BoundNameExists, InvalidBoundPoints;
    /**
     * Saves the current loaded bounds
     * O(n)
     * @return the saved bounds
     * @throws BoundsNotDefined if no bounds are defined
     */
    Bounds saveCurrentArea() throws BoundsNotDefined;
    /**
     * Loads an area from a file
     * O(n)
     * @param areaName the name of the area to load
     * @return the loaded area
     * @throws BoundsNotDefined if no area with the given name exists
     */
    Bounds loadArea(String areaName) throws BoundsNotDefined;
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
    void createService(String type, String serviceName, long latitude, long longitude, int price, int value) throws InvalidTypeException, BoundsNotDefined, OutsideBoundsException, InvalidPriceException, InvalidValueException, AlreadyExistsException;
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
    void createStudent(String type, String name, String lodging, String country) throws AlreadyExistsException, InvalidTypeException, BoundsNotDefined, NoSuchElementOfGivenType, ServiceIsFullException;
    /**
     * Gets a student by name
     * O(n)
     * @param student the name of a student
     * @return the student with the given name
     * @throws BoundsNotDefined if no bounds are defined
     * @throws StudentDoesNotExistException if no user with the given name exists
     */
    StudentRead getStudent(String student) throws BoundsNotDefined, StudentDoesNotExistException;
    /**
     * Gets the service with the given name
     * O(n)
     * @param serviceName the name of the service to get
     * @return the service with the given name
     * @throws BoundsNotDefined if no bounds are defined
     * @throws ServiceDoesNotExistException if no service with the given name exists
     */
    ServiceRead getService(String serviceName) throws BoundsNotDefined, ServiceDoesNotExistException;
    /**
     * Removes a student from the bounds
     * O(n^2)
     * @param studentName the name of the student to remove
     * @return the removed student
     * @throws BoundsNotDefined if no bounds are defined
     * @throws StudentDoesNotExistException if no student with the given name exists
     */
    StudentRead removeStudent(String studentName) throws BoundsNotDefined, StudentDoesNotExistException;
    record UpdatePositionResult(StudentRead student, ServiceRead service, boolean distracted) {}
    /**
     * Changes the user's current position
     * O(1) best case, O(n) worst case, expected O(n) time
     * @param student the student to change the position of
     * @param service the service to change the position of
     * @return whether the student got distracted by changing their position
     * @throws BoundsNotDefined if no bounds are defined
     * @throws InvalidTypeException if the given service can't receive students
     * @throws StudentAlreadyThereException if the student is already at the service
     * @throws ServiceIsFullException if the service is full
     */
    UpdatePositionResult updateStudentPosition(String student, String service) throws BoundsNotDefined, InvalidTypeException, StudentAlreadyThereException, ServiceIsFullException, StudentDoesNotExistException, ServiceDoesNotExistException;
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
    UpdatePositionResult moveHome(String studentName, String newHome) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException, BoundsNotDefined, ServiceDoesNotExistException, StudentDoesNotExistException;
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
    void rateService(int rate, String serviceName, String description) throws BoundsNotDefined, InvalidRating, ServiceDoesNotExistException;
    /**
     * Gets all the users in a given service
     * O(n) time
     * @param serviceName the name of the service
     * @return a two-way iterator over all the students in the given service
     * @throws BoundsNotDefined if no bounds are defined
     * @throws ServiceDoesNotExistException if no service with the given name exists
     * @throws CantShowUsersException if the given service doesn't store the users in it
     * @throws NoStudentsException if the given service is empty
     */
    TwoWayIterator<StudentRead> getUsersByService(String serviceName) throws BoundsNotDefined, ServiceDoesNotExistException, CantShowUsersException, NoStudentsException;
    /**
     * List all students in alphabetical order
     * O(1) to create the iterator, O(n) to traverse it
     * @return an iterator over all students in alphabetical order
     * @throws BoundsNotDefined if no bounds are defined
     */
    Iterator<StudentRead> listAllStudents() throws BoundsNotDefined;
    /**
     * List all students from a specific country in order of insertion
     * O(n) time to both create and traverse the iterator
     * @param country the country to get students of
     * @return an iterator over all students from a specific country in order of insertion
     * @throws BoundsNotDefined if no bounds are defined
     */
    Iterator<StudentRead> listStudentsByCountry(String country) throws BoundsNotDefined;
    /**
     * Creating the iterator is O(1), traversing it is O(n)
     * @return an iterator through all services in order of insertion
     * @throws BoundsNotDefined if no bounds are defined
     */
    Iterator<ServiceRead> listAllServices() throws BoundsNotDefined;
    /**
     * Lists all the services in order of their ranking
     * O(1)
     * @return an iterator through all the services in order of their rating and when they received it
     * @throws BoundsNotDefined if no bounds are defined
     */
    Iterator<ServiceRead> listServicesByRanking() throws BoundsNotDefined;
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
     * @throws NoSuchServiceWithGivenRate if no services with the given rating exist
     * @throws StudentDoesNotExistException if no student with the given name exists
     * @throws InvalidRating if the rating is not within the bounds
     */
    Iterator<ServiceRead> listClosestServicesByStudent(int rate, String type, String studentName) throws BoundsNotDefined, InvalidTypeException, IllegalArgumentException, NoSuchElementOfGivenType, NoSuchServiceWithGivenRate, StudentDoesNotExistException, InvalidRating;
    /**
     * Lists all services with a comment with the given tag
     * O(1) to create, O(n^2) to traverse, since for each service,
     * it also has to check if any of its comments has the tag
     * @param tagName the tag to find
     * @return an iterator through all services with the given tag
     * @throws BoundsNotDefined if no bounds are defined
     */
    Iterator<ServiceRead> listServicesByTag(String tagName) throws BoundsNotDefined;
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
    ServiceRead findBestService(String studentName, String type) throws BoundsNotDefined, StudentDoesNotExistException, InvalidTypeException;
}
