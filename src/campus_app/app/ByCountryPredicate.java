/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.app;

import dataStructures.Predicate;
import campus_app.entity.student.Student;

public class ByCountryPredicate implements Predicate<Student>  {
    String country;
    public ByCountryPredicate(String country) {
        this.country = country;
    }
    @Override
    public boolean check(Student student) {
        return student.getCountry().equalsIgnoreCase(country);
    }
}
