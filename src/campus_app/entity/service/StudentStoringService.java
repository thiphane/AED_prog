/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;

import campus_app.entity.student.Student;
import campus_app.exceptions.ServiceIsFullException;
import dataStructures.TwoWayIterator;

public interface StudentStoringService extends Service, StudentStoringServiceRead, StudentStoringServiceWrite {
}
