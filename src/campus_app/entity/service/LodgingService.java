package campus_app.entity.service;

import campus_app.app.Position;
import campus_app.exceptions.InvalidValueException;

public class LodgingService extends ServiceAbstract {
    public LodgingService(String serviceName, Position position, int price, int value) throws InvalidValueException {
        super(serviceName, position, price, value);
        if(value <= 0) {
            throw new InvalidValueException(this.getType());
        }
    }

    @Override
    public ServiceType getType() {
        return ServiceType.LODGING;
    }
}
