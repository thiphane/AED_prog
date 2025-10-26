package campus_app.app;

import campus_app.entity.service.Service;
import campus_app.entity.student.Student;
import campus_app.exceptions.AlreadyExistsException;
import campus_app.exceptions.BoundsNotDefined;
import campus_app.exceptions.InvalidBoundPoints;
import dataStructures.FilterIterator;
import dataStructures.Iterator;

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
    public Student getStudent(String student) {
        return students.getStudent(student);
    }

    @Override
    public Student removeStudent(String studentName) {
        return students.removeStudent(studentName);
    }

    @Override
    public boolean updateStudentLocation(String studentName, Service newLocation) {
        return students.updateStudentLocation(studentName, newLocation);
    }

    @Override
    public void moveHome(String studentName, Service newHomeService) {
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
    public Service findBestService(String studentName, FilterIterator<Service> serviceFilterIterator) {
        return students.findBestService(studentName, serviceFilterIterator);
    }

    @Override
    public Iterator<Service> listVisitedServices(String studentName) {
        return students.listVisitedServices(studentName);
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
}
