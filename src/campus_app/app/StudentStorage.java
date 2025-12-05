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
    private static final int EXPECTED_COUNTRY_COUNT = 10;
    // We only serialize studentsByCountry because it needs to save the insertion order
    // All students by order of insertion
    protected final Map<String, ObjectRemovalList<Student>> studentsByCountry;
    // All students sorted alphabetically
    transient protected SortedMap<String, Student> alphabeticalStudentsByName;

    /**
     * Constructor.
     * O(1)
     */
    public StudentStorage() {
        this.studentsByCountry = new SepChainHashTable<>(EXPECTED_COUNTRY_COUNT);
        this.alphabeticalStudentsByName = new AVLSortedMap<>();
    }

    /**
     * Inserting in a Map has Complexity O(log(n)), so expected case complexity is O(log n)
     * worst case complexity when map needs to be rehashed, so Time complexity is O(n)
     *
     * @param student to be inserted.
     */
    public void addStudent(Student student) {
        String cnty = student.getCountry().toLowerCase();
        ObjectRemovalList<Student> countryList = this.studentsByCountry.get(cnty); // O(log n)
        if ( countryList == null ) {
            countryList = new ObjectRemovalSinglyList<>();
            this.studentsByCountry.put(cnty, countryList);
        }
        countryList.addLast(student); // O(1)
        this.alphabeticalStudentsByName.put(student.getName().toLowerCase(), student); // O(log n)
    }

    /**
     * Has time complexity O(log(n))
     * @param student to be searched
     * @return student, if its found
     * @throws StudentDoesNotExistException when student does not exist
     */
    public Student getStudent(String student) throws StudentDoesNotExistException {
        Student s = this.alphabeticalStudentsByName.get(student.toLowerCase());
        if ( s == null ) { throw new StudentDoesNotExistException(); }
        return s;
    }

    /**
     * Time complexity is O(n), for worst case because we have to remove student
     * from country list, best case has Complexity O(log(n))-from removing from the sortedMap-
     * if the student is the first in the country list.
     *
     *
     * @param student to remove.
     * @return removed student, if student its found.
     * @throws StudentDoesNotExistException when student does not exist.
     */
    public Student removeStudent(Student student) throws StudentDoesNotExistException {
        Student removed = alphabeticalStudentsByName.remove(student.getName().toLowerCase()); // O(log n)
        if(removed == null) throw new StudentDoesNotExistException();
        ObjectRemovalList<Student> cList = studentsByCountry.get(student.getCountry().toLowerCase());
        cList.remove(student); // O(n)
        return removed;
    }

    /**
     *
     * @return Student list ordered by their names.
     */
    public Iterator<Student> getAllStudents() {
        return alphabeticalStudentsByName.values();
    }

    /**
     *
     * @param country Country name to be searched
     * @return An iterator with all students from the given country.
     */
    public Iterator<Student> getStudentsByCountry(String country) {
        List<Student> c = studentsByCountry.get(country.toLowerCase());
        if ( c == null ) { return new EmptyIterator<>(); }
        return c.iterator();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Ler os estudantes por pais: O(n)
        // Evitar ler 2 listas do ficheiro, com conteúdo igual
        this.alphabeticalStudentsByName = new AVLSortedMap<>();
        Iterator<Map.Entry<String, ObjectRemovalList<Student>>> mapIter = this.studentsByCountry.iterator();
        while(mapIter.hasNext()) { // O(n)
            Iterator<Student> iter = mapIter.next().value().iterator();
            while ( iter.hasNext()) {
                Student cur = iter.next();
                alphabeticalStudentsByName.put(cur.getName().toLowerCase(), cur);
            }
        }
    }
}
