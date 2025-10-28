package campus_app.app;

import campus_app.entity.student.Student;

public record ElementId(String name, Student student) {
    public boolean equals(String name){
        return name.equals(this.name);
    }
}
