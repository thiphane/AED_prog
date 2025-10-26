package campus_app.exceptions;

public class AlreadyExistsException extends Exception {
    private String element;
    public AlreadyExistsException(String element) {
        this.element = element;
    }

    public String getElement() { return this.element; }
}
