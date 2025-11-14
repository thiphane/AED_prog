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
    public static final int MAX_RATING = 5;
    // All services by order of insertion
    protected final List<Service> services;
    // All services by order of their rating
    protected List<Service>[] servicesByStar;
    @SuppressWarnings("unchecked")
    public ServiceStorage() {
        this.services = new ListInArray<>(2500);
        // TODO singly linked list
        this.servicesByStar = new DoublyLinkedList[MAX_RATING];
        for(int i = 0; i < servicesByStar.length; i++) {
            this.servicesByStar[i] = new DoublyLinkedList<>();
        }
    }

    /**
     * Adds a new service
     * @param service the service to add
     * @throws AlreadyExistsException if a service with the given name already exists
     */
    public void addService(Service service) throws AlreadyExistsException {
        Iterator<Service> iter = services.iterator();
        while(iter.hasNext()) { // O(n)
            Service cur = iter.next();
            if(cur.getName().equalsIgnoreCase(service.getName())) {
                throw new AlreadyExistsException(cur.getName());
            }
        }
        this.services.addLast(service); // O(1)
        this.servicesByStar[MAX_RATING - service.getRating()].addLast(service); // O(1)
    }

    public void updateServiceRating(Service service, int oldRating) {
        servicesByStar[MAX_RATING - oldRating].remove(servicesByStar[MAX_RATING - oldRating].indexOf(service));
        servicesByStar[MAX_RATING - service.getRating()].addLast(service);
    }


    public Service getService(String service) throws ServiceDoesNotExistException {
        Iterator<Service> iter = services.iterator();
        while(iter.hasNext()) {
            Service cur = iter.next();
            if(cur.getName().equalsIgnoreCase(service))return cur;
        } throw new ServiceDoesNotExistException();
    }

    public Iterator<Service> listAllServices() {
        return services.iterator(); // O(n)
    }

    Iterator<Service> listServicesByRanking() {
        return new ArrayOfListIterator<>(servicesByStar);
    }
}
