package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.*;
import campus_app.exceptions.ServiceIsFullException;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;
import dataStructures.ListInArray;

public class ThriftyStudent extends StudentAbstract {
    public ThriftyStudent(String name){
        super(name);
        visited = new ListInArray<>(3);
    }
    @Override
    public void setHome(LodgingService home) throws ServiceIsFullException {
        super.setHome(home);
        visited.addFirst(home);
    }
    public StudentType getType(){
        return StudentType.THRIFTY;
    }
    public boolean isDistracted(Service service){
       Iterator<Service> it = visited.iterator();
       while(it.hasNext()){
           Service s =  it.next();
           if(s.getType().equals(service.getType())){
               return s.getPrice()<service.getPrice();
           }
       } return false;
    }

    @Override
    public void updatePosition(Service position) throws ServiceIsFullException {
        super.updatePosition(position);
    }

    @Override
    public Service findBestService(Iterator<Service> services) {
        return null;
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
        throw new StudentDoesntStoreVisitedServicesException();
    }
}
