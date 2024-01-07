package robotService.entities.robot;

import static robotService.common.ExceptionMessages.*;

public abstract class BaseRobot implements Robot {
    private String name;
    private final String kind;
    private int kilograms;
    private final double price;

    public BaseRobot(String name, String kind, int kilograms, double price) {
        setName(name);
        if(kind == null || kind.trim().isEmpty()){
            throw new NullPointerException(ROBOT_KIND_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.kind = kind;
        this.kilograms = kilograms;
        if(price <= 0){
            throw new IllegalArgumentException(ROBOT_PRICE_CANNOT_BE_BELOW_OR_EQUAL_TO_ZERO);
        }
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new NullPointerException(ROBOT_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public int getKilograms() {
        return this.kilograms;
    }

    @Override
    public double getPrice() {
        return this.price;
    }
    @Override
    public void eating() {
        if(this.getClass().getSimpleName().equals("MaleRobot")){
            this.kilograms += 3;
        }else if(this.getClass().getSimpleName().equals("FemaleRobot")){
            this.kilograms += 1;
        }
    }

    public String getKind() {
        return kind;
    }
}
