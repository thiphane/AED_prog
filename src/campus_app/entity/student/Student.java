package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.InvalidTypeException;
import campus_app.exceptions.MoveNotAcceptable;
import campus_app.exceptions.SameHomeException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.*;
import campus_app.entity.service.Service;

import java.io.Serializable;

public interface Student extends Serializable {
    String getCountry();
    String getName();
    Service getLocation();
    StudentType getType();
    Service getHome();
    void moveHome(LodgingService home) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException;
    void updatePosition(Service position) throws ThriftyStudentIsDistracted, ServiceIsFullException;
    Service findBestService(Iterator<Service> services);
    Iterator<Service> getVisitedServices();
    Iterator<Service>  findClosestServices(Iterator<Service> services);
}
