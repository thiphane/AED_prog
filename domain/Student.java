package domain;

import dataStructures.*;

public interface Student {
    String getName();
    Service getLocation();
    ServiceType getType();
    Service getHome();
    void moveHome(Service home);
    void updatePosition(Service position);
    Service findBestService(ServiceType type, Iterator<Service> services);

}
