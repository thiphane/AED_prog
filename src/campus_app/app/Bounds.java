/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.Service;
import campus_app.entity.service.ServiceType;
import campus_app.entity.student.Student;
import campus_app.exceptions.*;
import campus_app.exceptions.AlreadyExistsException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.FilterIterator;
import dataStructures.Iterator;
import dataStructures.exceptions.NoSuchElementException;

public interface Bounds {
    static String getBoundFilename(String name) {
        return String.format("%s.ser", name.toLowerCase().replaceAll(" ", "_"));
    }

    /**
     * O(1) time
     * @return the name of the area
     */
    String getName();

    /**
     * O(1) time
     * @return the top right corner of the area
     */
    Position getTopPosition();

    /**
     * O(1) time
     * @return the bottom left corner of the area
     */
    Position getBottomPosition();

    /**
     * Saves the current area to a file called <area name>.ser in the current working directory
     * O(n) time
     */
    void save();

    /**
     * Checks if a position is inside the bounds
     * O(1) time
     * @param pos the position to check
     * @return whether the given position is inside the bounds
     */
    boolean isInside(Position pos);

    /**
     * Removes a student from the area
     * O(n^2) time
     * @param studentName the name of the student
     * @return the removed student
     * @throws StudentDoesNotExistException if no student with the given name exists
     */
    Student removeStudent(String studentName) throws StudentDoesNotExistException;

    /**
     * Adds a new service to the area
     * O(n) time
     * @param service the service to add
     * @throws AlreadyExistsException if a service with the same name already exists
     */
    void addService(Service service) throws AlreadyExistsException;

    /**
     * Gets a student by their name
     * O(n) time
     * @param student the name of the student
     * @return the student
     * @throws StudentDoesNotExistException if no student with the given name exists
     */
    Student getStudent(String student) throws StudentDoesNotExistException;

    /**
     * Changes a student's location
     * O(1) best case, O(n) worst case, expected O(n) time
     * @param student the student to change the location of
     * @param newLocation the location to move the student to
     * @throws ThriftyStudentIsDistracted if the student was distracted by this moving; they are still moved if this is thrown
     * @throws ServiceIsFullException if the location the student is moving to is full
     */
    void updateStudentLocation(Student student, Service newLocation) throws ThriftyStudentIsDistracted, ServiceIsFullException;

    /**
     * Changes a student's home
     * O(n) time
     * @param studentName the name of the student
     * @param newHomeService the new home
     * @throws ServiceIsFullException if the given home is full
     * @throws MoveNotAcceptable if the student refuses to move to the new home
     * @throws SameHomeException if the student tries to move to their own home
     * @throws StudentDoesNotExistException if no student with the given name exists
     */
    void moveHome(String studentName, LodgingService newHomeService) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException, StudentDoesNotExistException;

    /**
     * Creating the iterator is O(1), traversing it is O(n)
     * @return an iterator through all the students in the area in alphabetical order
     */
    Iterator<Student> getAllStudents();

    /**
     * Get all students from the given country
     * O(1) to create an iterator, O(n) to traverse it
     * @param country the country to get students of
     * @return an iterator through all students from the given country in insertion order
     */
    Iterator<Student> getStudentsByCountry(String country);

    /**
     * Creating the iterator is O(1), traversing it is O(n)
     * @return an iterator through all services in order of insertion
     */
    Iterator<Service> listAllServices();

    /**
     * Finds the best service of the given type for the given student
     * O(n) for thrifty students, O(1) otherwise
     * @param studentName the name of the student
     * @param type the type of service
     * @return the best service of the given type for the student
     * @throws StudentDoesNotExistException if the student does not exist
     */
    Service findBestService(String studentName, ServiceType type) throws StudentDoesNotExistException;
    /**
     * Gets the services visited by a student
     * O(1) time to create the iterator, O(n) to traverse it
     * @param student the student
     * @return an iterator through all the services visited by a student, in visiting order
     * @throws StudentDoesntStoreVisitedServicesException if the student doesn't remember the services they visited
     */
    Iterator<Service> listVisitedServices(Student student) throws StudentDoesntStoreVisitedServicesException;

    /**
     * Gets the closest service(s) to a student
     * O(n)
     * @param studentName the name of the student
     * @param byTypeAndRate an iterator through the services to consider
     * @return an iterator through the service(s) closest to the student
     * @throws StudentDoesNotExistException if the student does not exist
     */
    Iterator<Service> findClosestService(String studentName, Iterator<Service> byTypeAndRate) throws StudentDoesNotExistException;

    /**
     * Adds a new student to the bounds
     * O(n) time
     * @param student the student to add
     */
    void addStudent(Student student);

    /**
     * Gets the service with the given name
     * @param service the name of the service
     * @return the service
     * @throws ServiceDoesNotExistException if no service with the given name exists
     */
    Service getService(String service) throws ServiceDoesNotExistException;

    /**
     * Lists all the services in order of their ranking
     * O(1)
     * @return an iterator through all the services in order of their rating and when they received it
     */
    Iterator<Service>listServicesByRating();

    /**
     * Adds a new evaluation to the given service
     * O(n)
     * @param rate the rating
     * @param serviceName the name of the service
     * @param description the description of the evaluation
     * @throws ServiceDoesNotExistException if no service with the given name exists
     */
    void addRating(int rate, String serviceName, String description) throws ServiceDoesNotExistException;
}
