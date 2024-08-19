public class Container {
    public enum ContainerState{
        BUSY, IDLE
    }

    public int id;
    public int position;
    public ReagentComponent content;
    public ContainerState containerState;

    public Container(int _id, int _position){
        id = _id;
        position = _position;
        content = null;
        containerState = ContainerState.IDLE;
    }

    public void setState(ContainerState _containerState){
        containerState = _containerState;
    }

    public void setContent(ReagentComponent reagentComponent){
        content = reagentComponent;
    }

    public void clear(){
        content = null;
    }
}
