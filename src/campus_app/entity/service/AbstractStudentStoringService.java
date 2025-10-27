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
    private TwoWayList<Student> users;

    public AbstractStudentStoringService(String serviceName, Position position, int price, int value, ServiceType type) throws InvalidPriceException, InvalidValueException {
        super(serviceName, position, price, value, type);
        this.users = new DoublyLinkedList<>();
    }

    @Override
    public void addUser(Student student) throws ServiceIsFullException {
        if (this.getValue() <= this.users.size()) {
            throw new ServiceIsFullException(this);
        }
        this.users.addLast(student);
    }

    @Override
    public void removeUser(Student student) {
        this.users.remove(this.users.indexOf(student));
    }

    @Override
    public TwoWayIterator<Student> getUsers(){
        return users.twoWayiterator();
    }
}
