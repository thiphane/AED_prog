package domain.app;

import dataStructures.Predicate;
import domain.entity.student.Student;

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
