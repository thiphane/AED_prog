/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
import campus_app.app.Bounds;
import campus_app.app.CampusApp;
import campus_app.app.CampusAppClass;
import campus_app.app.Order;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;
import campus_app.entity.service.ServiceType;
import campus_app.exceptions.*;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
import user.Command;

import java.util.Scanner;

public class Main {
    private static final String ALL_STUDENTS = "all";

    public static final String HELP_FORMAT = "%s - %s\n";
    public static final String BOUND_CREATED_FORMAT = "%s created.\n";
    public static final String SAVE_FORMAT = "%s saved.\n";
    public static final String STUDENT_LIST_FORMAT = "%s: %s at %s.\n";
    public static final String EXIT_MESSAGE = "Bye!";
    // Error Messages
    public static final String BOUNDS_NOT_DEFINED = "System bounds not defined.";
    public static final String NO_SERVICES = "No services yet!";
    public static final String INVALID_SERVICE_TYPE = "Invalid service type!";
    public static final String INVALID_STUDENT_TYPE = "Invalid student type!";
    public static final String OUTSIDE_BOUNDS = "Invalid location!";
    public static final String INVALID_PRICE_EATING = "Invalid menu price!";
    public static final String INVALID_PRICE_LODGING = "Invalid room price!";
    public static final String INVALID_PRICE_LEISURE = "Invalid ticket price!";
    public static final String INVALID_VALUE_LEISURE = "Invalid discount price!";
    public static final String INVALID_VALUE_CAPACITY = "Invalid capacity!";
    public static final String ENTITY_ALREADY_EXISTS_FORMAT = "%s already exists!\n";
    public static final String SERVICE_LIST_FORMAT = "%s: %s (%d, %d).\n";
    public static final String SERVICE_FORMAT = "%s %s added.\n";
    public static final String RANKED_HEADER = "%s services closer with %d average\n";
    public static final String ELEMENT_DOES_NOT_EXIST = "%s does not exist!\n";
    public static final String UNKNOWN_ELEMENT = "Unknown %s!\n";
    public static final String NO_SERVICES_OF_GIVEN_TYPE = "No %s services!\n";
    public static final String NO_SUCH_SERVICE_WITH_AVERAGE = "No %s services with average!\n";
    public static final String BOUND_NAME_EXISTS = "Bounds already exists. Please load it!";
    public static final String STUDENT_FORMAT = "%s added.\n";
    public static final String LODGING_DOES_NOT_EXIST = "lodging %s does not exist!\n";
    public static final String SERVICE_IS_FULL = "%s %s is full!\n";
    public static final String SERVICE_IS_NOT_VALID = "%s is not a valid service!\n";
    public static final String INVALID_BOUND = "Invalid bounds.";
    public static final String ALREADY_THERE = "Already there!";
    public static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";
    private static final String BOUND_LOADED_FORMAT = "%s loaded.\n";
    private static final String THIS_ORDER_DOES_NOT_EXISTS = "This order does not exists!";
    private static final String SERVICE_CANT_CONTROL_USERS = "%s does not control student entry and exit!\n";
    private static final String STUDENT_IS_DISTRACTED = "%1$s is now at %2$s. %1$s is distracted!\n";
    private static final String LOCATION_CHANGED_FORMAT = "%s is now at %s.\n";
    private static final String MOVE_NOT_ACCEPTABLE = "Move is not acceptable for %s!\n";
    private static final String STUDENT_HAS_NOT_VISITED_ANY_LOCATION = "%s has not visited any locations!\n";
    private static final String STUDENT_IS_THRIFTY = "%s is thrifty!\n";
    private static final String STUDENT_HAS_LEFT = "%s has left.\n";
    private static final String RANKING_HEADER = "Services sorted in descending order";
    private static final String RANKING_FORMAT ="%s: %d\n";
    private static final String EMPTY_RANKING = "No services in the system.";
    private static final String TAG_FORMAT = "%s %s\n";
    private static final String NO_SUCH_TAG = "There are no services with this tag!";
    private static final String RATING_SUCCESS_FORMAT = "Your evaluation has been registered!";
    private static final String INVALID_EVALUATION = "Invalid evaluation!";
    private static final String INVALID_STARS = "Invalid stars!";

