package domain.app;

import dataStructures.DoublyLinkedList;
import dataStructures.List;
import dataStructures.SortedDoublyLinkedList;
import dataStructures.SortedList;
import domain.entity.service.Service;
import domain.entity.student.Student;

public class ServiceStorage {
    // All services by order of insertion
    protected List<Service> services;
    // All services by order of their rating
    protected SortedList<Student> servicesByStar;
    public ServiceStorage() {
        this.services = new DoublyLinkedList<>();
        this.servicesByStar = new SortedDoublyLinkedList<>();
    }

    public void
}
