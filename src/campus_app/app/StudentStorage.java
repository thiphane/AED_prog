package campus_app.app;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.ServiceType;
import campus_app.entity.service.StudentStoringService;
import campus_app.entity.student.BookishStudent;
import campus_app.entity.student.StudentType;
import campus_app.entity.student.ThriftyStudent;
import campus_app.exceptions.*;
import campus_app.exceptions.ServiceIsFullException;
import dataStructures.*;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;
import dataStructures.exceptions.NoSuchElementException;

import java.io.Serializable;

public class StudentStorage implements Serializable {
    // All students by order of insertion
    protected List<Student> students;
    // All students sorted alphabetically
    protected SortedList<Student> alphabeticalStudents;

    public StudentStorage() {
        this.students = new DoublyLinkedList<>();
        this.alphabeticalStudents = new SortedDoublyLinkedList<>(new AlphabeticalStudentComparator());
    }

    public void addStudent(Student student) {
        this.students.addLast(student);
        this.alphabeticalStudents.add(student);
    }

    public Student getStudent(String student) throws StudentDoesNotExistException {
        Iterator<Student> iterator = this.students.iterator();
        while (iterator.hasNext()) {
            Student element = iterator.next();
            if(element.getName().equalsIgnoreCase(student))
                return element;
        } throw new StudentDoesNotExistException();
    }

    public Student removeStudent(String student) throws StudentDoesNotExistException {
        Student st = alphabeticalStudents.remove(new BookishStudent(student, "", null));
        students.remove(students.indexOf(st));
        if(st == null) throw new StudentDoesNotExistException();
        return st;
    }

    public void updateStudentLocation(Student student, Service newLocation) throws ThriftyStudentIsDistracted, ServiceIsFullException {
        student.updatePosition(newLocation);
    }

    public void moveHome(String student, LodgingService newHome) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException, StudentDoesNotExistException {
        Student s = this.getStudent(student);
        if(s == null) {
            throw new StudentDoesNotExistException();
        }
        s.moveHome(newHome);
    }

    public Iterator<Student> getAllStudents() {
        return alphabeticalStudents.iterator();
    }

    public Iterator<Student> getStudentsByCountry(String country) {
        return new FilterIterator<>(students.iterator(), new ByCountryPredicate(country));
    }

    public Iterator<Service> listVisitedServices(Student student) throws StudentDoesntStoreVisitedServicesException {
        return student.getVisitedServices();
    }

    public Service findBestService(Student student, ServiceType type,  Iterator<Service> services) throws StudentDoesNotExistException {
        return student.findBestService(new FilterIterator<Service>(services, new ServiceTypePredicate(type)));
   }
    public Iterator<Service> findClosestService(String studentName, Iterator<Service> services) throws StudentDoesNotExistException {
        Student s = this.getStudent(studentName);
        if(s == null) {
            throw new StudentDoesNotExistException();
        }
        return s.findClosestServices(services);
    }
}
