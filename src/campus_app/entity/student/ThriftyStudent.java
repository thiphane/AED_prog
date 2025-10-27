package campus_app.entity.student;

import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import campus_app.entity.service.Service;

public class ThriftyStudent extends StudentAbstract {
    public ThriftyStudent(String name, Service home, String country) {
        super(name, home, country);
        super.visited = new DoublyLinkedList<>();
    }

    public StudentType getType(){
        return StudentType.THRIFTY;
    }

    @Override
    public void updatePosition(Service position) throws ThriftyStudentIsDistracted, ServiceIsFullException {
        // TODO throw exception se estiver distraido, mas ainda fazer os outros efeitos
        boolean isDistracted =this.getLocation().getPrice()<position.getPrice();
        super.updatePosition(position);
        if(isDistracted)throw new ThriftyStudentIsDistracted();
    }

    @Override
    public Service findBestService(Iterator<Service> services) {
        return null;
    }

    @Override
    public void moveHome(Service home) {
        // TODO throw exception se nao for valido
        super.moveHome(home);
    }

    @Override
    public Iterator<Service> getVisitedServices() {
        // throw new exception
        return null;
    }}
