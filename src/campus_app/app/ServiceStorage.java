package campus_app.app;

import campus_app.entity.service.Service;
import campus_app.exceptions.AlreadyExistsException;
import dataStructures.*;


public class ServiceStorage {
    // All services by order of insertion
    protected List<Service> servicesList;
    // All services by order of their rating
    protected SortedList<Service> servicesByStar;
    //Accessing a service
    protected List<Service> services;
    public ServiceStorage() {
        this.services = new ListInArray<>(2500);
        this.servicesList = new ListInArray<>(2500);
        this.servicesByStar = new SortedDoublyLinkedList<>(new ServiceStarComparator());
    }
    private int hashFunction(String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            hash = (hash * 31 + name.charAt(i)) % 2500;
        }
        return Math.abs(hash);
    }

    public void addService(Service service) throws AlreadyExistsException {
        int pos = hashFunction(service.getName());
        if(services.get(pos) != null)throw new AlreadyExistsException();
        services.add(pos, service);
        this.servicesList.addLast(service);
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
        return services.get(hashFunction(service));
    }

    public Iterator<Service> listAllServices() {
        return servicesList.iterator();
    }

    Iterator<Service> listServicesByRanking() {
        return servicesByStar.iterator();
    }
}
