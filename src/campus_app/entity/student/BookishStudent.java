package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.ServiceType;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.StudentDoesntStoreVisitedServicesException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;
import dataStructures.List;
import dataStructures.ListInArray;

public class BookishStudent extends StudentAbstract implements Student {
    protected List<Service> visited;
    public BookishStudent(String name, String country, LodgingService home) throws ServiceIsFullException {
        super(name, country, home);
    }

    public StudentType getType(){
        return StudentType.BOOKISH;
    }


    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted {
        super.updatePosition(position);
        if(visited == null) { // updatePosition é usado pelo construtor do abstrato, então não se consegue inicializar a lista antes
            visited = new DoublyLinkedList<>();
        }
        if(position.getType().equals(ServiceType.LEISURE) && (visited.indexOf(position) == -1))
            visited.addLast(position);
    }

    @Override
    public Iterator<Service> getVisitedServices() throws StudentDoesntStoreVisitedServicesException {
        return visited.iterator();
    }
}
