/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.Service;
import campus_app.entity.service.ServiceType;
import dataStructures.Predicate;

public class ServiceTypePredicate implements Predicate<Service> {
    private final ServiceType type;
    public ServiceTypePredicate(ServiceType type) {
        this.type = type;
    }

    public boolean check(Service service) {
        return service.getType().equals(type);
    }
}

