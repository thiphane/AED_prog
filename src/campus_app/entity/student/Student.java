/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.*;
import dataStructures.*;
import campus_app.entity.service.Service;

import java.io.Serializable;

public interface Student extends Serializable {
    /**
     * O(1)
     * @return the student's country of origin
     */
    String getCountry();

    /**
     * O(1)
     * @return the student's name
     */
    String getName();

    /**
     * O(1)
     * @return the student's location
     */
    Service getLocation();

    /**
     * O(1)
     * @return the student's type
     */
    StudentType getType();

    /**
     * O(1)
     * @return the user's home
     */
    Service getHome();

    /**
     * changes the student's home
     * O(n)
     * @param home the new home
     * @throws ServiceIsFullException if the new home is full
     * @throws MoveNotAcceptable if the student refuses to move to the new home
     * @throws SameHomeException if the student tries to move into their own home
     */
    void moveHome(LodgingService home) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException;

    /**
     * changes the user's current position
     * O(1) best case, O(n) worst case, expected O(n)
     * @param position the new position
     * @throws ThriftyStudentIsDistracted if the student was distracted by this moving; they are still moved if this is thrown
     * @throws ServiceIsFullException if the new position is full
     */
    void updatePosition(Service position) throws ThriftyStudentIsDistracted, ServiceIsFullException;

    /**
     * finds the best service for the user
     * O(1) best case, O(n) worst case
     * @param services the services to consider
     * @return the best service for the user
     */
    Service findBestService(Iterator<Service> services);

    /**
     * Gets the services visited by the user
     * O(n)
     * @return an iterator through services visited by the user in visiting order
     * @throws StudentDoesntStoreVisitedServicesException if the student does not store their visits
     */
    Iterator<Service> getVisitedServices() throws StudentDoesntStoreVisitedServicesException, NoVisitedServicesException;

    /**
     * Finds the closest service(s) to the user
     * O(n)
     * @param services the services to consider
     * @return an iterator through the closest service(s) to the user
     */
    Iterator<Service>  findClosestServices(Iterator<Service> services);
}
