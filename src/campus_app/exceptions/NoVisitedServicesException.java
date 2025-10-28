package campus_app.exceptions;

import campus_app.entity.student.Student;

public class NoVisitedServicesException extends Exception {private final Student student;
    public NoVisitedServicesException(Student student) {
        this.student = student;
    }
    public Student getStudent() { return this.student; }
}
