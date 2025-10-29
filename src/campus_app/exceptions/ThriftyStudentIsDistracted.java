/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.exceptions;

import campus_app.entity.student.Student;

public class ThriftyStudentIsDistracted extends Exception {
    private final Student student;
    public ThriftyStudentIsDistracted(Student student) {
        this.student = student;
    }
    public Student getStudent() { return this.student; }
}
