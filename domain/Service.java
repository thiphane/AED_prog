package domain;
import dataStructures.*;

public interface Service {
    String getName();
    ServiceType getType();
    Position getPosition();
    Iterator<Student> getUsers(Order order);
    void addRating(int rating, String description);
    int getRating();
    void addUser(Student student);
    int getPrice();
    boolean hasTag(String tagName);

}
