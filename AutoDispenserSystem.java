import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutoDispenserSystem {
    private List<Container> containerList = new ArrayList<>();
    private Dispenser dispenser;
    private RobotArm robotArm;
    private ReagentDatabase reagentDatabase;
    private Scanner scanner;
    private boolean isRunning;

    public AutoDispenserSystem(
            Dispenser dispenser,
            RobotArm robotArm,
            ReagentDatabase reagentDatabase
            ) {
        this.dispenser = dispenser;
        this.robotArm = robotArm;
        this.reagentDatabase = reagentDatabase;
        this.scanner = new Scanner(System.in);
        this.isRunning = true;

        for (int i = 1; i <= 3; i++) {
            containerList.add(new Container(i, i*10));
        }
    }

    public Dispenser getDispenser(){
        return dispenser;
    }

    public RobotArm getRobotArm(){
        return robotArm;
    }

    public void createNewReagent(String newName, int index1, int index2) {
        List<ReagentComponent> newReagentList = new ArrayList<>();
    
        ReagentComponent reagent1 = reagentDatabase.getComponent(index1);
        ReagentComponent reagent2 = reagentDatabase.getComponent(index2);

        if (reagent1 == null || reagent2 == null) {
            System.out.println("Error: Invalid reagent indices. Please check your input.");
            return;
        }
        System.out.println("-----------------------------");

        Container container1 = containerList.get(0);
        Container container2 = containerList.get(1);
        Container container3 = containerList.get(2);

        container1.setContent(reagent1);
        container2.setContent(reagent2);

        System.out.println("Starting dispensing process...");
        robotArm.move(10);
        System.out.println("-----------------------------");

        System.out.println("Aspirating " + reagent1.getVolume() + " mL of " + reagent1.getName() + " from container 1");
        dispenser.aspirate(reagent1.getVolume(), container1);
        System.out.println("-----------------------------");

        robotArm.move(30);
        System.out.println("-----------------------------");
        
        System.out.println("Dispensing " + reagent1.getVolume() + " mL into container 3");
        dispenser.dispense(reagent1.getVolume(), container3);
        System.out.println("-----------------------------");
        
        robotArm.move(20);
        System.out.println("-----------------------------");
        
        System.out.println("Aspirating " + reagent2.getVolume() + " mL of " + reagent2.getName() + " from container 2");
        dispenser.aspirate(reagent2.getVolume(), container2);
        System.out.println("-----------------------------");
        
        robotArm.move(30);
        System.out.println("-----------------------------");

        System.out.println("Dispensing " + reagent2.getVolume() + " mL into container 3");
        dispenser.dispense(reagent2.getVolume(), container3);
        System.out.println("-----------------------------");
        
        System.out.println("Returning to home position");
        robotArm.move(0);
        System.out.println("-----------------------------");
        
        newReagentList.add(reagent1);
        newReagentList.add(reagent2);
        
        int totalVolume = reagent1.getVolume() + reagent2.getVolume();
        ReagentComponent mixture = new Reagent(newName, totalVolume, newReagentList);
        
        reagentDatabase.addComponent(mixture);
        reagentDatabase.removeComponent(reagent1);
        reagentDatabase.removeComponent(reagent2);
        
        System.out.println("-----------------------------");
        System.out.println("New reagent " + newName + " created with volume " + totalVolume);
        
        // Clear containers after use
        container1.clear();
        container2.clear();
        container3.clear();
        
        System.out.println("-----------------------------");
        System.out.println("Updated components in the database:");
        reagentDatabase.printAllComponents();

        System.out.println("-----------------------------");
        mixture.showDescription();
    }

    public void startEventListener() {
        new Thread(() -> {
            while (isRunning) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim().toLowerCase();
                    handleInput(input);
                }
            }
        }).start();
    }

    private void handleInput(String input) {
        System.out.println("Received command: " + input);
        switch (input) {
            case "s":
                robotArm.stop();
                dispenser.stop();
                break;
            case "r":
                robotArm.resume();
                dispenser.resume();
                break;
            default:
                System.out.println("Unknown command. Available commands: s and r");
        }
    }

    public void shutdown() {
        isRunning = false;
        scanner.close();
    }
}