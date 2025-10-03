package domain.app;
import dataStructures.*;
import domain.entity.service.Service;
import domain.entity.service.ServiceType;
import domain.entity.student.Student;
import domain.entity.student.StudentType;

public class CampusAppClass implements CampusApp {
    StudentStorage students;
    ServiceStorage services;
    public CampusAppClass() {
        this.students = new StudentStorage();
        this.services = new ServiceStorage();
    }

    @Override
    public void createBounds(String name, long topLongitude, long topLatitude, long bottomLatitude, long bottomLongitude) {

    }

    @Override
    public void saveCurrentArea() {

    }

    @Override
    public void loadArea(String areaName) {

    }

    @Override
    public void createService(ServiceType type, String serviceName, long latitude, long longitude, int price, int value) {

    }

    @Override
    public void createStudent(StudentType type, String name, String lodging, String country) {

    }

    @Override
    public Student getStudent(String student) {
        return students.getStudent(student);
    }

    @Override
    public Service getService(String serviceName) {
        return null;
    }

    @Override
    public Student removeStudent(String studentName) {
        return students.removeStudent(studentName);
    }

    @Override
    public boolean updateStudentPosition(String studentName, String service) {
        Service newLocation = this.getService(service);

        return students.updateStudentLocation(studentName, newLocation);
    }

    @Override
    public void moveHome(String studentName, String newHome) {
        Service newHomeService = this.getService(newHome);

        students.moveHome(studentName, newHomeService);
    }

    @Override
    public void rateService(int rate, String serviceName, String description) {

    }

    @Override
    public TwoWayIterator<Student> getUsersByService(String serviceName) {
        return null;
    }

    @Override
    public Iterator<Student> listAllStudents() {
        return students.getAllStudents();
    }

    @Override
    public Iterator<Student> listStudentsByCountry(String country) {
        return students.getStudentsByCountry(country);
    }

    @Override
    public Iterator<Service> listAllServices() {
        return null;
    }

    @Override
    public Iterator<Service> listVisitedServices(String studentName) {
        return students.listVisitedServices(studentName);
    }

    @Override
    public Iterator<Service> listServicesByRanking() {
        return null;
    }

    @Override
    public Iterator<Service> listClosestServicesByStudent(int rate, ServiceType type, String studentName) {
        // TODO add to student storage
        return null;
    }

    @Override
    public Iterator<Service> listServicesByTag(String tagName) {
        return new FilterIterator<>(services.listAllServices(), new ServiceTagPredicate(tagName));
    }

    @Override
    public Service findBestService(String studentName, ServiceType type) {
        return students.findBestService(studentName, new FilterIterator<>(services.listAllServices(), new ServiceTypePredicate(type)));
    }
}
