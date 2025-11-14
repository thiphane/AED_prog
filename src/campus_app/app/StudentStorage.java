/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.entity.service.ServiceType;
import campus_app.exceptions.*;
import dataStructures.*;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;

import java.io.*;

public class StudentStorage implements Serializable {
    // All students by order of insertion
    protected final List<Student> students;
    // All students sorted alphabetically
    transient protected SortedList<Student> alphabeticalStudents;
    transient protected Map<String, Student> studentsByName;

    public StudentStorage() {
        this.students = new DoublyLinkedList<>();
        this.alphabeticalStudents = new SortedDoublyLinkedList<>(new AlphabeticalStudentComparator());
        this.studentsByName = new SepChainHashTable<>();
    }

    public void addStudent(Student student) {
        this.students.addLast(student); // O(1)
        this.alphabeticalStudents.add(student); // O(log n)
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
        Student removed = alphabeticalStudents.remove(student); // O(log n)
        if(removed == null) throw new StudentDoesNotExistException();
        students.remove(students.indexOf(removed)); // O(n)
        return removed;
    }

    public Iterator<Student> getAllStudents() {
        return alphabeticalStudents.iterator();
    }

    public Iterator<Student> getStudentsByCountry(String country) {
        return new FilterIterator<>(students.iterator(), new ByCountryPredicate(country));
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
