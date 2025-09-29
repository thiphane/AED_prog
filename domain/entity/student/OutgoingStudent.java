package domain.entity.student;

import domain.app.StudentType;
import domain.entity.Service;
import domain.entity.StudentAbstract;

public class OutgoingStudent extends StudentAbstract {
    public OutgoingStudent(String name, Service home, Service location) {
        super(name, home, location);
    }

    public StudentType getType(){
        return StudentType.OUTGOING;
    }
}
