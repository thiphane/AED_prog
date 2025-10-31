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
import dataStructures.DoublyLinkedList;
import dataStructures.TwoWayIterator;
import dataStructures.TwoWayList;

public abstract class AbstractStudentStoringService extends ServiceAbstract implements StudentStoringService {
    final private TwoWayList<Student> users;

    public AbstractStudentStoringService(String serviceName, Position position, int price, int value, ServiceType type) throws InvalidPriceException, InvalidValueException {
        super(serviceName, position, price, value, type);
        this.users = new DoublyLinkedList<>();
    }

    /**
     * Adds a new user to the service
     * O(n) time
     * @param student the student
     * @throws ServiceIsFullException if the service is full
     */
    @Override
    public void addUser(Student student) throws ServiceIsFullException {
        if (this.getValue() <= this.users.size()) {
            throw new ServiceIsFullException(this);
        }
        if(this.users.indexOf(student) ==-1) // O(n)
            this.users.addLast(student); // O(1)
    }

    /**
     * Removes a user from the service
     * O(n) time
     * @param student the student
     */
    @Override
    public void removeUser(Student student) {
        int idx = this.users.indexOf(student); // O(n)
        if(idx != -1) {
            this.users.remove(idx); // O(1) best case (first or last element), O(n) worst case
        }
    }

    @Override
    public TwoWayIterator<Student> getUsers(){
        return users.twoWayiterator();
    }
}
