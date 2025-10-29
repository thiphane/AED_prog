package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.ServiceType;
import campus_app.exceptions.*;
import campus_app.exceptions.ServiceIsFullException;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;
import dataStructures.ListInArray;

public class ThriftyStudent extends StudentAbstract {
    public ThriftyStudent(String name, String country, LodgingService home) throws ServiceIsFullException {
        super(name, country, home);
        visited = new ListInArray<>(ServiceType.values().length);
    }
    public StudentType getType(){
        return StudentType.THRIFTY;
    }

    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted {
        super.updatePosition(position);
        for(int i = 0; i < visited.size(); i++) {
            Service s = visited.get(i);
            if(s.getType() == position.getType()) {
                if (position.getPrice() > s.getPrice()) {
                    throw new ThriftyStudentIsDistracted(this);
                } else {
                    visited.remove(i);
                    visited.addLast(position);
                    return;
                }
            }
        }
        visited.addLast(position);
    }

    @Override
   public Service findBestService(Iterator<Service> services) {
        if(services.hasNext()) {
            Service best = services.next();
            while (services.hasNext()) {
                Service curr = services.next();
                if(curr.getPrice() < best.getPrice())best = curr;
            }
            return best;
        }else return null;
    }

    @Override
    public void moveHome(LodgingService home) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException {
        if (!this.getHome().equals(home) && home.getPrice() >= this.getHome().getPrice()) {
            throw new MoveNotAcceptable(this);
        }
        super.moveHome(home);
    }

    @Override
    public Iterator<Service> getVisitedServices()throws StudentDoesntStoreVisitedServicesException{
        throw new StudentDoesntStoreVisitedServicesException(this);
    }
}
