package campus_app.app;

import campus_app.entity.service.Service;
import campus_app.entity.student.Student;
import campus_app.exceptions.AlreadyExistsException;
import dataStructures.FilterIterator;
import dataStructures.Iterator;

public interface Bounds {
    static String getBoundFilename(String name) {
        return String.format("%s.ser", name.toLowerCase());
    }

    String getName();
    Position getTopPosition();
    Position getBottomPosition();
    void save();
    boolean isInside(Position pos);

    Student removeStudent(String studentName);

    void addService(Service service) throws AlreadyExistsException;

    Student getStudent(String student);

    boolean updateStudentLocation(String studentName, Service newLocation);

    void moveHome(String studentName, Service newHomeService);

    Iterator<Student> getAllStudents();

    Iterator<Student> getStudentsByCountry(String country);

    Iterator<Service> listAllServices();

    Service findBestService(String studentName, FilterIterator<Service> serviceFilterIterator);

    Iterator<Service> listVisitedServices(String studentName);

    Iterator<Service> findClosestService(String studentName, Iterator<Service> byTypeAndRate);

    void addStudent(Student student);

    Service getService(String lodging);

    boolean studentExists(String name);
}
