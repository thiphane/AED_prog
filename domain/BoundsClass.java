package domain;

public class BoundsClass implements Bounds {
    private final String name;
    private final Position topPosition;
    private final Position bottomPosition;
    public BoundsClass(String name, long topLongitude, long topLatitude, long bottomLongitude, long bottomLatitude) {
        this.name = name;
        this.topPosition = new Position(topLongitude, topLatitude);
        this.bottomPosition = new Position(bottomLongitude, bottomLatitude);
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

}
