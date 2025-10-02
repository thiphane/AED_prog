package domain.entity.student;

import dataStructures.*;
import domain.entity.service.ServiceType;
import domain.entity.service.Service;

public interface Student extends Comparable<Student> {
    String getCountry();
    String getName();
    Service getLocation();
    StudentType getType();
    Service getHome();
    void moveHome(Service home);
    void updatePosition(Service position);
    Service findBestService(ServiceType type, Iterator<Service> services);
    Iterator<Service> getVisitedServices();
}
