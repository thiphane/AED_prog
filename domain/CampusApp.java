package domain;
import dataStructures.*;

public interface CampusApp {
    void bounds(String name, long topLongitude, long topLatitude, long bottomLatitude, long bottomLongitude );
    void save();
    void load(String areaName);
    void createService(String serviceName, long latitude, long longitude, int price, int value);
    void createStudent(StudentType type, String name, String Lodging, String country);
    Student getStudent(String student);
    Service getService(String serviceName);
    void removeStudent(String studentName);
    boolean updateStudentPosition(String studentName, String service);
    void moveHome(String studentName, String newHome);
    void rateService(int rate, String serviceName, String description);
    TwoWayIterator<Student> getUsersByService(String serviceName);
    Iterator<Student> listAllStudents();
    Iterator<Student> listStudentsByCountry(String country);
    Iterator<Service> listAllServices();
    Iterator<Service> listVisitedServices(String studentName);
    Iterator<Service> listServicesByRanking();
    Iterator<Service> listClosestServicesByStudent(int rate, ServiceType type, String studentName);
    Iterator<Service> listServicesByTag(String tagName);
    Service findBestService(String studentName, ServiceType type);
}
