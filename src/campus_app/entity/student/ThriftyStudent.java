package campus_app.entity.student;

import dataStructures.Iterator;
import campus_app.entity.service.Service;

public class ThriftyStudent extends StudentAbstract {
    public ThriftyStudent(String name, Service home, String country) {
        super(name, home, country);
    }

    public StudentType getType(){
        return StudentType.THRIFTY;
    }

    @Override
    public void updatePosition(Service position) {
        // TODO throw exception se estiver distraido, mas ainda fazer os outros efeitos
        super.updatePosition(position);
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
