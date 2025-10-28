package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;

public class OutgoingStudent extends StudentAbstract implements Student {
    public OutgoingStudent(String name, String country, LodgingService home){
        super(name, country, home);
        super.visited = new DoublyLinkedList<>();
    }
    @Override
    public void setHome(LodgingService home) throws ServiceIsFullException {
        super.setHome(home);
        visited.addFirst(home);
    }

    public StudentType getType(){
        return StudentType.OUTGOING;
    }

    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted {
        super.updatePosition(position);
        if(super.visited.indexOf(position) ==-1){
            super.visited.addLast(position);
        }

    }

    @Override
    public Service findBestService(Iterator<Service> services) {
        return null;
    }

}
