package domain;

import dataStructures.Iterator;

public abstract class StudentAbstract implements Student {
    private String name;
    private Service home;
    private Service location;
    public StudentAbstract(String name, Service home, Service location) {
        this.name = name;
        this.home = home;
        this.location = location;
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

    }

    @Override
    public Service findBestService(ServiceType type, Iterator<Service> services) {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Service getLocation() {
        return null;
    }
}
