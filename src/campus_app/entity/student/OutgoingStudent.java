package campus_app.entity.student;

import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.Iterator;
import campus_app.entity.service.Service;

public class OutgoingStudent extends StudentAbstract implements Student {
    public OutgoingStudent(String name, Service home, String country) {
        super(name, home, country);
    }

    public StudentType getType(){
        return StudentType.OUTGOING;
    }

    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted {
        super.updatePosition(position);
        // TODO Store service if needed
    }

    @Override
    public Service findBestService(Iterator<Service> services) {
        return null;
    }

    @Override
    public Iterator<Service> getVisitedServices() {
        return null;
    }
}
