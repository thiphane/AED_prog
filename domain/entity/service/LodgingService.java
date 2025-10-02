package domain.entity.service;

import domain.app.Position;

public class LodgingService extends ServiceAbstract{
    protected LodgingService(String serviceName, Position position, int price, int value) {
        super(serviceName, position, price, value);
    }

    @Override
    public ServiceType getType() {
        return ServiceType.LODGING;
    }
}
