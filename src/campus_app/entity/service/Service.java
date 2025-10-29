/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;
import dataStructures.*;
import campus_app.app.Position;
import campus_app.entity.student.Student;

import java.io.Serializable;

public interface Service extends Serializable {
    String getName();
    ServiceType getType();
    Position getPosition();
    void addRating(int rating, String description);
    int getRating();
    float getRealRating();
    int getPrice();
    boolean hasTag(String tagName);
}
