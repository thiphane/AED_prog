package campus_app.entity.service;

import campus_app.app.Position;
import campus_app.exceptions.InvalidValueException;

public class LeisureService extends ServiceAbstract implements Service {
    public LeisureService(String serviceName, Position position, int price, int value) throws InvalidValueException {
        super(serviceName, position, price, value);
        if(value < 0 || value > 100) {
            throw new InvalidValueException(this.getType());
        }
    }

    @Override
    public ServiceType getType() {
        return ServiceType.LEISURE;
    }
}
