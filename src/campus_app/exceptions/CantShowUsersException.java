/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.exceptions;

import campus_app.entity.service.Service;

public class CantShowUsersException extends Exception {
    final Service service;

    public CantShowUsersException(Service service) {
        this.service = service;
    }

    public Service getService() { return this.service; }
}
