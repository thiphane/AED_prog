package campus_app.app;

import campus_app.entity.student.BookishStudent;
import dataStructures.*;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;

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

    public Student getStudent(String student) {
        return alphabeticalStudents.get(new BookishStudent(student, null, ""));
    }

    public Student removeStudent(String student) {
        Student st = alphabeticalStudents.remove(new BookishStudent(student, null, ""));
        students.remove(students.indexOf(st));
        return st;
    }

    public boolean updateStudentLocation(String student, Service newLocation) {
        return false;
    }

    public void moveHome(String student, Service newHome) {
        this.getStudent(student).moveHome(newHome);
    }

    public Iterator<Student> getAllStudents() {
        return students.iterator();
    }

    public Iterator<Student> getStudentsByCountry(String country) {
        return new FilterIterator<>(alphabeticalStudents.iterator(), new ByCountryPredicate(country));
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
