package campus_app.app;

public interface Bounds {
    static String getBoundFilename(String name) {
        return String.format("%s.ser", name);
    }

    String getName();
    Position getTopPosition();
    Position getBottomPosition();
    void save();
    boolean isInside(Position pos);
}
