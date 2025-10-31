/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.EatingService;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;
import campus_app.exceptions.AlreadyExistsException;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;
import campus_app.exceptions.ServiceDoesNotExistException;
import dataStructures.*;
import dataStructures.exceptions.NoSuchElementException;

import java.io.*;

public class ServiceStorage implements Serializable {
    // All services by order of insertion
    protected List<Service> services;
    // All services by order of their rating
    transient protected SortedList<Service> servicesByStar;
    public ServiceStorage() {
        this.services = new ListInArray<>(2500);
        this.servicesByStar = new SortedDoublyLinkedList<>(new ServiceStarComparator());
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
        this.servicesByStar.add(service); // O(n) worst case, O(1) best case
    }

    public void rateService(String service, int rating, String description) throws ServiceDoesNotExistException {
        Service elem = getService(service); // O(n)
        int oldRating = elem.getRating();
        elem.addRating(rating, description); // O(1)
        if(oldRating != elem.getRating()) {
            servicesByStar.remove(elem); // O(n)
            servicesByStar.add(elem); // O(1) best cases, O(n) worst case, expected O(n)
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
        return services.iterator(); // O(n)
    }

    Iterator<Service> listServicesByRanking() {
        return servicesByStar.iterator();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // O(n)
        // Evitar ler 2 listas do ficheiro, com conteúdo igual
        this.servicesByStar = new SortedDoublyLinkedList<>(new ServiceStarComparator());
        Iterator<Service> iter = this.services.iterator();
        while(iter.hasNext()) { // O(n)
            servicesByStar.add(iter.next());
        }
    }
}
