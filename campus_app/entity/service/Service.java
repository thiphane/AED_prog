package campus_app.entity.service;
import dataStructures.*;
import campus_app.app.Position;
import campus_app.entity.student.Student;

public interface Service {
    String getName();
    ServiceType getType();
    Position getPosition();
    TwoWayIterator<Student> getUsers();
    void addRating(int rating, String description);
    int getRating();
    float getRealRating();
    void addUser(Student student);
    void removeUser(Student student);
    int getPrice();
    boolean hasTag(String tagName);
}
