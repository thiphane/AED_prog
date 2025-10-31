/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;
import dataStructures.*;
import campus_app.app.Position;
import campus_app.entity.student.Student;

public abstract class ServiceAbstract implements Service {
    private final String name;
    private final Position position;
    private final int price;
    private final int value;
    int rating;
    List<String> ratings;
    protected ServiceAbstract(String serviceName, Position position, int price, int value, ServiceType type) throws InvalidPriceException, InvalidValueException {
        if(price < 0) {
            throw new InvalidPriceException(type);
        }
        if(value < 0) {
            throw new InvalidValueException(type);
        }
        this.name = serviceName;
        this.price = price;
        this.value = value;
        this.rating = 4;
        this.position = position;
        this.ratings = new DoublyLinkedList<>();
    }

    @Override
    public void addRating(int rating, String description) {
        this.rating+=rating;
        ratings.addLast(description); // O(1)
    }

    @Override
    public int getRating() {
        return Math.round(this.getRealRating());
    }

    private float getRealRating() {
        return (float)rating / (ratings.size()+1);
    }

    @Override
    public int getPrice() {
        return price;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position getPosition() {
        return position;
    }
    protected int getValue() {
        return value;
    }

    /**
     * Checks if a service has a comment with the given tag
     * O(n)
     * @param tagName the tag to check
     * @return whether the service has a comment with the given tag
     */
    @Override
    public boolean hasTag(String tagName) {
        // TODO talvez guardar as tags únicamente, em vez de guardar os comentários todos
        Iterator<String> iter = ratings.iterator();
        while(iter.hasNext()) {
            String[] cur = iter.next().split(" ");
            for(String c : cur) {
                if(c.equalsIgnoreCase(tagName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Service service)) {
            return false;
        }
        return this.getName().equalsIgnoreCase(service.getName());
    }
}
