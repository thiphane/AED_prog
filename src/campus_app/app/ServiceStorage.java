/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.Service;
import campus_app.exceptions.AlreadyExistsException;
import campus_app.exceptions.ServiceDoesNotExistException;
import dataStructures.*;

import java.io.*;

public class ServiceStorage implements Serializable {
    private static final int EXPECTED_SERVICE_COUNT = 2500;
    public static final int MAX_RATING = 5;
    // All services by order of their rating
    protected ObjectRemovalList<Service>[] servicesByStar;
    protected Map<String, Service> servicesByName;
    @SuppressWarnings("unchecked")
    public ServiceStorage() {
        this.servicesByName = new LinkedHashMap<>(EXPECTED_SERVICE_COUNT);
        this.servicesByStar = new ObjectRemovalList[MAX_RATING];
        for(int i = 0; i < servicesByStar.length; i++) {
            this.servicesByStar[i] = new ObjectRemovalSinglyList<>();
        }
    }

    /**
     * Adds a new service
     * O(1)
     * @param service the service to add
     * @throws AlreadyExistsException if a service with the given name already exists
     */
    public void addService(Service service) throws AlreadyExistsException {
        try {
            Service s = this.getService(service.getName()); // O(1)
            throw new AlreadyExistsException(s.getName());
        } catch ( ServiceDoesNotExistException e) {
            this.servicesByStar[MAX_RATING - service.getRating()].addLast(service); // O(1)
            this.servicesByName.put(service.getName().toLowerCase(), service); // O(1)
        }
    }

    /**
     * Updates a service's position in the ranking
     * O(n)
     * @param service the service to update
     * @param oldRating the rating the service had before
     */
    public void updateServiceRating(Service service, int oldRating) {
        servicesByStar[MAX_RATING - oldRating].remove(service); // O(n)
        servicesByStar[MAX_RATING - service.getRating()].addLast(service); // O(1)
    }

    /**
     * Gets a service through its name
     * O(1)
     * @param service the name of the service
     * @return the service
     * @throws ServiceDoesNotExistException if no service with the given name exists
     */
    public Service getService(String service) throws ServiceDoesNotExistException {
        Service s = this.servicesByName.get(service.toLowerCase()); // O(1)
        if ( s == null ) {
            throw new ServiceDoesNotExistException();
        }
        return s;
    }

    /**
     * @return an iterator through the services in order of their alphabetical name
     */
    public Iterator<Service> listAllServices() {
        return servicesByName.values();
    }

    /**
     * @return an iterator through the services in order of their rating
     */
    Iterator<Service> listServicesByRanking() {
        return new ArrayOfListIterator<>(servicesByStar);
    }
}
