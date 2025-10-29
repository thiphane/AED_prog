package campus_app.app;

import java.io.Serializable;

public record Position(long latitude, long longitude) implements Serializable {
    public long getManhattanDistance(Position position) {
        return ( Math.abs(position.latitude()-this.latitude())+
                Math.abs(position.longitude()-this.longitude()));
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.latitude(), this.longitude());
    }
}
