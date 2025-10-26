package campus_app.app;
import campus_app.exceptions.*;
import dataStructures.*;
import campus_app.entity.service.Service;
import campus_app.entity.service.ServiceType;
import campus_app.entity.student.Student;
import dataStructures.exceptions.NoSuchElementException;

public interface CampusApp {
    void createBounds(String name, long topLatitude, long topLongitude, long bottomLatitude, long bottomLongitude ) throws BoundNameExists, InvalidBoundPoints;
    Bounds saveCurrentArea() throws BoundsNotDefined;
    Bounds loadArea(String areaName) throws BoundsNotDefined;
    void createService(String type, String serviceName, long latitude, long longitude, int price, int value) throws InvalidTypeException, BoundsNotDefined, OutsideBoundsException, InvalidPriceException, InvalidValueException, AlreadyExistsException;
    void createStudent(String type, String name, String lodging, String country) throws AlreadyExistsException, InvalidTypeException, BoundsNotDefined;

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
    Iterator<Service> listClosestServicesByStudent(int rating, String type, String studentName);
    Iterator<Service> listServicesByTag(String tagName);
    Service findBestService(String studentName, ServiceType type);
}
