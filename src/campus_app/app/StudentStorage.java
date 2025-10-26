package campus_app.app;

import dataStructures.*;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;

import java.util.ArrayList;

public class StudentStorage {
    // All students by order of insertion
    protected List<Student> studentsList;
    // All students sorted alphabetically
    protected SortedList<Student> alphabeticalStudents;
    // Accessing a student
    protected List<Student> students;

    protected int MAX_STUDENT_CAPACITY = 1200;

    public StudentStorage() {
        this.studentsList = new DoublyLinkedList<>();
        this.students = new ListInArray<>(MAX_STUDENT_CAPACITY);
        this.alphabeticalStudents = new SortedDoublyLinkedList<>(new AlphabeticalStudentComparator());
    }
    private int hashFunction(String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            hash = (hash * 31 + name.charAt(i)) % MAX_STUDENT_CAPACITY;
        }
        return Math.abs(hash);
    }

    public void addStudent(Student student) {
        int pos = hashFunction(student.getName());
        this.students.add(pos, student);
        this.alphabeticalStudents.add(student);
    }

    public Student getStudent(String student) {
        int pos = hashFunction(student);
        return this.students.get(pos);
    }

    public Student removeStudent(String student) {
        int pos = hashFunction(student);
        return this.students.remove(pos);
    }

    public boolean updateStudentLocation(String student, Service newLocation) {return false;
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
