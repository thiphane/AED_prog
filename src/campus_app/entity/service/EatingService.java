package campus_app.entity.service;

import campus_app.app.Position;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;

public class EatingService extends AbstractStudentStoringService {
    private static final ServiceType TYPE = ServiceType.EATING;
    public EatingService(String serviceName, Position position, int price, int value) throws InvalidValueException, InvalidPriceException {
        super(serviceName, position, price, value, TYPE);
        if(value <= 0) {
            throw new InvalidValueException(TYPE);
        }
    }

    @Override
    public ServiceType getType() {
        return TYPE;
    }
}
