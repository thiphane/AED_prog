package campus_app.entity.student;

import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.Iterator;
import campus_app.entity.service.Service;

public class BookishStudent extends StudentAbstract implements Student {
    public BookishStudent(String name, Service home, String country) {
        super(name, home, country);
    }

    public StudentType getType(){
        return StudentType.BOOKISH;
    }

    @Override
    public Service findBestService(Iterator<Service> services) {
        return null;
    }

    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted {
        // TODO update position if needed
        super.updatePosition(position);
    }

    @Override
    public Iterator<Service> getVisitedServices() {
        return null;
    }
}
