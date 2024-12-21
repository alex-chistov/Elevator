public class Request {
    private final int startFloor;
    private final int destinationFloor;

    public Request(int startFloor, int destinationFloor) {
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getStartFloor() {
        return startFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    @Override
    public String toString() {
        return "Request from floor " + startFloor + " to floor " + destinationFloor;
    }
}
