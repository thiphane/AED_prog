package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;

public class OutgoingStudent extends StudentAbstract implements Student {
    public OutgoingStudent(String name, String country, LodgingService home) throws ServiceIsFullException {
        super(name, country, home);
    }

    public StudentType getType(){
        return StudentType.OUTGOING;
    }

    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted {
        super.updatePosition(position);
        if(visited == null) { // TODO updatePosition é usado pelo construtor do abstrato, então não se consegue inicializar a lista antes
            visited = new DoublyLinkedList<>();
        }
        if(super.visited.indexOf(position) == -1){
            super.visited.addLast(position);
        }

    }

}
