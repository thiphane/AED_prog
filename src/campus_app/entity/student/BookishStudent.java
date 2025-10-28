package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.ServiceType;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;
import dataStructures.ListInArray;

public class BookishStudent extends StudentAbstract implements Student {
    public BookishStudent(String name, String country, LodgingService home){
        super(name, country, home);
        visited = new DoublyLinkedList<>();
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
        super.updatePosition(position);
        if(position.getType().equals(ServiceType.LEISURE)&& (super.visited.indexOf(position) ==-1))
            super.visited.addLast(position);
    }
}
