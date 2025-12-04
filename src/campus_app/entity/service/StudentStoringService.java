/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;

import campus_app.entity.student.Student;
import campus_app.exceptions.ServiceIsFullException;
import dataStructures.TwoWayIterator;

public interface StudentStoringService extends Service, StudentStoringServiceRead {
    /**
     * Adds a new user to the service
     * O(n)
     * @param student the student to add
     * @throws ServiceIsFullException if this service is full
     */
    void addUser(Student student) throws ServiceIsFullException;

    /**
     * Removes the given student from the service
     * O(n)
     * @param student the student to remove
     */
    void removeUser(Student student);
}
