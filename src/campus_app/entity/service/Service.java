/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;
import campus_app.app.Position;

import java.io.Serializable;

public interface Service extends Serializable, ServiceRead {
    /**
     * Adds a new evaluation to the service
     * O(1)
     * @param rating the rating of the evaluation
     * @param description the description of the evaluation
     */
    void addRating(int rating, String description);
}
