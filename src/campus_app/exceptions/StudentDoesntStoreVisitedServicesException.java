package campus_app.exceptions;

import campus_app.entity.student.Student;

public class StudentDoesntStoreVisitedServicesException extends Exception {
    private final Student student;
    public StudentDoesntStoreVisitedServicesException(Student student) {
        this.student = student;
    }
    public Student getStudent() { return this.student; }
}
