package campus_app.entity.student;


public enum StudentType {
    THRIFTY, OUTGOING, BOOKISH;
    public static StudentType getType(String type) {
        return valueOf(type.toUpperCase().trim());
    }
}
