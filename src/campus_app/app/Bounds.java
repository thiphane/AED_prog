package campus_app.app;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;
import campus_app.exceptions.*;
import campus_app.exceptions.AlreadyExistsException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
import dataStructures.FilterIterator;
import dataStructures.Iterator;
import dataStructures.exceptions.NoSuchElementException;

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

    Student getStudent(String student) throws NoSuchElementException;

    void updateStudentLocation(String studentName, Service newLocation) throws ThriftyStudentIsDistracted, ServiceIsFullException;

    void moveHome(String studentName, LodgingService newHomeService);

    Iterator<Student> getAllStudents();

    Iterator<Student> getStudentsByCountry(String country);

    Iterator<Service> listAllServices();

    Service findBestService(String studentName, FilterIterator<Service> serviceFilterIterator);

    Iterator<Service> listVisitedServices(String studentName);

    Iterator<Service> findClosestService(String studentName, Iterator<Service> byTypeAndRate);

    void addStudent(Student student);

    Service getService(String service);
}
