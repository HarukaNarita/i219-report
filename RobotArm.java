public class RobotArm {
    private enum State {
        IDLE, MOVING, FINISHED
    }

    private State currentState;
    private int position;
    private int destination;

    public RobotArm() {
        currentState = State.IDLE;
        position = 0;
        destination = 0;
    }

    public void move(int newDestination) {
        destination = newDestination;
        currentState = State.MOVING;
        System.out.println("Moving to destination: " + destination);
        update();
    }

    public void stop() {
        if (currentState == State.MOVING) {
            currentState = State.IDLE;
            System.out.println("Stopped at position: " + position);
        }
    }

    public void resume() {
        if (currentState == State.IDLE && position != destination) {
            currentState = State.MOVING;
            System.out.println("Resuming movement to destination: " + destination);
            update();
        }
    }

    public void update() {
        while (true) {
            switch (currentState) {
                case IDLE:
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case MOVING:
                    System.out.println("Current position: " + position);
                    if (position < destination) {
                        position++;
                    } else if (position > destination) {
                        position--;
                    }
                    
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    if (position == destination) {
                        currentState = State.FINISHED;
                        System.out.println("Reached destination: " + position);
                    }
                    break;
                case FINISHED:
                    sendFinishedSignal();
                    currentState = State.IDLE;
                    return;
            }
        }
    }

    private void sendFinishedSignal() {
        System.out.println("Sending reached signal");
    }

    public State getCurrentState() {
        return currentState;
    }

    public int getPosition() {
        return position;
    }
}