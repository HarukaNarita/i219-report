import java.util.ArrayList;
import java.util.List;

abstract class ReagentComponent {
    protected String name;
    protected int volume;
    protected List<ReagentComponent> itemList;

    public ReagentComponent(String name, int volume, List<ReagentComponent> itemList) {
        this.name = name;
        this.volume = volume;
        this.itemList = itemList;
    }
    
    public ReagentComponent(String name, int volume) {
        this.name = name;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public int getVolume(){
        return volume;
    };

    public List<ReagentComponent> getItemList() {
        return new ArrayList<>(itemList);
    }

    public abstract void showDescription();
}