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
    private TwoWayList<Student> users;
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
    public boolean isFull() {
        return value>users.size();
    }

    @Override
    public void addRating(int rating, String description) {
        this.rating+=rating;
        ratings.addLast(description);
    }
    @Override
    public void addUser(Student student) {
        users.addLast(student);
    }
    @Override
    public void removeUser(Student student) {
        //users.remove(student);
    }
    @Override
    public int getRating() {
        return Math.round(this.getRealRating());
    }

    @Override
    public float getRealRating() {
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
    @Override
    public boolean hasTag(String tagName) {
        Iterator<String> iter = ratings.iterator();
        while(iter.hasNext()) {
            if(iter.next().contains(tagName)) { return true; }
        }
        return false;
    }
    @Override
    public TwoWayIterator<Student> getUsers(){
        return users.twoWayiterator();
    }
}
