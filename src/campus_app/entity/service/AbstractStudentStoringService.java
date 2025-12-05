/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;

import campus_app.app.Position;
import campus_app.entity.student.Student;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;
import campus_app.exceptions.ServiceIsFullException;
import dataStructures.*;

public abstract class AbstractStudentStoringService extends ServiceAbstract implements StudentStoringService {
    private static final int EXPECTED_STUDENTS_IN_SERVICE = 20;
    final private TwoWayList<Student> users;

    public AbstractStudentStoringService(String serviceName, Position position, int price, int value, ServiceType type) throws InvalidPriceException, InvalidValueException {
        super(serviceName, position, price, value, type);
        this.users = new HashMapList<>(EXPECTED_STUDENTS_IN_SERVICE);
    }

    /**
     * Adds a new user to the service
     * O(1) time
     * @param student the student
     * @throws ServiceIsFullException if the service is full
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addUser(Student student) throws ServiceIsFullException {
        if (this.getValue() <= this.users.size()) {
            throw new ServiceIsFullException(this);
        }
        if(!((ContainCheckingList<Student>)this.users).contains(student)) // O(1)
            this.users.addLast(student); // O(1)
    }

    /**
     * Removes a user from the service
     * O(1) time
     * @param student the student
     */
    @SuppressWarnings("unchecked")
    @Override
    public void removeUser(Student student) {
        ((ObjectRemovalList<Student>)this.users).remove(student);
    }

    @Override
    public TwoWayIterator<Student> getUsers(){
        return users.twoWayiterator();
    }
}
