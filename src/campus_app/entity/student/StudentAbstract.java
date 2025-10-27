package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.InvalidTypeException;
import campus_app.exceptions.MoveNotAcceptable;
import campus_app.exceptions.SameHomeException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.entity.service.StudentStoringService;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.Iterator;
import campus_app.entity.service.Service;
import dataStructures.SortedDoublyLinkedList;
import dataStructures.SortedList;

public abstract class StudentAbstract implements Student {
    private final String name;
    private final String country;
    private LodgingService home;
    private Service location;
    public StudentAbstract(String name, LodgingService home, String country) {
        this.name = name;
        this.home = home;
        this.country = country;
        this.updatePosition(home);
    }
    @Override
    public String getCountry() {
        return this.country;
    }
    @Override
    public Service getHome() {
        return home;
    }

    @Override
    public void moveHome(LodgingService home) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException {
        if(this.home.equals(home)) {
            throw new SameHomeException(this);
        }
        this.home.removeUser(this);
        home.addUser(this);
        this.home = home;
        this.location = home;
    }

    @Override
    public void updatePosition(Service position) throws ThriftyStudentIsDistracted, ServiceIsFullException {
        if(position instanceof StudentStoringService service)service.addUser(this);
        this.location = position;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Service getLocation() {
        return this.location;
    }

    @Override
    public Iterator<Service> findClosestServices(Iterator<Service> services) {
        SortedList<Service> sortedSet = new SortedDoublyLinkedList<>(new DistanceComparator(this));
        while(services.hasNext()){
            sortedSet.add(services.next());
        }
        return sortedSet.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student st) {
            return this.getName().equalsIgnoreCase(st.getName());
        }
        return false;
    }
}
