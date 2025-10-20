package campus_app.entity.student;

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
    public void updatePosition(Service position) {
        // TODO update position if needed
        super.updatePosition(position);
    }

    @Override
    public Iterator<Service> getVisitedServices() {
        return null;
    }
}
