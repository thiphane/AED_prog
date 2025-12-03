package campus_app.entity.service;

import campus_app.app.Position;

public interface ServiceRead {
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
