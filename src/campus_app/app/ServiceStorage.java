package campus_app.app;

import campus_app.entity.service.EatingService;
import campus_app.entity.service.Service;
import campus_app.exceptions.AlreadyExistsException;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;
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

    public void rateService(String service, int rating, String description) {
        Service elem = getService(service);
        int oldRating = elem.getRating();
        elem.addRating(rating, description);
        if(oldRating != elem.getRating()) {
            servicesByStar.remove(elem);
            servicesByStar.add(elem);
        }
        // Update servicesByStar
    }

    public Service getService(String service) {
        try {
            int ind = services.indexOf(new EatingService(service, new Position(0,0), 50, 50));
            if(ind < 0) {
                throw new NoSuchElementException();
            }
            return services.get(ind);
        } catch (InvalidValueException | InvalidPriceException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterator<Service> listAllServices() {
        return services.iterator();
    }

    Iterator<Service> listServicesByRanking() {
        return servicesByStar.iterator();
    }
}
