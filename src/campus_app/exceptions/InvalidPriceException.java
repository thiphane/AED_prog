/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.exceptions;

import campus_app.entity.service.ServiceType;

public class InvalidPriceException extends Exception {
    ServiceType type;
    public InvalidPriceException(ServiceType type) {
        this.type = type;
    }

    public ServiceType getType() {
        return this.type;
    }
}
