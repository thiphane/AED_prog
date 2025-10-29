/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.exceptions;

public class AlreadyExistsException extends Exception {
    private String element;
    public AlreadyExistsException(String element) {
        this.element = element;
    }

    public String getElement() { return this.element; }
}
