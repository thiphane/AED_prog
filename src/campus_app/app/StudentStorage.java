/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import campus_app.exceptions.*;
import dataStructures.*;
import campus_app.entity.student.Student;

import java.io.*;

public class StudentStorage implements Serializable {
    // TODO decidir quantidades
    private static final int EXPECTED_STUDENT_COUNT = 200;
    private static final int EXPECTED_COUNTRY_COUNT = 10;
    // We only serialize studentsByCountry because it needs to save the insertion order
    // All students by order of insertion
    protected final Map<String, ObjectRemovalList<Student>> studentsByCountry;
    // All students sorted alphabetically
    transient protected SortedList<Student> alphabeticalStudents;
    transient protected Map<String, Student> studentsByName;

    public StudentStorage() {
        this.studentsByCountry = new SepChainHashTable<>(EXPECTED_COUNTRY_COUNT);
        this.alphabeticalStudents = new SortedDoublyLinkedList<>(new AlphabeticalStudentComparator());
        this.studentsByName = new SepChainHashTable<>(EXPECTED_STUDENT_COUNT);
    }

    public void addStudent(Student student) {
        String cnty = student.getCountry().toLowerCase();
        ObjectRemovalList<Student> countryList = this.studentsByCountry.get(cnty);
        if ( countryList == null ) {
            countryList = new ObjectRemovalSinglyList<>();
            this.studentsByCountry.put(cnty, countryList);
        }
        countryList.addLast(student); // O(1)
        this.alphabeticalStudents.add(student); // O(log n)
        this.studentsByName.put(student.getName().toLowerCase(), student);
    }

    public Student getStudent(String student) throws StudentDoesNotExistException {
        Student s = studentsByName.get(student.toLowerCase());
        if ( s == null ) { throw new StudentDoesNotExistException(); }
        return s;
    }

    public Student removeStudent(Student student) throws StudentDoesNotExistException {
        Student removed = alphabeticalStudents.remove(student); // O(log n)
        if(removed == null) throw new StudentDoesNotExistException();
        List<Student> cList = studentsByCountry.get(student.getCountry().toLowerCase());
        // TODO não usar remove(indexof()), ObjectRemovalSinglyList
        assert cList.remove(cList.indexOf(student)) != null; // O(n)
        assert studentsByName.remove(student.getName().toLowerCase()) != null;
        return removed;
    }

    public Iterator<Student> getAllStudents() {
        return alphabeticalStudents.iterator();
    }

    public Iterator<Student> getStudentsByCountry(String country) {
        List<Student> c = studentsByCountry.get(country.toLowerCase());
        if ( c == null ) { return new EmptyIterator<>(); }
        return c.iterator();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Ler os estudantes por pais: O(n)
        // Evitar ler 2 listas do ficheiro, com conteúdo igual
        this.alphabeticalStudents = new SortedDoublyLinkedList<>(new AlphabeticalStudentComparator());
        this.studentsByName = new SepChainHashTable<>(EXPECTED_STUDENT_COUNT);
        Iterator<Map.Entry<String, ObjectRemovalList<Student>>> mapIter = this.studentsByCountry.iterator();
        while(mapIter.hasNext()) { // O(n)
            Iterator<Student> iter = mapIter.next().value().iterator();
            while ( iter.hasNext()) {
                Student cur = iter.next();
                alphabeticalStudents.add(cur);
                studentsByName.put(cur.getName().toLowerCase(), cur);
            }
        }
    }
}
