package robotService.entities.services;

import robotService.entities.robot.Robot;
import robotService.entities.supplements.Supplement;

import java.util.ArrayList;
import java.util.Collection;

import static robotService.common.ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_ROBOT;
import static robotService.common.ExceptionMessages.SERVICE_NAME_CANNOT_BE_NULL_OR_EMPTY;

public abstract class BaseService implements Service {
    private String name;
    private final int capacity;
    private final Collection<Supplement> supplements;
    private final Collection<Robot> robots;
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new NullPointerException(SERVICE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<Robot> getRobots() {
        return this.robots;
    }

    @Override
    public Collection<Supplement> getSupplements() {
        return this.supplements;
    }

    @Override
    public void addRobot(Robot robot) {
        if(this.capacity <= this.robots.size()){
            throw new IllegalArgumentException(NOT_ENOUGH_CAPACITY_FOR_ROBOT);
        }
        robots.add(robot);
    }

    @Override
    public void removeRobot(Robot robot) {
        robots.remove(robot);
    }

    @Override
    public void addSupplement(Supplement supplement) {
        supplements.add(supplement);
    }

    @Override
    public void feeding() {
        for (Robot robot: this.robots) {
            robot.eating();
        }
    }

    @Override
    public int sumHardness() {
        int sum = 0;
        for (Supplement supplement: this.supplements) {
            sum += supplement.getHardness();
        }
        return sum;
    }

    public BaseService(String name, int capacity) {
        setName(name);
        this.capacity = capacity;
        robots = new ArrayList<>();
        supplements = new ArrayList<>();
    }

    @Override
    public String getStatistics() {
        StringBuilder robotsStr = new StringBuilder();
        if (robots.size() == 0) {
            robotsStr.append("none");
        }
        for (Robot robot: robots) {
            robotsStr.append(robot.getName()).append(" ");
        }
        return String.format("%s %s:\nRobots: %s\nSupplements: %d Hardness: %d", this.name, this.getClass().getSimpleName(), robotsStr.toString().trim(), supplements.size(), this.sumHardness());
    }
}
