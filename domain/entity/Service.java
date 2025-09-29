package domain.entity;
import dataStructures.*;
import domain.app.ServiceType;
import domain.app.Order;
import domain.app.Position;
import domain.entity.student.Student;

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
