package domain.entity.service;

import domain.app.Position;

public class LeisureService extends ServiceAbstract implements VisitableService {
    protected LeisureService(String serviceName, Position position, int price, int value) {
        super(serviceName, position, price, value);
    }

    @Override
    public ServiceType getType() {
        return ServiceType.LEISURE;
    }
}
