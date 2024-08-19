import java.util.List;


class Reagent extends ReagentComponent {
    public Reagent(String name, int volume, List<ReagentComponent> itemList) {
        super(name, volume, itemList);
    }

    @Override
    public void showDescription() {
        System.out.println("Reagent: " + name + " (Total Volume: " + getVolume() + ")");
        for (ReagentComponent item : itemList) {
            item.showDescription();
        }
    }
}