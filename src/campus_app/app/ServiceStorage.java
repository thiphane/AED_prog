/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.EatingService;
import campus_app.entity.service.Service;
import campus_app.exceptions.AlreadyExistsException;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;
import campus_app.exceptions.ServiceDoesNotExistException;
import dataStructures.*;
import dataStructures.exceptions.NoSuchElementException;

import java.io.Serializable;

public class ServiceStorage implements Serializable {
    // All services by order of insertion
    protected List<Service> services;
    // All services by order of their rating
    protected SortedList<Service> servicesByStar;
    public ServiceStorage() {
        this.services = new ListInArray<>(2500);
        this.servicesByStar = new SortedDoublyLinkedList<>(new ServiceStarComparator());
    }

    public void addService(Service service) throws AlreadyExistsException {
        Iterator<Service> iter = services.iterator();
        while(iter.hasNext()) {
            Service cur = iter.next();
            if(cur.getName().equalsIgnoreCase(service.getName())) {
                throw new AlreadyExistsException(cur.getName());
            }
        }
        this.services.addLast(service);
        this.servicesByStar.add(service);
    }

    public void rateService(String service, int rating, String description) throws ServiceDoesNotExistException {
        Service elem = getService(service);
        int oldRating = elem.getRating();
        elem.addRating(rating, description);
        if(oldRating != elem.getRating()) {
            servicesByStar.remove(elem);
            servicesByStar.add(elem);
        }
    }

    public Service getService(String service) throws ServiceDoesNotExistException {
        Iterator<Service> iter = services.iterator();
        while(iter.hasNext()) {
            Service cur = iter.next();
            if(cur.getName().equalsIgnoreCase(service))return cur;
        } throw new ServiceDoesNotExistException();
    }

    public Iterator<Service> listAllServices() {
        return services.iterator();
    }

    Iterator<Service> listServicesByRanking() {
        return servicesByStar.iterator();
    }
}
