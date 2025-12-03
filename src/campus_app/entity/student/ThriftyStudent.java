/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;

import campus_app.entity.service.EatingService;
import campus_app.entity.service.LodgingService;
import campus_app.entity.service.ServiceRead;
import campus_app.exceptions.*;
import dataStructures.Iterator;
import campus_app.entity.service.Service;

public class ThriftyStudent extends StudentAbstract {
    EatingService cheapestEating;
    public ThriftyStudent(String name, String country, LodgingService home) throws ServiceIsFullException {
        super(name, country, home);
        this.cheapestEating = null;
    }
    public StudentType getType(){
        return StudentType.THRIFTY;
    }

    @Override
    public void updatePosition(Service position) throws ServiceIsFullException, ThriftyStudentIsDistracted, StudentAlreadyThereException {
        super.updatePosition(position);
        if(position instanceof EatingService eat) {
            if(cheapestEating == null || eat.getPrice() <= cheapestEating.getPrice()) {
                this.cheapestEating = eat;
            } else {
                throw new ThriftyStudentIsDistracted(this);
            }
        }
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
    public Iterator<ServiceRead> getVisitedServices() throws StudentDoesntStoreVisitedServicesException{
        throw new StudentDoesntStoreVisitedServicesException(this);
    }
}
