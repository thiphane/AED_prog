package domain.entity;

import dataStructures.Iterator;
import domain.app.Order;
import domain.app.ServiceType;
import domain.entity.student.Student;

public class EatingService extends ServiceAbstract{
    @Override
    public ServiceType getType() {
        return null;
    }

    @Override
    public Iterator<Student> getUsers(Order order) {
        return null;
    }
}
