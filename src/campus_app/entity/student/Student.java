/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;

import campus_app.entity.service.LodgingService;
import campus_app.exceptions.*;
import campus_app.entity.service.Service;

import java.io.Serializable;

public interface Student extends Serializable, StudentRead {
    /**
     * changes the student's home
     * O(n)
     * @param home the new home
     * @throws ServiceIsFullException if the new home is full
     * @throws MoveNotAcceptable if the student refuses to move to the new home
     * @throws SameHomeException if the student tries to move into their own home
     */
    void moveHome(LodgingService home) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException;

    /**
     * changes the user's current position
     * O(1) best case, O(n) worst case, expected O(n)
     * @param position the new position
     * @throws ThriftyStudentIsDistracted if the student was distracted by this moving; they are still moved if this is thrown
     * @throws ServiceIsFullException if the new position is full
     */
    void updatePosition(Service position) throws ThriftyStudentIsDistracted, ServiceIsFullException, StudentAlreadyThereException;
}
