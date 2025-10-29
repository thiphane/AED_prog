package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.*;
import campus_app.entity.service.StudentStoringService;
import campus_app.exceptions.ServiceIsFullException;
import dataStructures.*;
import campus_app.entity.service.Service;
import dataStructures.SortedDoublyLinkedList;
import dataStructures.SortedList;

public abstract class StudentAbstract implements Student {
    private final String name;
    private final String country;
    private LodgingService home;
    private Service location;
    protected List<Service> visited;
    public StudentAbstract(String name, String country, LodgingService home) throws ServiceIsFullException {
        this.name = name;
        this.country = country;
        this.home = home;
        this.location = home;
        // Por causa dos estudantes de identificação
        if(home != null) {
            try {
                this.updatePosition(home);
            } catch (ThriftyStudentIsDistracted e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setHome(LodgingService home) throws ServiceIsFullException {
        try {
            this.updatePosition(home);
            this.home.removeUser(this);
            this.home = home;
        } catch (ThriftyStudentIsDistracted e) {
            throw new RuntimeException(e);
        }
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
        this.setHome(home);
    }

    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted {
        Service oldLocation = this.location;
        if(position instanceof StudentStoringService service)service.addUser(this);
        this.location = position;
        if(oldLocation instanceof StudentStoringService loc && !oldLocation.equals(home))
            loc.removeUser(this);
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
    @Override
    public Iterator<Service> getVisitedServices() throws StudentDoesntStoreVisitedServicesException {
        return visited.iterator();
    }
    @Override
    public Service findBestService(Iterator<Service> services) {
        if(services.hasNext())return services.next();
        else return null;
    }
}
