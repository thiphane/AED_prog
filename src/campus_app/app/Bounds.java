/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.Service;
import campus_app.entity.service.ServiceType;
import campus_app.entity.student.Student;
import campus_app.exceptions.*;
import dataStructures.Iterator;

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
     * Updates the service's ranking
     * @param service the service to update
     */
    void updateServiceRating(Service service, int oldRating);
}
