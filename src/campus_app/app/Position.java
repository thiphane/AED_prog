package campus_app.app;

public record Position(long latitude, long longitude){

    public long getManhattanDistance(Position position) {
        return ((position.latitude()-this.latitude())+
                (position.longitude()-this.longitude()));
    }
}
