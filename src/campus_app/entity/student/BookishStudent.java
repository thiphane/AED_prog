/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.ServiceType;
import campus_app.exceptions.NoVisitedServicesException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;
import dataStructures.List;

public class BookishStudent extends StudentAbstract {
    protected List<Service> visited;
    public BookishStudent(String name, String country, LodgingService home) throws ServiceIsFullException { // O(n)
        super(name, country, home); // O(n)
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
        if(position.getType().equals(ServiceType.LEISURE) && (visited.indexOf(position) == -1)) // O(n)
            visited.addLast(position);
    }

    @Override
    public Iterator<Service> getVisitedServices() throws NoVisitedServicesException {
        if ( visited.isEmpty() ) {
            throw new NoVisitedServicesException(this);
        }
        return visited.iterator();
    }
}
