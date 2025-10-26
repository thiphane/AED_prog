import campus_app.app.Bounds;
import campus_app.app.CampusApp;
import campus_app.app.CampusAppClass;
import campus_app.entity.service.Service;
import campus_app.entity.student.Student;
import campus_app.entity.service.ServiceType;
import campus_app.exceptions.*;
import dataStructures.Iterator;
import dataStructures.exceptions.NoSuchElementException;
import user.Command;

import java.util.Scanner;

public class Main {
    public static final String HELP_FORMAT = "%s - %s\n";
    public static final String BOUND_CREATED_FORMAT = "%s created.\n";
    public static final String SAVE_FORMAT = "%s saved.\n";
    public static final String STUDENT_LIST_FORMAT = "%s: %s at %s\n";
    public static final String EXIT_MESSAGE = "Bye!";
    // Error Messages
    public static final String BOUNDS_NOT_DEFINED = "System bounds not defined.";
    public static final String NO_SERVICES = "No services yet!";
    public static final String INVALID_SERVICE_TYPE = "Invalid service type!";
    public static final String OUTSIDE_BOUNDS = "Invalid location!";
    public static final String INVALID_PRICE_EATING = "Invalid menu price!";
    public static final String INVALID_PRICE_LODGING = "Invalid room price!";
    public static final String INVALID_PRICE_LEISURE = "Invalid ticket price!";
    public static final String INVALID_VALUE_LEISURE = "Invalid discount price!";
    public static final String INVALID_VALUE_CAPACITY = "Invalid capacity!";
    public static final String SERVICE_ALREADY_EXISTS_FORMAT = "%s already exists!\n";
    public static final String SERVICE_LIST_FORMAT = "%s: %s (%d, %d).\n";
    public static final String SERVICE_FORMAT = "%s %s added.\n";
    public static final String RANKED_HEADER = "%s services closer with %s average\n";
    public static final String STUDENT_DOES_NOT_EXIST = "%s does not exist!\n";
    public static final String NO_SERVICES_OF_GIVEN_TYPE = "No %s services!\n";
    public static final String NO_SUCH_SERVICE_WITH_AVERAGE = "No %s services with average!\n";
    public static final String BOUND_NAME_EXISTS = "Bounds already exists. Please load it.";
    public static final String STUDENT_FORMAT = "%s added.\n";
    public static final String LODGING_DOES_NOT_EXIST = "Lodging %s does not exist.\n";
    public static final String SERVICE_IS_FULL = "%s %s is full!";
    public static final String INVALID_BOUND = "Invalid bounds.";
    public static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";
    private static final String BOUND_LOADED_FORMAT = "%s loaded.\n";
    private static final String ALL_STUDENTS = "all";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Command command;
        CampusApp app = new CampusAppClass();
        do {
            String cmd = in.next();
            command = Command.getCommand(cmd);
            switch(command) {
                case Command.HELP -> {
                    in.nextLine();
                    for(Command c : Command.values()) {
                        if(!c.getDescription().isEmpty()) {
                            System.out.printf(HELP_FORMAT, c.name().toLowerCase(), c.getDescription());
                        }
                    }
                }
                case Command.BOUNDS -> {
                    long topLeftLat = in.nextLong();
                    long topLeftLon = in.nextLong();
                    long botRightLat = in.nextLong();
                    long botRightLon = in.nextLong();
                    String name = in.nextLine().trim();
                    try {
                        app.createBounds(name, topLeftLat, topLeftLon, botRightLat, botRightLon);
                        System.out.printf(BOUND_CREATED_FORMAT, name);
                    } catch (BoundNameExists e) {
                        System.out.println(BOUND_NAME_EXISTS);
                    } catch (InvalidBoundPoints e) {
                        System.out.println(INVALID_BOUND);
                    }
                }
                case Command.SERVICES -> {
                    in.nextLine();

                    Iterator<Service> services = app.listAllServices();
                    if(!services.hasNext()) {
                        System.out.println(NO_SERVICES);
                    }
                    while(services.hasNext()) {
                        Service s = services.next();
                        System.out.printf(SERVICE_LIST_FORMAT, s.getName(), s.getType().toString().toLowerCase(), s.getPosition().latitude(), s.getPosition().longitude());
                    }
                }
                case Command.SERVICE -> {
                    String type = in.next();
                    long latitude = in.nextLong();
                    long longitude = in.nextLong();
                    int price = in.nextInt();
                    int value = in.nextInt();
                    String name = in.nextLine().trim();
                    try {
                        app.createService(type, name, latitude, longitude, price, value);
                        System.out.printf(SERVICE_FORMAT, type.toLowerCase().trim(), name);
                    } catch (InvalidValueException e) {
                        switch(e.getType()) {
                            case EATING, LODGING -> System.out.println(INVALID_VALUE_CAPACITY);
                            case LEISURE -> System.out.println(INVALID_VALUE_LEISURE);
                        }
                    } catch (InvalidPriceException e) {
                        switch(e.getType()) {
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
                        System.out.printf(SERVICE_ALREADY_EXISTS_FORMAT, e.getElement());
                    }
                }
                case Command.SAVE -> {
                    try {
                        String name = app.saveCurrentArea().getName();
                        System.out.printf(SAVE_FORMAT, name);
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    }
                }
                case Command.LOAD -> {
                    try{
                        String name = in.nextLine().trim();
                        Bounds area = app.loadArea(name);
                        System.out.printf(BOUND_LOADED_FORMAT, area.getName());
                    } catch (BoundsNotDefined e) {
                        System.out.println(INVALID_BOUND);
                    }
                }
                case Command.STUDENTS -> {
                    String country = in.nextLine().trim();
                    Iterator<Student> students;
                    if (country.equalsIgnoreCase(ALL_STUDENTS)) {
                        students = app.listAllStudents();
                    } else {
                        students = app.listStudentsByCountry(country);
                    }
                    while(students.hasNext()) {
                        Student cur = students.next();
                        System.out.printf(STUDENT_LIST_FORMAT, cur.getName(), cur.getType().toString().toLowerCase(), cur.getLocation().getName());
                    }
                }
                case Command.STUDENT -> {
                    String type = in.next();
                    String name = in.nextLine().trim();
                    String country = in.nextLine().trim();
                    String lodging = in.nextLine().trim();
                    try {
                        app.createStudent(type, name, country, lodging);
                        System.out.printf(STUDENT_FORMAT, name);
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (InvalidTypeException e) {
                        System.out.println(INVALID_SERVICE_TYPE);
                    }
                    catch (NoSuchElementException e) {
                        System.out.printf(LODGING_DOES_NOT_EXIST, lodging);
                    }
                    catch (ServiceIsFullException e) {
                        System.out.printf(SERVICE_IS_FULL, ServiceType.LODGING.toString().toLowerCase(), lodging);
                    } catch (AlreadyExistsException e) {
                        System.out.printf(SERVICE_ALREADY_EXISTS_FORMAT, name);
                    }
                }case Command.RANKED -> {
                    String type = in.next();int rate = in.nextInt();String name = in.nextLine().trim();
                    try {
                        Iterator<Service> it = app.listClosestServicesByStudent(rate, type, name);
                        System.out.println(RANKED_HEADER);
                        while(it.hasNext()) {
                            Service s = it.next();
                            System.out.println(s.getName());
                        }
                    } catch (BoundsNotDefined e) {
                        System.out.println(BOUNDS_NOT_DEFINED);
                    } catch (NoSuchElementException e) {
                        System.out.printf(STUDENT_DOES_NOT_EXIST, name);
                    }catch (InvalidTypeException e) {
                         System.out.println(INVALID_SERVICE_TYPE);
                    } catch (NoSuchElementOfGivenType e) {
                         System.out.printf(NO_SERVICES_OF_GIVEN_TYPE, type);
                    }catch (NoSuchServiceWithGivenRate e) {
                        System.out.printf(NO_SUCH_SERVICE_WITH_AVERAGE, type);
                    }
                }
                case Command.UNKNOWN -> System.out.println(UNKNOWN_COMMAND);
                case Command.EXIT -> System.out.println(EXIT_MESSAGE);
            }
        } while(command != Command.EXIT);
    }
}
