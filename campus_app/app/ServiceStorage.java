package campus_app.app;

import dataStructures.DoublyLinkedList;
import dataStructures.List;
import dataStructures.SortedDoublyLinkedList;
import dataStructures.SortedList;
import campus_app.entity.service.Service;

public class ServiceStorage {
    // All services by order of insertion
    protected List<Service> services;
    // All services by order of their rating
    protected SortedList<Service> servicesByStar;
    public ServiceStorage() {
        this.services = new DoublyLinkedList<>();
        this.servicesByStar = new SortedDoublyLinkedList<>(new ServiceStarComparator());
    }
}
