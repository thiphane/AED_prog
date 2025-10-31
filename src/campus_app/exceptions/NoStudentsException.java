/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.exceptions;

import campus_app.entity.service.Service;

public class NoStudentsException extends Exception {
    final Service service;
    public NoStudentsException(Service where) {
        this.service = where;
    }

    public Service getService() { return this.service; }
}
