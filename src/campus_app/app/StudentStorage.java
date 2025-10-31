/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
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
import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.io.*;

public class StudentStorage implements Serializable {
    // All students by order of insertion
    protected List<Student> students;
    // All students sorted alphabetically
    transient protected SortedList<Student> alphabeticalStudents;

    public StudentStorage() {
        this.students = new DoublyLinkedList<>();
        this.alphabeticalStudents = new SortedDoublyLinkedList<>(new AlphabeticalStudentComparator());
    }

    public void addStudent(Student student) throws AlreadyExistsException {
        this.students.addLast(student); // O(1)
        this.alphabeticalStudents.add(student); // O(n) worst case, O(1) best case (student is the lowest in the alphabetical order)
    }

    public Student getStudent(String student) throws StudentDoesNotExistException {
        Iterator<Student> iterator = this.students.iterator();
        while (iterator.hasNext()) { // O(n)
            Student element = iterator.next();
            if(element.getName().equalsIgnoreCase(student))
                return element;
        } throw new StudentDoesNotExistException();
    }

    public Student removeStudent(Student student) throws StudentDoesNotExistException {
        Student removed = alphabeticalStudents.remove(student);
        if(removed == null) throw new StudentDoesNotExistException();
        students.remove(students.indexOf(removed));
        return removed;
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

    public Service findBestService(Student student, ServiceType type,  Iterator<Service> services) {
        return student.findBestService(new FilterIterator<Service>(services, new ServiceTypePredicate(type)));
   }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Ler os estudantes: O(n)
        // Evitar ler 2 listas do ficheiro, com conteúdo igual
        this.alphabeticalStudents = new SortedDoublyLinkedList<>(new AlphabeticalStudentComparator());
        Iterator<Student> iter = this.students.iterator();
        while(iter.hasNext()) { // O(n)
            alphabeticalStudents.add(iter.next());
        }
    }
}
