package campus_app.exceptions;

import campus_app.entity.student.Student;

public class MoveNotAcceptable extends Exception {
    private Student student;
    public MoveNotAcceptable(Student student) {
        this.student = student;
    }

    public Student getStudent() { return student; }
}
