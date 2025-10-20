package campus_app.exceptions;

import campus_app.entity.service.ServiceType;

public class InvalidValueException extends Exception {
    ServiceType type;
    public InvalidValueException(ServiceType type) {
        this.type = type;
    }

    public ServiceType getType() {
        return this.type;
    }
}
