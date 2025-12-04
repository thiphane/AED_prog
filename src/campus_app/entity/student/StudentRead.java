package campus_app.entity.student;

import campus_app.entity.service.Service;
import campus_app.entity.service.ServiceRead;
import campus_app.exceptions.NoVisitedServicesException;
import campus_app.exceptions.StudentDoesntStoreVisitedServicesException;
import dataStructures.Iterator;

public interface StudentRead {
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
    ServiceRead getLocation();

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
     * finds the best service for the user
     * O(1) best case, O(n) worst case
     * @param services the services to consider
     * @return the best service for the user
     */
    ServiceRead findBestService(Iterator<ServiceRead> services);

    /**
     * Gets the services visited by the user
     * O(n)
     * @return an iterator through services visited by the user in visiting order
     * @throws StudentDoesntStoreVisitedServicesException if the student does not store their visits
     */
    Iterator<ServiceRead> getVisitedServices() throws StudentDoesntStoreVisitedServicesException, NoVisitedServicesException;

    /**
     * Finds the closest service(s) to the user
     * O(n)
     * @param services the services to consider
     * @return an iterator through the closest service(s) to the user
     */
    Iterator<ServiceRead>  findClosestServices(Iterator<Service> services);
}
