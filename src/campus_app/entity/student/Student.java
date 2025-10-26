package campus_app.entity.student;

import dataStructures.*;
import campus_app.entity.service.ServiceType;
import campus_app.entity.service.Service;

import java.io.Serializable;

public interface Student extends Serializable {
    String getCountry();
    String getName();
    Service getLocation();
    StudentType getType();
    Service getHome();
    void moveHome(Service home);
    void updatePosition(Service position);
    Service findBestService(Iterator<Service> services);
    Iterator<Service> getVisitedServices();
}
