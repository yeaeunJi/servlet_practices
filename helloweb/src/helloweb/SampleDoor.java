package helloweb;

public class SampleDoor extends Door {
	  public SampleDoor() { super(); }
	  
	  @Override
	  public void close() { doorStatus = DoorStatus.CLOSED; }
	  
	  @Override
	  public void open() { doorStatus = DoorStatus.OPENED; }
}
