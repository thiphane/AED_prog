package campus_app.app;

import campus_app.entity.service.Service;
import dataStructures.Predicate;

public class ServiceRatePredicate implements Predicate<Service> {
    int rate;
    public ServiceRatePredicate(int rate) {
        this.rate = rate;
    }
    @Override
    public boolean check(Service service) {
        return service.getRating()==rate;
    }
}
