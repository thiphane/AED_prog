package campus_app.exceptions;

import campus_app.entity.service.Service;

public class CantShowUsersException extends Exception { Service service;

    public CantShowUsersException(Service service) {
        this.service = service;
    }

    public Service getService() { return this.service; }
}
