package domain.entity.service;
import dataStructures.*;
import domain.app.Position;
import domain.entity.student.Student;

public interface Service extends Comparable<Service> {
    String getName();
    ServiceType getType();
    Position getPosition();
    TwoWayIterator<Student> getUsers();
    void addRating(int rating, String description);
    int getRating();
    float getComparableRating();
    void addUser(Student student);
    void removeUser(Student student);
    int getPrice();
    boolean hasTag(String tagName);
}
