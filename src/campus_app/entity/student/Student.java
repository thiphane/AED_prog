package campus_app.entity.student;

import dataStructures.*;
import campus_app.entity.service.ServiceType;
import campus_app.entity.service.Service;

public interface Student {
    String getCountry();
    String getName();
    Service getLocation();
    StudentType getType();
    Service getHome();
    void moveHome(Service home);
    void updatePosition(Service position);
    Service findBestService(Iterator<Service> services);
    Iterator<Service> getVisitedServices();
    Iterator<Service>  findClosestServices(Iterator<Service> services);
}
