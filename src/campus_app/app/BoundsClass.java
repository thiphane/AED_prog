package campus_app.app;

import campus_app.exceptions.InvalidBoundPoints;

public class BoundsClass implements Bounds {
    private final String name;
    private final Position topPosition;
    private final Position bottomPosition;
    public BoundsClass(String name, long topLongitude, long topLatitude, long bottomLongitude, long bottomLatitude) throws InvalidBoundPoints {
        this(name, new Position(topLatitude, topLongitude), new Position(bottomLatitude, bottomLongitude));
    }

    public BoundsClass(String name, Position topLeft, Position botRight) throws InvalidBoundPoints {
        if(topLeft.latitude() <= botRight.latitude() || topLeft.longitude() >= botRight.longitude()) {
            throw new InvalidBoundPoints();
        }
        this.name = name;
        this.topPosition = topLeft;
        this.bottomPosition = botRight;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position getTopPosition() {
        return topPosition;
    }

    @Override
    public Position getBottomPosition() {
        return bottomPosition;
    }

    @Override
    public void save() {

    }

    @Override
    public boolean isInside(Position pos) {
        return topPosition.latitude() >= pos.latitude() &&
                topPosition.longitude() <= pos.longitude() &&
                bottomPosition.latitude() <= pos.latitude() &&
                bottomPosition.longitude() >= pos.longitude();
    }
}