    private static final String STUDENT_LOCATION_FORMAT = "%s is at %s %s %s.\n";
    private static final String NO_STUDENTS = "No students yet!";
    private static final String NO_STUDENTS_COUNTRY = "No students from %s!\n";
    private static final String USER_FORMAT = "%s: %s\n";
    private static final String SAME_HOME_FORMAT = "That is %s's home!\n";
    private static final String STUDENT_MOVED_FORMAT = "lodging %1$s is now %2$s's home. %2$s is at home.\n";
    private static final String NO_STUDENTS_IN = "No students on %s!\n";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Command command;
        CampusApp app = new CampusAppClass();
        do {
            String cmd = in.next();
            command = Command.getCommand(cmd);
            switch (command) {
                case HELP -> {
                    in.nextLine();
                    for (Command c : Command.values()) {
                        if (!c.getDescription().isEmpty()) {
                            System.out.printf(HELP_FORMAT, c.name().toLowerCase(), c.getDescription());
                        }
                    }
                }
                case BOUNDS -> { // O(n)
                    long topLeftLat = in.nextLong();
                    long topLeftLon = in.nextLong();
                    long bottomRightLat = in.nextLong();
                    long bottomRightLong = in.nextLong();
                    String name = in.nextLine().trim();
                    try {
                        app.createBounds(name, topLeftLat, topLeftLon, bottomRightLat, bottomRightLong);
                        System.out.printf(BOUND_CREATED_FORMAT, name);
                    } catch (BoundNameExists e) {
                        System.out.println(BOUND_NAME_EXISTS);
                    } catch (InvalidBoundPoints e) {
                        System.out.println(INVALID_BOUND);
                    }
                }
                case SERVICES -> { // O(n)
                    in.nextLine();

                    try{
                        Iterator<Service> services = app.listAllServices(); // O(1)
                        if (!services.hasNext()) {
                            System.out.println(NO_SERVICES);
                        }
                        while (services.hasNext()) { // O(n)
                            Service s = services.next();
                            System.out.printf(SERVICE_LIST_FORMAT, s.getName(), s.getType().toString().toLowerCase(), s.getPosition().latitude(), s.getPosition().longitude());
                        }
                    }catch(BoundsNotDefined e){
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }
                }
                case SERVICE -> { // O(n)
                    String type = in.next();
                    long latitude = in.nextLong();
                    long longitude = in.nextLong();
                    int price = in.nextInt();
                    int value = in.nextInt();
                    String name = in.nextLine().trim();
                    try {
                        app.createService(type, name, latitude, longitude, price, value); // O(n)
                        System.out.printf(SERVICE_FORMAT, type.toLowerCase().trim(), name);
                    } catch (InvalidValueException e) {
                        switch (e.getType()) {
                            case EATING, LODGING -> System.out.println(INVALID_VALUE_CAPACITY);
                            case LEISURE -> System.out.println(INVALID_VALUE_LEISURE);
                        }
                    } catch (InvalidPriceException e) {
                        switch (e.getType()) {
                            case EATING -> System.out.println(INVALID_PRICE_EATING);
                            case LEISURE -> System.out.println(INVALID_PRICE_LEISURE);
                            case LODGING -> System.out.println(INVALID_PRICE_LODGING);
                        }
                    } catch (OutsideBoundsException e) {
                        System.out.println(OUTSIDE_BOUNDS);
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (InvalidTypeException e) {
                        System.out.println(INVALID_SERVICE_TYPE);
                    } catch (AlreadyExistsException e) {
                        System.out.printf(ENTITY_ALREADY_EXISTS_FORMAT, e.getElement());
                    }
                }
                case SAVE -> { // O(n)
                    try {
                        String name = app.saveCurrentArea().getName(); // O(n)
                        System.out.printf(SAVE_FORMAT, name);
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }
                }
                case LOAD -> { // O(n)
                    try {
                        String name = in.nextLine().trim();
                        Bounds area = app.loadArea(name); // O(n)
                        System.out.printf(BOUND_LOADED_FORMAT, area.getName());
                    } catch (BoundsNotDefined e) {
                        System.out.println(INVALID_BOUND);
                    }
                }
                case STUDENTS -> { // O(n)
                    String country = in.nextLine().trim();
                    Iterator<Student> students;
                    try {
                        if (country.equalsIgnoreCase(ALL_STUDENTS)) {
                            students = app.listAllStudents(); // O(1)
                            if (!students.hasNext()) {
                                System.out.println(NO_STUDENTS);
                            }
                        } else {
                            students = app.listStudentsByCountry(country); // O(1)
                            if (!students.hasNext()) {
                                System.out.printf(NO_STUDENTS_COUNTRY, country);
                            }
                        }
                        while (students.hasNext()) { // O(n)
                            Student cur = students.next();
                            System.out.printf(STUDENT_LIST_FORMAT, cur.getName(), cur.getType().toString().toLowerCase(), cur.getLocation().getName());
                        }
                    }catch(BoundsNotDefined e){
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }
                }
                case STUDENT -> { // O(n)
                    String type = in.next();
                    in.nextLine();
                    String name = in.nextLine().trim();
                    String country = in.nextLine().trim();
                    String lodging = in.nextLine().trim();
                    try {
                        app.createStudent(type, name, lodging, country); // O(n)
                        System.out.printf(STUDENT_FORMAT, name);
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (InvalidTypeException e) {
                        System.out.println(INVALID_STUDENT_TYPE);
                    } catch (NoSuchElementOfGivenType e) {
                        System.out.printf(LODGING_DOES_NOT_EXIST, lodging);
                    } catch (ServiceIsFullException e) {
                        System.out.printf(SERVICE_IS_FULL, ServiceType.LODGING.toString().toLowerCase(), lodging);
                    } catch (AlreadyExistsException e) {
                        System.out.printf(ENTITY_ALREADY_EXISTS_FORMAT, e.getElement());
                    }
                }
                case WHERE -> { // O(n)
                    String name = in.nextLine().trim();
                    try {
                        Student student = app.getStudent(name); // O(n)
                        Service location = student.getLocation(); // O(1)
                        System.out.printf(STUDENT_LOCATION_FORMAT, student.getName(), location.getName(), location.getType().toString().toLowerCase(), location.getPosition());
                        }
                    catch (BoundsNotDefined e){
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }catch(StudentDoesNotExistException e){
                        System.out.printf(ELEMENT_DOES_NOT_EXIST, name);
                    }

                }
                case RANKED -> { // O(n)
                    String type = in.next().toLowerCase();
                    int rate = in.nextInt();
                    String name = in.nextLine().trim();
                    try {
                        Iterator<Service> it = app.listClosestServicesByStudent(rate, type, name); // O(n)
                        System.out.printf(RANKED_HEADER, type, rate);
                        while (it.hasNext()) { // O(n)
                            Service s = it.next();
                            System.out.println(s.getName());
                        }
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (StudentDoesNotExistException e) {
                        System.out.printf(ELEMENT_DOES_NOT_EXIST, name);
                    }catch (InvalidTypeException e) {
                        System.out.println(INVALID_SERVICE_TYPE);
                    } catch (NoSuchElementOfGivenType e) {
                        System.out.printf(NO_SERVICES_OF_GIVEN_TYPE, type);
                    } catch (NoSuchServiceWithGivenRate e) {
                        System.out.printf(NO_SUCH_SERVICE_WITH_AVERAGE, type);
                    } catch (InvalidRating e) {
                        System.out.println(INVALID_STARS);
                    }
                }
                case USERS -> { // O(n)
                    String order = in.next();
                    String serviceName = in.nextLine().trim();
                    Order actualOrder = null;
                    if (order.equals("<")) actualOrder = Order.NEW_TO_OLD;
                    if (order.equals(">")) actualOrder = Order.OLD_TO_NEW;
                    try {
                        TwoWayIterator<Student> it = app.getUsersByService(serviceName, actualOrder); // O(n)
                        assert actualOrder != null;
                        if (actualOrder.equals(Order.OLD_TO_NEW)) {
                            while (it.hasNext()) { // O(n)
                                Student s = it.next();
                                System.out.printf(USER_FORMAT, s.getName(), s.getType().toString().toLowerCase());
                            }
                        } else {
                            while (it.hasPrevious()) { // O(n)
                                Student s = it.previous();
                                System.out.printf(USER_FORMAT, s.getName(), s.getType().toString().toLowerCase());
                            }
                        }
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (InvalidOrderException e) {
                        System.out.println(THIS_ORDER_DOES_NOT_EXISTS);
                    } catch (ServiceDoesNotExistException e) {
                        System.out.printf(ELEMENT_DOES_NOT_EXIST, serviceName);
                    } catch (CantShowUsersException e) {
                        System.out.printf(SERVICE_CANT_CONTROL_USERS, e.getService().getName());
                    } catch (NoStudentsException e) {
                        System.out.printf(NO_STUDENTS_IN, e.getService().getName());
                    }
                }
                case GO -> { // O(n)
                    String studentName = in.nextLine().trim();
                    String locationName = in.nextLine().trim();
                    boolean isDistracted;
                    try {
                        Student student = app.getStudent(studentName); // O(n)
                        Service home = app.getService(locationName); // O(n)
                        isDistracted = app.updateStudentPosition(student, home); // O(1) best case, O(n) worst case, expected O(n)
                        if(isDistracted){System.out.printf(STUDENT_IS_DISTRACTED, student.getName(), home.getName());}
                        else System.out.printf(LOCATION_CHANGED_FORMAT, student.getName(), home.getName());
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (ServiceDoesNotExistException e) {
                        System.out.printf(UNKNOWN_ELEMENT, locationName);
                    }catch (StudentDoesNotExistException e) {
                        System.out.printf(ELEMENT_DOES_NOT_EXIST,studentName);
                    }catch(InvalidTypeException e){
                        System.out.printf(SERVICE_IS_NOT_VALID, locationName);
                    }catch (StudentAlreadyThereException e){
                        System.out.println(ALREADY_THERE);
                    }catch(ServiceIsFullException e){
                        System.out.printf(SERVICE_IS_FULL, ServiceType.EATING.name().toLowerCase(), e.getService().getName());
                    }
                }

                case MOVE -> { // O(n)
                    String name = in.nextLine().trim();
                    String lodging = in.nextLine().trim();
                    try {
                        app.moveHome(name, lodging); // O(n)
                        // TODO arranjar forma de não usar getService e getUser e devolver com moveHome ou algo assim
                        Student student = app.getStudent(name); // O(n)
                        Service home = app.getService(lodging); // O(n)
                        System.out.printf(STUDENT_MOVED_FORMAT, home.getName(), student.getName());
                    } catch(BoundsNotDefined e){
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (ServiceDoesNotExistException e) {
                        System.out.printf(LODGING_DOES_NOT_EXIST, lodging);
                    } catch (StudentDoesNotExistException e) {
                        System.out.printf(ELEMENT_DOES_NOT_EXIST, name);
                    }catch (ServiceIsFullException e) {
                        System.out.printf(SERVICE_IS_FULL, ServiceType.LODGING.toString().toLowerCase(), e.getService().getName());
                    } catch (SameHomeException e) {
                        System.out.printf(SAME_HOME_FORMAT, e.getStudent().getName());
                    }catch (MoveNotAcceptable e) {
                        System.out.printf(MOVE_NOT_ACCEPTABLE, e.getStudent().getName());
                    }
                }case VISITED -> { // O(n)
                    String name = in.nextLine().trim();
                    try{
                        Student student = app.getStudent(name);
                        Iterator<Service> it = app.listVisitedServices(student); // O(1)
                            while(it.hasNext()){ // O(n) time
                                System.out.println(it.next().getName());
                            }
                    }catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }  catch (StudentDoesNotExistException e) {
                        System.out.printf(ELEMENT_DOES_NOT_EXIST, name);
                    } catch (StudentDoesntStoreVisitedServicesException e) {
                        System.out.printf(STUDENT_IS_THRIFTY, e.getStudent().getName());
                    } catch (NoVisitedServicesException e) {
                        System.out.printf(STUDENT_HAS_NOT_VISITED_ANY_LOCATION, e.getStudent().getName());
                    }
                }case LEAVE -> { // O(n^2)
                    String name = in.nextLine().trim();
                    try{
                        Student student = app.removeStudent(name); // O(n^2)
                        System.out.printf(STUDENT_HAS_LEFT, student.getName());
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }catch (StudentDoesNotExistException e) {
                        System.out.printf(ELEMENT_DOES_NOT_EXIST, name);
                    }
                }case RANKING -> { // O(n)
                    try {
                        Iterator<Service> iter = app.listServicesByRanking(); // O(1)
                        if(iter.hasNext()) System.out.println(RANKING_HEADER);
                        else System.out.println(EMPTY_RANKING);
                        while(iter.hasNext()){ // O(n)
                            Service ser =  iter.next();
                            System.out.printf(RANKING_FORMAT, ser.getName(), ser.getRating());
                        }
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }
                }case TAG -> { // O(n^2)
                    String tagName = in.nextLine().trim();
                    try {
                        Iterator<Service> iter = app.listServicesByTag(tagName); // O(1)
                        if(iter.hasNext()){
                            while(iter.hasNext()) { // traversing the iterator is O(n^2)
                                Service ser = iter.next();
                                System.out.printf(TAG_FORMAT, ser.getType().name().toLowerCase(), ser.getName());
                            }
                        }else System.out.println(NO_SUCH_TAG);
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }
                }case FIND ->{ // O(1) best case, O(n) worst case
                        String studentName = in.nextLine().trim();
                        String type = in.nextLine().trim();
                        try {
                            Service bestServices = app.findBestService(studentName, type); // O(1) best case, O(n) worst case
                            if(bestServices == null)System.out.printf(NO_SERVICES_OF_GIVEN_TYPE, type.toLowerCase());
                            else System.out.println(bestServices.getName());
                        } catch (BoundsNotDefined e) {
                            System.out.println(BOUNDS_NOT_DEFINED);
                        } catch (InvalidTypeException e) {
                            System.out.println(INVALID_SERVICE_TYPE);
                        }catch (StudentDoesNotExistException e) {
                            System.out.printf(ELEMENT_DOES_NOT_EXIST, studentName);
                        }
                }case STAR ->{ // O(n)
                    int rating = in.nextInt();String serviceName = in.nextLine().trim();
                    String description =  in.nextLine().trim();
                    try{
                        app.rateService(rating, serviceName, description); // O(n)
                        System.out.println(RATING_SUCCESS_FORMAT);
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (InvalidRating e) {
                        System.out.println(INVALID_EVALUATION);
                    }catch(ServiceDoesNotExistException e){
                        System.out.printf(ELEMENT_DOES_NOT_EXIST, serviceName);
                    }
                }
                case UNKNOWN -> System.out.println(UNKNOWN_COMMAND);
                case EXIT -> { // O(n)
                    try {
                        app.saveCurrentArea(); // O(n)
                    } catch (BoundsNotDefined ignored) {}
                    System.out.println(EXIT_MESSAGE);
                }
            }
        } while(command != Command.EXIT);
    }
}
