package domain.entity.service;

import domain.app.Position;

public class EatingService extends ServiceAbstract implements VisitableService {
    protected EatingService(String serviceName, Position position, int price, int value) {
        super(serviceName, position, price, value);
    }

    @Override
    public ServiceType getType() {
        return ServiceType.EATING;
    }
}
