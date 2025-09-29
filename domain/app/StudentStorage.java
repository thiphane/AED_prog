package domain.app;

import dataStructures.*;
import domain.entity.service.Service;
import domain.entity.student.Student;

public class StudentStorage {
    protected List<Student> students;
    protected SortedList<Student> alphabeticalStudents;

    public StudentStorage() {
        this.students = new DoublyLinkedList<>();
        this.alphabeticalStudents = new SortedDoublyLinkedList<>();
    }

    void addStudent(Student student) {
        this.students.addLast(student);
        this.alphabeticalStudents.add(student);
    }

    Student getStudent(String student) {
        return null;
    }

    Student removeStudent(String student) {
        return null;
    }

    boolean updateStudentLocation(String student, Service newLocation) {
        return false;
    }

    void moveHome(String student, Service newHome) {

    }

    Iterator<Student> getAllStudents() {
        return students.iterator();
    }

    Iterator<Student> getStudentsByCountry(String country) {
        return new FilterIterator<>(alphabeticalStudents.iterator(), new ByCountryPredicate(country));
    }
    Iterator<Service> listVisitedServices(String studentName) {
        return null;
    }
}
