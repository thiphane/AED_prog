package campus_app.app;

import dataStructures.*;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;

public class StudentStorage {
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
        return null;
    }

    public Student removeStudent(String student) {
        return null;
    }

    public boolean updateStudentLocation(String student, Service newLocation) {
        return false;
    }

    public void moveHome(String student, Service newHome) {

    }

    public Iterator<Student> getAllStudents() {
        return students.iterator();
    }

    public Iterator<Student> getStudentsByCountry(String country) {
        return new FilterIterator<>(alphabeticalStudents.iterator(), new ByCountryPredicate(country));
    }

    public Iterator<Service> listVisitedServices(String studentName) {
        return null;
    }
}
