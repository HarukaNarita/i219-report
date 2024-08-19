class RawMaterial extends ReagentComponent {
    public RawMaterial(String name, int volume) {
        super(name, volume);
    }
    
    @Override
    public void showDescription() {
        System.out.println(volume + " mL of " + name);
    }
}