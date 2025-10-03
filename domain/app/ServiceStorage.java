package domain.app;

import dataStructures.*;
import domain.entity.service.Service;
import domain.entity.student.Student;

public class ServiceStorage {
    // All services by order of insertion
    protected List<Service> services;
    // All services by order of their rating
    protected SortedList<Service> servicesByStar;
    public ServiceStorage() {
        this.services = new ListInArray<>(2500);
        this.servicesByStar = new SortedDoublyLinkedList<>();
    }

    public void addService(Service service) {
        this.services.addLast(service);
        this.servicesByStar.add(service);
    }

    public void rateService(String service, int rating, String description) {
        // Update servicesByStar
    }

    public Service getService(String service) {
        return null;
    }

    public Iterator<Service> listAllServices() {
        return services.iterator();
    }

    Iterator<Service> listServicesByRanking() {
        return servicesByStar.iterator();
    }
}
