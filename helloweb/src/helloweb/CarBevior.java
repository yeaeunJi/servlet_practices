package helloweb;

	class UpBehavior implements CarMoveBehavior{
		@Override
		public void action() {
			System.out.println("Up");
		}
	}
	class DownBehavior implements CarMoveBehavior{
		@Override
		public void action() {
			System.out.println("down");
		}
	}
	class LeftBehavior implements CarMoveBehavior{
		@Override
		public void action() {
			System.out.println("Left");
		}
	}
	class RightBehavior implements CarMoveBehavior{
		@Override
		public void action() {
			System.out.println("Right");
		}
	
}
