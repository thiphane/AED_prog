package campus_app.entity.service;

import campus_app.entity.student.Student;
import dataStructures.TwoWayIterator;

public interface StudentStoringServiceRead extends ServiceRead {
    /**
     * Lists all the students in the service
     * O(1)
     * @return a two-way iterator through the users in the service
     */
    TwoWayIterator<Student> getUsers();
}
