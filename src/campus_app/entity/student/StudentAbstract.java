/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.*;
import campus_app.entity.service.StudentStoringService;
import dataStructures.*;
import campus_app.entity.service.Service;

public abstract class StudentAbstract implements Student {
    private final String name;
    private final String country;
    private LodgingService home;
    private Service location;
    public StudentAbstract(String name, String country, LodgingService home) throws ServiceIsFullException {
        this.name = name;
        this.country = country;
        this.home = home;
        try {
            this.updatePosition(home);
        } catch (ThriftyStudentIsDistracted | StudentAlreadyThereException e) {
            throw new RuntimeException(e);
        }
    }

    private void setHome(LodgingService home) throws ServiceIsFullException {
        try {
            this.updatePosition(home); // O(n)
            this.home.removeUser(this); // O(n)
            this.home = home;
        } catch (ThriftyStudentIsDistracted | StudentAlreadyThereException e) {
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
        this.setHome(home); // O(n)
    }

    /**
     * Updates the user's position
     * O(n) worst case (either the student's current or new position store students),
     * O(1) best case (neither one stores students)
     * @param position the new position
     * @throws ServiceIsFullException if the given position is full
     * @throws ThriftyStudentIsDistracted if the user gets distracted by going to this service
     */
    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted, StudentAlreadyThereException {
        Service oldLocation = this.location;
        if ( oldLocation != null && oldLocation.equals(position) ) { throw new StudentAlreadyThereException(this); }
        if(position instanceof StudentStoringService service)service.addUser(this); // O(n)
        this.location = position;
        if(oldLocation instanceof StudentStoringService loc && !oldLocation.equals(home))
            loc.removeUser(this); // O(n)
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Service getLocation() {
        return this.location;
    }

    /**
     * Gets the services closest to the student
     * O(n)
     * @param services the services to get the closest from
     * @return an iterator through the services tied for distance from the user
     */
    @Override
    public Iterator<Service> findClosestServices(Iterator<Service> services) {
        DistanceComparator comparator = new DistanceComparator(this.getLocation().getPosition());
        List<Service> list = new DoublyLinkedList<>();
        Service closest = null;
        while(services.hasNext()) {
            Service cur = services.next();
            if (closest == null || comparator.compare(cur, closest) < 0) {
                list = new DoublyLinkedList<>();
                list.addLast(cur);
                closest = cur;
            } else if(comparator.compare(cur, closest) == 0) {
                list.addLast(cur);
            }
        }
        return list.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student st) {
            return this.getName().equalsIgnoreCase(st.getName());
        }
        return false;
    }

    @Override
    public Service findBestService(Iterator<Service> services) {
        if(services.hasNext())return services.next();
        else return null;
    }
}
