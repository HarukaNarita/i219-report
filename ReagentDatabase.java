import java.util.ArrayList;
import java.util.List;

public class ReagentDatabase {
    public List<ReagentComponent> reagentList;
    
    public ReagentDatabase() {
        this.reagentList = new ArrayList<>();
    }

    public List<ReagentComponent> getReagentList(){
        return this.reagentList;
    }
    
    public void addComponent(ReagentComponent reagent) {
        reagentList.add(reagent);
    }

    public void removeComponent(ReagentComponent reagent) {
        reagentList.remove(reagent);
    }

    public ReagentComponent getComponent(int index) {
        if (index >= 0 && index < reagentList.size()) {
            return reagentList.get(index);
        }
        return null;
    }

    public int getSize() {
        return reagentList.size();
    }

    public void printAllComponents() {
        for (ReagentComponent reagent : reagentList) {
            System.out.println(reagent.getName());
        }
    }
}

