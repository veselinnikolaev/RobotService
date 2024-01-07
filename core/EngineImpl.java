package robotService.core;

import robotService.common.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EngineImpl implements Engine {
    private final Controller controller;
    private final BufferedReader reader;

    public EngineImpl() {
        this.controller = new ControllerImpl();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            String result;
            try {
                result = processInput();

                if (result.equals("End")) {
                    break;
                }
            } catch (NullPointerException | IllegalArgumentException | IllegalStateException | IOException e) {
                result = e.getMessage();
            }

            System.out.println(result);
        }
    }

    private String processInput() throws IOException {
        String input = this.reader.readLine();
        String[] tokens = input.split("\\s+");

        Command command = Command.valueOf(tokens[0]);
        String result;
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);

        result = switch (command) {
            case AddService -> addService(data);
            case AddSupplement -> addSupplement(data);
            case SupplementForService -> supplementForService(data);
            case AddRobot -> addRobot(data);
            case FeedingRobot -> feedingRobot(data);
            case SumOfAll -> sumOfAll(data);
            case Statistics -> getStatistics();
            case End -> Command.End.name();
        };
        return result;
    }

    private String addService(String[] data) {
        return controller.addService(data[0], data[1]);
    }
    private String addSupplement(String[] data) {
        return controller.addSupplement(data[0]);
    }

    private String supplementForService(String[] data) {
        return controller.supplementForService(data[0], data[1]);
    }

    private String addRobot(String[] data) {
        return controller.addRobot(data[0], data[1], data[2], data[3], Double.parseDouble(data[4]));
    }

    private String feedingRobot(String[] data){
        return controller.feedingRobot(data[0]);
    }

    private String sumOfAll(String[] data) {
        return controller.sumOfAll(data[0]);
    }

    private String getStatistics() {
        return this.controller.getStatistics();
    }
}