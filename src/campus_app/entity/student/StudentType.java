/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;


public enum StudentType {
    THRIFTY, OUTGOING, BOOKISH;
    public static StudentType getType(String type) {
        return valueOf(type.toUpperCase().trim());
    }
}
