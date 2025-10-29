package campus_app.exceptions;

import campus_app.entity.service.Service;

public class NoStudentsException extends Exception {
    Service service;
    public NoStudentsException(Service where) {
        this.service = where;
    }

    public Service getService() { return this.service; }
}
