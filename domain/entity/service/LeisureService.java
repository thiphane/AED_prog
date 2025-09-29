package domain.entity.service;

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
        return ServiceType.LEISURE;
    }
}
