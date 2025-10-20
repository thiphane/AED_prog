package campus_app.entity.service;
public enum ServiceType {
    LEISURE,EATING,LODGING;
    public static ServiceType getType(String type) {
        return valueOf(type.toUpperCase().trim());
    }
}
