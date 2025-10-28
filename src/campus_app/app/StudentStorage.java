package campus_app.app;

import campus_app.entity.service.LodgingService;
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

    public Student getStudent(String student){
        Iterator<Student> iterator = this.students.iterator();
        while (iterator.hasNext()) {
            Student element = iterator.next();
            if(element.getName().equalsIgnoreCase(student))return element;
        } return null;
    }

    public Student removeStudent(String student) {
        Student st = alphabeticalStudents.remove(new BookishStudent(student));
        students.remove(students.indexOf(st));
        return st;
    }

    public void updateStudentLocation(Student student, Service newLocation) throws ThriftyStudentIsDistracted, ServiceIsFullException {
        boolean isDistracted = false;
        if(student instanceof ThriftyStudent std){
            isDistracted = std.isDistracted(newLocation);
        }
        student.updatePosition(newLocation);
        if(isDistracted)throw new ThriftyStudentIsDistracted();
    }

    public void moveHome(String student, LodgingService newHome) throws ServiceIsFullException, MoveNotAcceptable, SameHomeException {
        this.getStudent(student).moveHome(newHome);
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

    public Service findBestService(String studentName, Iterator<Service> services) {
        return this.getStudent(studentName).findBestService(services);
    }
    public Iterator<Service> findClosestService(String studentName, Iterator<Service> services) {
        return this.getStudent(studentName).findClosestServices(services);
    }
}
