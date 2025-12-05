package campus_app.entity.service;

import campus_app.entity.student.Student;
import dataStructures.Transformer;

public class StudentNameTransformer implements Transformer<Student, String> {
    @Override
    public String transform(Student value) {
        return value.getName();
    }
}
