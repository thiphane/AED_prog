/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.Service;
import dataStructures.Predicate;

public class ServiceTagPredicate implements Predicate<Service> {
    private final String tag;
    public ServiceTagPredicate(String tag) {
        this.tag = tag;
    }

    public boolean check(Service service) {
        return service.hasTag(tag);
    }
}

