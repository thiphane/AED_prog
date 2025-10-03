package domain.app;

import domain.entity.service.Service;

public class ServiceTypePredicate implements Predicate<Service> {
    private ServiceType type;
    public ServiceTypePredicate(ServiceType type) {
        this.type = type;
    }

    public boolean check(Service service) {
        return service.getType().equals(type);
    }
}
