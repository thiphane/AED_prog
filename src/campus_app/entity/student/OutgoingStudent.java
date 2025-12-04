/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.ServiceRead;
import campus_app.exceptions.NoVisitedServicesException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.StudentAlreadyThereException;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;
import dataStructures.List;
import dataStructures.TransformerIterator;

public class OutgoingStudent extends StudentAbstract {
    protected List<ServiceRead> visited;
    public OutgoingStudent(String name, String country, LodgingService home) throws ServiceIsFullException {
        super(name, country, home);
    }

    public StudentType getType(){
        return StudentType.OUTGOING;
    }

    @Override
    public boolean updatePosition(Service position) throws ServiceIsFullException, StudentAlreadyThereException {
        super.updatePosition(position);
        if(visited == null) { // TODO updatePosition é usado pelo construtor do abstrato, então não se consegue inicializar a lista antes
            visited = new DoublyLinkedList<>();
        }
        if(visited.indexOf(position) == -1){
            visited.addLast(position);
        }
        return false;
    }

    @Override
    public Iterator<ServiceRead> getVisitedServices() throws NoVisitedServicesException {
        if ( visited.isEmpty() ) {
            throw new NoVisitedServicesException(this);
        }
        return new TransformerIterator<>(visited.iterator());
    }
}
