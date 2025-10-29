/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;

import campus_app.app.Position;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;

public class LodgingService extends AbstractStudentStoringService {
    private static final ServiceType TYPE = ServiceType.LODGING;
    public LodgingService(String serviceName, Position position, int price, int value) throws InvalidValueException, InvalidPriceException {
        super(serviceName, position, price, value, TYPE);
        if(value <= 0) {
            throw new InvalidValueException(this.getType());
        }
    }

    @Override
    public ServiceType getType() {
        return TYPE;
    }
}
