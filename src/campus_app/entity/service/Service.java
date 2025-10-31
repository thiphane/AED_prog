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
    /**
     * O(1)
     * @return the name of the service
     */
    String getName();

    /**
     * O(1)
     * @return the type of service
     */
    ServiceType getType();

    /**
     * O(1)
     * @return the position of the service
     */
    Position getPosition();

    /**
     * Adds a new evaluation to the service
     * O(1)
     * @param rating the rating of the evaluation
     * @param description the description of the evaluation
     */
    void addRating(int rating, String description);

    /**
     * Gets the average rating of the service
     * O(1)
     * @return the average rating of all evaluations
     */
    int getRating();

    /**
     * O(1)
     * @return the price of the service
     */
    int getPrice();

    /**
     * Checks if the service has an evaluation with the given tag
     * @param tagName the tag to find
     * @return whether the service has an evaluation with the given tag
     */
    boolean hasTag(String tagName);
}
