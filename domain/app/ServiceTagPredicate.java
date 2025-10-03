package domain.app;

import dataStructures.Predicate;
import domain.entity.service.Service;

public class ServiceTagPredicate implements Predicate<Service> {
    private final String tag;
    public ServiceTagPredicate(String tag) {
        this.tag = tag;
    }

    public boolean check(Service service) {
        return service.hasTag(tag);
    }
}
