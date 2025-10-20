package campus_app.entity.service;

import campus_app.app.Position;

public class LeisureService extends ServiceAbstract implements Service {
    protected LeisureService(String serviceName, Position position, int price, int value) {
        super(serviceName, position, price, value);
    }

    @Override
    public ServiceType getType() {
        return ServiceType.LEISURE;
    }
}
