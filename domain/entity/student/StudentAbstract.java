package domain.entity.student;

import dataStructures.Iterator;
import domain.app.ServiceType;
import domain.entity.service.Service;

public abstract class StudentAbstract implements Student {
    private final String name;
    private final String country;
    private Service home;
    private Service location;
    public StudentAbstract(String name, Service home, String country) {
        this.name = name;
        this.home = home;
        this.location = home;
        this.country = country;
    }
    @Override
    public String getCountry() {
        return this.country;
    }
    @Override
    public Service getHome() {
        return home;
    }

    @Override
    public void moveHome(Service home) {
        this.home = home;
    }

    @Override
    public void updatePosition(Service position) {
        this.location = position;
    }

    @Override
    public Service findBestService(ServiceType type, Iterator<Service> services) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Service getLocation() {
        return this.location;
    }
}
