public class Dispenser {
    private enum State {
        IDLE, WORKING, FINISHED
    }

    private enum WorkingState {
        ASPIRATING, DISPENSING
    }

    private enum plungerDirection{
        UP, DOWN
    }

    private static int pMin = 0;
    private static int pMax = 100;

    private static int getPlungerDelta(int volume){
        return volume/10;
    }


    private State currentState;
    private WorkingState workingState;
    private int pTemp;
    private int pFinal;
    private plungerDirection pDirection;

    public Dispenser() {
        currentState = State.IDLE;
        workingState = null;
        pTemp = 0;
        pFinal = 0;
        pDirection = null;
    }

    public void aspirate(int volume, Container container) {
        if (currentState == State.IDLE) {
            currentState = State.WORKING;
            workingState = WorkingState.ASPIRATING;
            container.setState(Container.ContainerState.BUSY);
            pDirection = plungerDirection.UP;
            pFinal = Math.min(pTemp + getPlungerDelta(volume), pMax);
            System.out.println("Aspirating volume: " + volume + " mL from container: " + container.position);
            update(container);
        }
    }

    public void dispense(int volume, Container container) {
        currentState = State.WORKING;
        workingState = WorkingState.DISPENSING;
        container.setState(Container.ContainerState.BUSY);
        pDirection = plungerDirection.DOWN;
        pFinal = Math.max(pTemp - getPlungerDelta(volume), pMin);
        System.out.println("Dispensing volume: " + volume + " mL to container: " + container.position);
        update(container);
    }

    public void stop() {
        if (currentState == State.WORKING) {
            currentState = State.IDLE;
            workingState = null;
            System.out.println("Stopped at pTemp: " + pTemp);
        }
    }

    public void resume() {
        if (currentState == State.IDLE && pTemp != pFinal) {
            currentState = State.WORKING;
            if (pDirection==plungerDirection.UP){
                workingState = WorkingState.ASPIRATING;
            }
            else{
                workingState = WorkingState.DISPENSING;
            }
            System.out.println("Resuming " + workingState);
        }
    }

    public void update(Container container) {
        while (true) {
            switch (currentState) {
                case IDLE:
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case WORKING:
                    System.out.println("Current plunger position: " + pTemp);
                    if (workingState == WorkingState.ASPIRATING) {
                        pTemp++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (pTemp == pFinal) {
                            finishWorking();
                        }
                    } else if (workingState == WorkingState.DISPENSING) {
                        pTemp--;
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (pTemp == pFinal) {
                            finishWorking();
                        }
                    }
                    break;
                case FINISHED:
                    sendFinishedSignal();
                    currentState = State.IDLE;
                    container.setState(Container.ContainerState.IDLE);
                    return;
            }
            
        }
    }

    private void finishWorking() {
        currentState = State.FINISHED;
        workingState = null;
        System.out.println("Finished");
    }

    private void sendFinishedSignal() {
        System.out.println("Sending finished signal");
    }

    public State getCurrentState() {
        return currentState;
    }

    public int getPTemp() {
        return pTemp;
    }
}