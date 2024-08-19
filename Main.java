class Main {
    public static void main(String args[]) {
        ReagentDatabase database = new ReagentDatabase();
        
        ReagentComponent reagent1 = new RawMaterial("500 mM magnesium chloride", 30);
        ReagentComponent reagent2 = new RawMaterial("100 mM sodium chloride", 50);
        ReagentComponent reagent3 = new RawMaterial("phosphate buffer", 100);
        ReagentComponent reagent4 = new RawMaterial("double distilled water", 200);
        
        database.addComponent(reagent1);
        database.addComponent(reagent2);
        database.addComponent(reagent3);
        database.addComponent(reagent4);
        
        System.out.println("Initial reagents in the database:");
        database.printAllComponents();
        
        Dispenser dispenser = new Dispenser();
        RobotArm robotArm = new RobotArm();
        
        AutoDispenserSystem autoDispenserSystem = new AutoDispenserSystem(dispenser, robotArm, database);
        autoDispenserSystem.startEventListener();
        autoDispenserSystem.createNewReagent("phosphate-buffered saline", 1, 2);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}