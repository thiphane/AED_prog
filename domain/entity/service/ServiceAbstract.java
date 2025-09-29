package domain.entity.service;
import dataStructures.*;
import domain.app.Order;
import domain.app.Position;
import domain.entity.student.Student;

public abstract class ServiceAbstract implements Service {
    private final String name;
    private final Position position;
    private final int price;
    private final int value;
    int rating;
    int ratingCount;
    private DoublyLinkedList<Student> users;
    protected ServiceAbstract(String serviceName, Position position, int price, int value) {
        this.name = serviceName;
        this.price = price;
        this.value = value;
        this.rating = 4;
        this.ratingCount = 1;
        this.position = position;

    }
    @Override
    public void addRating(int rating, String description) {
        rating+=rating;
    }
    @Override
    public void addUser(Student student) {
        users.addLast(student);
    }
    @Override
    public int getRating() {
        return rating/ratingCount;
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
        return false;
    }
    @Override
    public TwoWayIterator<Student> getUsers(){
        return users.twoWayiterator();
    }
}
