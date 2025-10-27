package campus_app.entity.service;

import campus_app.entity.student.Student;
import campus_app.exceptions.ServiceIsFullException;
import dataStructures.TwoWayIterator;

public interface StudentStoringService extends Service {
    void addUser(Student student) throws ServiceIsFullException;
    void removeUser(Student student);
    TwoWayIterator<Student> getUsers();
}
