package campus_app.app;
import dataStructures.*;
import campus_app.entity.service.Service;
import campus_app.entity.service.ServiceType;
import campus_app.entity.student.Student;
import campus_app.entity.student.StudentType;

public interface CampusApp {
    void createBounds(String name, long topLongitude, long topLatitude, long bottomLatitude, long bottomLongitude );
    void saveCurrentArea();
    void loadArea(String areaName);
    void createService(ServiceType type, String serviceName, long latitude, long longitude, int price, int value);
    void createStudent(StudentType type, String name, String lodging, String country);
    Student getStudent(String student);
    Service getService(String serviceName);
    Student removeStudent(String studentName);
    boolean updateStudentPosition(String studentName, String service);
    void moveHome(String studentName, String newHome);
    void rateService(int rate, String serviceName, String description);
    TwoWayIterator<Student> getUsersByService(String serviceName);
    Iterator<Student> listAllStudents();
    Iterator<Student> listStudentsByCountry(String country);
    Iterator<Service> listAllServices();
    Iterator<Service> listVisitedServices(String studentName);
    Iterator<Service> listServicesByRanking();
    Iterator<Service> listClosestServicesByStudent(int rating, ServiceType type, String studentName);
    Iterator<Service> listServicesByTag(String tagName);
    Service findBestService(String studentName, ServiceType type);
}
