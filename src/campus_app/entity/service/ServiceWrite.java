package campus_app.entity.service;

public interface ServiceWrite {
    /**
     * Adds a new evaluation to the service
     * O(1)
     * @param rating the rating of the evaluation
     * @param description the description of the evaluation
     */
    void addRating(int rating, String description);
}
