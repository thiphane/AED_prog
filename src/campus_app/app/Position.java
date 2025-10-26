package campus_app.app;

import java.io.Serializable;

public record Position(long latitude, long longitude) implements Serializable {
    public long getManhattanDistance(Position position) {
        return ((position.latitude()-this.latitude())+
                (position.longitude()-this.longitude()));
    }
}
