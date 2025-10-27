package campus_app.exceptions;

import campus_app.entity.service.Service;

public class ServiceIsFullException extends Exception {
    Service service;

    public ServiceIsFullException(Service service) {
        this.service = service;
    }

    public Service getService() { return this.service; }
}
