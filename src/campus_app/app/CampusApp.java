package campus_app.app;
import campus_app.exceptions.*;
import dataStructures.*;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;

public interface CampusApp {
    void createBounds(String name, long topLatitude, long topLongitude, long bottomLatitude, long bottomLongitude ) throws BoundNameExists, InvalidBoundPoints;
    Bounds saveCurrentArea() throws BoundsNotDefined;
    Bounds loadArea(String areaName) throws BoundsNotDefined;
    void createService(String type, String serviceName, long latitude, long longitude, int price, int value) throws InvalidTypeException, BoundsNotDefined, OutsideBoundsException, InvalidPriceException, InvalidValueException, AlreadyExistsException;
    void createStudent(String type, String name, String lodging, String country) throws AlreadyExistsException, InvalidTypeException, BoundsNotDefined, NoSuchElementOfGivenType, ServiceIsFullException;

    Student getStudent(String student) throws BoundsNotDefined, StudentDoesNotExistException;
    Service getService(String serviceName) throws BoundsNotDefined, ServiceDoesNotExistException;
    Student removeStudent(String studentName) throws BoundsNotDefined, StudentDoesNotExistException;
    boolean updateStudentPosition(Student student, Service service) throws BoundsNotDefined, InvalidTypeException, StudentAlreadyThereException, ServiceIsFullException;
    void moveHome(String studentName, String newHome) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException, BoundsNotDefined, ServiceDoesNotExistException, StudentDoesNotExistException;
    void rateService(int rate, String serviceName, String description) throws BoundsNotDefined, InvalidRating, ServiceDoesNotExistException;
    TwoWayIterator<Student> getUsersByService(String serviceName, Order actualOrder) throws InvalidOrderException, BoundsNotDefined, ServiceDoesNotExistException, CantShowUsersException;;
    Iterator<Student> listAllStudents() throws BoundsNotDefined;
    Iterator<Student> listStudentsByCountry(String country) throws BoundsNotDefined;
    Iterator<Service> listAllServices() throws BoundsNotDefined;
    Iterator<Service> listVisitedServices(Student student) throws StudentDoesntStoreVisitedServicesException, BoundsNotDefined, NoVisitedServicesException;
    Iterator<Service> listServicesByRanking() throws BoundsNotDefined;
    Iterator<Service> listClosestServicesByStudent(int rating, String type, String studentName) throws BoundsNotDefined, InvalidTypeException, IllegalArgumentException, NoSuchElementOfGivenType, NoSuchServiceWithGivenRate, StudentDoesNotExistException;;
    Iterator<Service> listServicesByTag(String tagName) throws BoundsNotDefined;
    Service findBestService(String studentName, String type) throws BoundsNotDefined, StudentDoesNotExistException, InvalidTypeException;
}
