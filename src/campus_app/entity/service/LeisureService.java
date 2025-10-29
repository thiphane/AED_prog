package campus_app.entity.service;

import campus_app.app.Position;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;

public class LeisureService extends ServiceAbstract implements Service {
    private static final ServiceType TYPE = ServiceType.LEISURE;
    public LeisureService(String serviceName, Position position, int price, int value) throws InvalidValueException, InvalidPriceException {
        super(serviceName, position, price, value, TYPE);
        if(value < 0 || value > 100) {
            throw new InvalidValueException(this.getType());
        }
    }

    @Override
    public int getPrice() {
        float disc = this.getValue() / 100f;
        return Math.round(super.getPrice() - (super.getPrice() * disc));
    }

    @Override
    public ServiceType getType() {
        return TYPE;
    }
}
