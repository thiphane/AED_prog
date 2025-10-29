/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.Service;
import campus_app.entity.service.ServiceType;
import campus_app.entity.service.StudentStoringService;
import campus_app.entity.student.Student;
import campus_app.entity.student.StudentType;
import campus_app.exceptions.*;
import dataStructures.FilterIterator;
import dataStructures.Iterator;
import dataStructures.exceptions.NoSuchElementException;

import java.io.*;

public class BoundsClass implements Bounds, Serializable {
    private final String name;
    private final Position topPosition;
    private final Position bottomPosition;
    StudentStorage students;
    ServiceStorage services;
    public BoundsClass(String name, long topLongitude, long topLatitude, long bottomLongitude, long bottomLatitude) throws InvalidBoundPoints {
        this(name, new Position(topLatitude, topLongitude), new Position(bottomLatitude, bottomLongitude));
    }

    public BoundsClass(String name, Position topLeft, Position botRight) throws InvalidBoundPoints {
        if(topLeft.latitude() <= botRight.latitude() || topLeft.longitude() >= botRight.longitude()) {
            throw new InvalidBoundPoints();
        }
        this.name = name;
        this.topPosition = topLeft;
        this.bottomPosition = botRight;
        this.students = new StudentStorage();
        this.services = new ServiceStorage();
    }

    @Override
    public Student getStudent(String student) throws StudentDoesNotExistException {
        return students.getStudent(student);
    }

    @Override
    public Student removeStudent(String studentName) throws StudentDoesNotExistException {
        Iterator<Service> services = this.services.listAllServices();
        Student student = students.getStudent(studentName);
        if(student == null) {
            throw new StudentDoesNotExistException();
        }
        while(services.hasNext()) {
            Service cur = services.next();
            if(cur instanceof StudentStoringService ss) {
                ss.removeUser(student);
            }
        }
        return students.removeStudent(student);
    }

    @Override
    public void updateStudentLocation(Student student, Service newLocation) throws ThriftyStudentIsDistracted, ServiceIsFullException {
        students.updateStudentLocation(student, newLocation);

    }

    @Override
    public void moveHome(String studentName, LodgingService newHomeService) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException, StudentDoesNotExistException {
        students.moveHome(studentName, newHomeService);
    }

    @Override
    public Iterator<Student> getAllStudents() {
        return students.getAllStudents();
    }

    @Override
    public Iterator<Student> getStudentsByCountry(String country) {
        return students.getStudentsByCountry(country);
    }

    @Override
    public Iterator<Service> listAllServices() {
        return services.listAllServices();
    }

    @Override
    public Service findBestService(String studentName, ServiceType type) throws StudentDoesNotExistException {
        Student st = students.getStudent(studentName);
        if(st.getType().equals(StudentType.THRIFTY)) {
            return students.findBestService(st,type,listAllServices());
        }else return students.findBestService(st, type, listServicesByRating());
    }

    @Override
    public Iterator<Service> listVisitedServices(Student student) throws StudentDoesntStoreVisitedServicesException {
        return students.listVisitedServices(student);
    }

    public void addService(Service service) throws AlreadyExistsException {
        this.services.addService(service);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position getTopPosition() {
        return topPosition;
    }

    @Override
    public Position getBottomPosition() {
        return bottomPosition;
    }

    @Override
    public void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Bounds.getBoundFilename(this.getName())))){
            oos.writeObject(this);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isInside(Position pos) {
        return topPosition.latitude() >= pos.latitude() &&
                topPosition.longitude() <= pos.longitude() &&
                bottomPosition.latitude() <= pos.latitude() &&
                bottomPosition.longitude() >= pos.longitude();
    }

    public static Bounds loadBounds(String name) throws BoundsNotDefined {
        Bounds res;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Bounds.getBoundFilename(name)))) {
            res = (Bounds)ois.readObject();
        } catch (FileNotFoundException e) {
            throw new BoundsNotDefined();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public Iterator<Service> findClosestService(String studentName, Iterator<Service> byTypeAndRate) throws StudentDoesNotExistException {
        return students.findClosestService(studentName, byTypeAndRate);
    }

    @Override
    public void addStudent(Student student) {
        students.addStudent(student);
    }

    @Override
    public Service getService(String service) throws ServiceDoesNotExistException {
        return services.getService(service);
    }
    public Iterator<Service>listServicesByRating() {
        return services.listServicesByRanking();
    }

    @Override
    public void addRating(int rate, String serviceName, String description) throws ServiceDoesNotExistException {
        services.rateService(serviceName, rate, description);
    }

}
