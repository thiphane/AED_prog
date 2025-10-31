/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.exceptions;

import campus_app.entity.student.Student;

public class MoveNotAcceptable extends Exception {
    private final Student student;
    public MoveNotAcceptable(Student student) {
        this.student = student;
    }

    public Student getStudent() { return student; }
}
