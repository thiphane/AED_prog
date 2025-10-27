package campus_app.app;

import campus_app.entity.service.LodgingService;
import campus_app.entity.service.StudentStoringService;
import campus_app.entity.student.BookishStudent;
import campus_app.exceptions.InvalidTypeException;
import campus_app.exceptions.MoveNotAcceptable;
import campus_app.exceptions.SameHomeException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ServiceIsFullException;
import campus_app.exceptions.ThriftyStudentIsDistracted;
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

    public Student getStudent(String student) throws NoSuchElementException {
        // TODO students.get(students.indexof()) vai passar pela lista 2 vezes, mas isto não fica muito bonito
        return alphabeticalStudents.get(new BookishStudent(student, null, ""));
    }

    public Student removeStudent(String student) {
        Student st = alphabeticalStudents.remove(new BookishStudent(student, null, ""));
        students.remove(students.indexOf(st));
        return st;
    }

    public void updateStudentLocation(String student, Service newLocation) throws ThriftyStudentIsDistracted, ServiceIsFullException {
        Student actualStudent = getStudent(student);
        actualStudent.updatePosition(newLocation);
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

    public Iterator<Service> listVisitedServices(String studentName) {
        return this.getStudent(studentName).getVisitedServices();
    }

    public Service findBestService(String studentName, Iterator<Service> services) {
        return this.getStudent(studentName).findBestService(services);
    }
    public Iterator<Service> findClosestService(String studentName, Iterator<Service> services) {
        return this.getStudent(studentName).findClosestServices(services);
    }
}
