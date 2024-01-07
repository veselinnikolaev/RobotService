package robotService.core;

import robotService.entities.robot.FemaleRobot;
import robotService.entities.robot.MaleRobot;
import robotService.entities.robot.Robot;
import robotService.entities.services.MainService;
import robotService.entities.services.SecondaryService;
import robotService.entities.services.Service;
import robotService.entities.supplements.MetalArmor;
import robotService.entities.supplements.PlasticArmor;
import robotService.entities.supplements.Supplement;
import robotService.repositories.SupplementRepository;

import java.util.ArrayList;
import java.util.Collection;

import static robotService.common.ConstantMessages.*;
import static robotService.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private final SupplementRepository supplements;
    private final Collection<Service> services;

    @Override
    public String addService(String type, String name) {
        Service service;
        if (type.equals("MainService")) {
            service = new MainService(name);
        } else if (type.equals("SecondaryService")) {
            service = new SecondaryService(name);
        } else {
            throw new NullPointerException(INVALID_SERVICE_TYPE);
        }
        services.add(service);
        return String.format(SUCCESSFULLY_ADDED_SERVICE_TYPE, type);

    }

    @Override
    public String addSupplement(String type) {
        Supplement supplement;
        if (type.equals("PlasticArmor")) {
            supplement = new PlasticArmor();
        } else if (type.equals("MetalArmor")) {
            supplement = new MetalArmor();
        } else {
            throw new IllegalArgumentException(INVALID_SUPPLEMENT_TYPE);
        }
        supplements.addSupplement(supplement);
        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForService(String serviceName, String supplementType) {
        Supplement supplement = supplements.findFirst(supplementType);
        Service desiredService = services.stream().filter(service -> service.getName().equals(serviceName)).findFirst().orElse(null);
        if (supplement == null) {
            throw new IllegalArgumentException(String.format(NO_SUPPLEMENT_FOUND, supplementType));
        }
        desiredService.addSupplement(supplement);
        supplements.removeSupplement(supplement);
        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_IN_SERVICE, supplementType, serviceName);

    }

    @Override
    public String addRobot(String serviceName, String robotType, String robotName, String robotKind, double price) {
        Service desiredService = services.stream().filter(service -> service.getName().equals(serviceName)).findFirst().orElse(null);
        Robot robot;
        if (robotType.equals("MaleRobot")) {
            robot = new MaleRobot(robotName, robotKind, price);
            if (desiredService.getClass().getSimpleName().equals("SecondaryService")) {
                return UNSUITABLE_SERVICE;
            }

        } else if (robotType.equals("FemaleRobot")) {
            robot = new FemaleRobot(robotName, robotKind, price);
            if (desiredService.getClass().getSimpleName().equals("MainService")) {
                return UNSUITABLE_SERVICE;
            }

        } else {
            throw new IllegalArgumentException(INVALID_ROBOT_TYPE);
        }
        desiredService.addRobot(robot);
        return String.format(SUCCESSFULLY_ADDED_ROBOT_IN_SERVICE, robotType, serviceName);
    }

    @Override
    public String feedingRobot(String serviceName) {
        Service desiredService = services.stream().filter(service -> service.getName().equals(serviceName)).findFirst().orElse(null);
        for (Robot robot : desiredService.getRobots()) {
            robot.eating();
        }
        return String.format(FEEDING_ROBOT, desiredService.getRobots().size());
    }

    @Override
    public String sumOfAll(String serviceName) {
        Service desiredService = services.stream().filter(service -> service.getName().equals(serviceName)).findFirst().orElse(null);
        double sum = 0;
        for (Robot robot : desiredService.getRobots()) {
            sum += robot.getPrice();
        }
        for (Supplement supplement : desiredService.getSupplements()) {
            sum += supplement.getPrice();
        }
        return String.format(VALUE_SERVICE, serviceName, sum);
    }

    public ControllerImpl() {
        this.supplements = new SupplementRepository();
        this.services = new ArrayList<>();
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Service service : services) {
            sb.append(service.getStatistics());
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
