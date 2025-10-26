package campus_app.app;

import campus_app.entity.student.Student;
import dataStructures.Comparator;

public class AlphabeticalStudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        // TODO verificar se é isto ou o2.compareto o1
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}
