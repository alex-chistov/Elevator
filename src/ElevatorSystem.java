import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {
    private final List<Elevator> elevators = new ArrayList<>();

    public ElevatorSystem(int numberOfElevators) {
        for (int i = 0; i < numberOfElevators; i++) {
            Elevator elevator = new Elevator();
            elevators.add(elevator);
            new Thread(elevator).start();
        }
    }

    public void requestElevator(int requestedFloor) {
        Elevator bestElevator = findBestElevator(requestedFloor);
        Request request = new Request(requestedFloor, requestedFloor);
        int elevatorIndex = elevators.indexOf(bestElevator) + 1;
        System.out.println("Requested floor: " + requestedFloor + " assigned to elevator " + elevatorIndex + ".");
    }

    public void resetAll() {
        for (Elevator elevator : elevators) {
            elevator.reset();
        }
    }

    private Elevator findBestElevator(int requestedFloor) {
        Elevator optimalElevator = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int currentFloor = elevator.getCurrentFloor();
            int distance = Math.abs(currentFloor - requestedFloor);

            if (distance < shortestDistance || optimalElevator == null) {
                optimalElevator = elevator;
                shortestDistance = distance;
            }
        }
        return optimalElevator;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}
