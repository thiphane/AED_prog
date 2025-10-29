package campus_app.app;

import campus_app.entity.service.Service;
import dataStructures.Comparator;

public class ServiceStarComparator implements Comparator<Service> {
    @Override
    public int compare(Service o1, Service o2) {
        return o2.getRating() - o1.getRating();
    }
}
