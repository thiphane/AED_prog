/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;
public enum ServiceType {
    LEISURE,EATING,LODGING;
    public static ServiceType getType(String type) {
        return valueOf(type.toUpperCase().trim());
    }
}
