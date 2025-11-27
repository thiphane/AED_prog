/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.Service;
import campus_app.entity.service.ServiceType;
import campus_app.entity.service.StudentStoringService;
import campus_app.entity.student.Student;
import campus_app.entity.student.StudentType;
import campus_app.exceptions.*;
import dataStructures.FilterIterator;
import dataStructures.Iterator;

import java.io.*;

public class BoundsClass implements Bounds, Serializable {
    private final String name;
    private final Position topPosition;
    private final Position bottomPosition;
    StudentStorage students;
    ServiceStorage services;
    private Service[] cheapestServices;
    public BoundsClass(String name, long topLongitude, long topLatitude, long bottomLongitude, long bottomLatitude) throws InvalidBoundPoints {
        this(name, new Position(topLatitude, topLongitude), new Position(bottomLatitude, bottomLongitude));
    }

    public BoundsClass(String name, Position topLeft, Position bottomRight) throws InvalidBoundPoints {
        if(topLeft.latitude() <= bottomRight.latitude() || topLeft.longitude() >= bottomRight.longitude()) {
            throw new InvalidBoundPoints();
        }
        this.name = name;
        this.topPosition = topLeft;
        this.bottomPosition = bottomRight;
        this.students = new StudentStorage();
        this.services = new ServiceStorage();
        this.cheapestServices = new Service[ServiceType.values().length];
    }

    @Override
    public Student getStudent(String student) throws StudentDoesNotExistException {
        return students.getStudent(student);
    }

    @Override
    public Student removeStudent(String studentName) throws StudentDoesNotExistException {
        Iterator<Service> services = this.services.listAllServices(); // O(1)
        Student student = students.getStudent(studentName);  // O(n)
        if(student == null) {
            throw new StudentDoesNotExistException();
        }
        while(services.hasNext()) { // O(n)
            Service cur = services.next();
            if(cur instanceof StudentStoringService ss) {
                ss.removeUser(student); // O(n)
            }
        } // O(n^2)
        return students.removeStudent(student);
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
        Student st = students.getStudent(studentName); // O(n)
        if(st.getType().equals(StudentType.THRIFTY)) {
            if ( cheapestServices[type.ordinal()] == null) { return null; }
            return cheapestServices[type.ordinal()];
        }else return st.findBestService(new FilterIterator<>(listServicesByRating(), new ServiceTypePredicate(type)));
    }

    /**
     * Adds a new service to the bounds
     * O(n)
     * @param service the service to add
     * @throws AlreadyExistsException if a service with the same name already exists
     */
    @Override
    public void addService(Service service) throws AlreadyExistsException {
        if ( cheapestServices[service.getType().ordinal()] == null ||
                cheapestServices[service.getType().ordinal()].getPrice() > service.getPrice() ) {
            cheapestServices[service.getType().ordinal()] = service;
        }
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
            oos.writeObject(this); // O(n), tem de escrever os estudantes e serviços
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

    /**
     * Adds a new student to the bounds
     * O(n)
     * @param student the student
     */
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
    public void updateServiceRating(Service service, int oldRating) {
        services.updateServiceRating(service, oldRating);
    }
}
