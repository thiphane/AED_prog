package domain.entity;

import dataStructures.Iterator;
import domain.app.Order;
import domain.app.Position;
import domain.app.ServiceType;
import domain.entity.student.Student;

public class LeisureService extends ServiceAbstract{
    protected LeisureService(String serviceName, Position position, int price, int value) {
        super(serviceName, position, price, value);
    }

    @Override
    public ServiceType getType() {
        return null;
    }

    @Override
    public Iterator<Student> getUsers(Order order) {
        return null;
    }
}
