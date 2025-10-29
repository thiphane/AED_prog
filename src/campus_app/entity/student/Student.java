/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.*;
import campus_app.exceptions.ServiceIsFullException;
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
    Iterator<Service> getVisitedServices() throws StudentDoesntStoreVisitedServicesException;
    Iterator<Service>  findClosestServices(Iterator<Service> services);
}
