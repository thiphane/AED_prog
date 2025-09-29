package domain.app;
import dataStructures.*;
import domain.entity.Service;
import domain.entity.student.Student;

public class CampusAppClass implements CampusApp {
    protected ListInArray<Student> students;
    protected ListInArray<Service> services;
    public CampusAppClass() {

    }

    @Override
    public void bounds(String name, long topLongitude, long topLatitude, long bottomLatitude, long bottomLongitude) {

    }

    @Override
    public void save() {

    }

    @Override
    public void load(String areaName) {

    }

    @Override
    public void createService(String serviceName, long latitude, long longitude, int price, int value) {

    }

    @Override
    public void createStudent(StudentType type, String name, String Lodging, String country) {

    }

    @Override
    public Student getStudent(String student) {
        return null;
    }

    @Override
    public Service getService(String serviceName) {
        return null;
    }

    @Override
    public void removeStudent(String studentName) {

    }

    @Override
    public boolean updateStudentPosition(String studentName, String service) {
        return false;
    }

    @Override
    public void moveHome(String studentName, String newHome) {

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
        return null;
    }

    @Override
    public Iterator<Student> listStudentsByCountry(String country) {
        return null;
    }

    @Override
    public Iterator<Service> listAllServices() {
        return null;
    }

    @Override
    public Iterator<Service> listVisitedServices(String studentName) {
        return null;
    }

    @Override
    public Iterator<Service> listServicesByRanking() {
        return null;
    }

    @Override
    public Iterator<Service> listClosestServicesByStudent(int rate, ServiceType type, String studentName) {
        return null;
    }

    @Override
    public Iterator<Service> listServicesByTag(String tagName) {
        return null;
    }

    @Override
    public Service findBestService(String studentName, ServiceType type) {
        return null;
    }
}
