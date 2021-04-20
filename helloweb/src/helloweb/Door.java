package helloweb;

enum DoorStatus { CLOSED, OPENED }
enum MotorStatus { MOVING, STOPPED }

public abstract class Door {
		  protected DoorStatus doorStatus;

		  public Door() { doorStatus = DoorStatus.CLOSED; }
		  public DoorStatus getDoorStatus() { return doorStatus; }
		  public abstract void close(); 
		  public abstract void open(); 
		}
