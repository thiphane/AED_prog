/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.student.Student;
import dataStructures.Comparator;

public class AlphabeticalStudentComparator implements Comparator<Student> {
    /**
     * O(1)
     * @param o1 first student
     * @param o2 second student
     * @return Negative,zero or positive if the name of first student is lexicographically less,
     * equal or grater then second student.
     */
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}
