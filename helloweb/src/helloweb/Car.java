package helloweb;

public class Car {
	private CarMoveBehavior carMoveBehavior;
	
	public Car(CarMoveBehavior carMoveBehavior) {
		this.carMoveBehavior = carMoveBehavior;
	}
	
	public void move() {
		carMoveBehavior.action();
		}
	
	public void setMoveBehavior(CarMoveBehavior carMoveBehavior) {
		this.carMoveBehavior = carMoveBehavior;
	}
	
	public static void main(String[] args) {
		System.out.println("자바 전략 패턴");
		Car car1 = new Car(new UpBehavior());
		car1.move();
		
		Car car2 = new Car(new DownBehavior());
		car2.move(); 
		
		Car car3 = new Car(new LeftBehavior());
		car3.move(); 
		
		Car car4 = new Car(new RightBehavior());
		car4.move(); 
	}

}

