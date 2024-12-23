import java.util.LinkedList;
import java.util.Queue;

public class Elevator implements Runnable {
    private int currentFloor = 1;
    private final Queue<Request> requestQueue = new LinkedList<>();
    private boolean isMovingUp = true;
    private volatile boolean isResetting = false;

    public synchronized void requestFloor(Request request) {
        if (!isResetting) {
            requestQueue.add(request);
            notify();
        }
    }

    public synchronized void reset() {
        isResetting = true;
        requestQueue.clear();
        currentFloor = 1;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (requestQueue.isEmpty() && !isResetting) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            if (isResetting) {
                returnToFirstFloor();
            } else {
                processNextRequest();
            }
        }
    }

    private void returnToFirstFloor() {
        while (currentFloor != 1) {
            moveToFloor(1);
        }
        synchronized (this) {
            isResetting = false;
            System.out.println("Elevator reset to floor 1");
        }
    }

    private void processNextRequest() {
        Request nextRequest;
        synchronized (this) {
            nextRequest = requestQueue.poll();
        }

        if (nextRequest != null) {
            int startFloor = nextRequest.getStartFloor();
            int destinationFloor = nextRequest.getDestinationFloor();

            moveToFloor(startFloor);

            while (currentFloor != destinationFloor) {
                moveToFloor(destinationFloor);
            }
            System.out.println("Elevator stopped at floor: " + currentFloor);
        }
    }

    private void moveToFloor(int floor) {
        if (currentFloor < floor) {
            currentFloor++;
            isMovingUp = true;
        } else {
            currentFloor--;
            isMovingUp = false;
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized int getCurrentFloor() {
        return currentFloor;
    }

    public synchronized boolean isMovingUpward() {
        return isMovingUp;
    }

    public synchronized int getTargetFloor() {
        return currentFloor;
    }
}
