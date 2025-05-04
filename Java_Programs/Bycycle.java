class Bicycle
{
	int speed;	
	boolean isMoving;
	public static void main(String[] arg)
	{
		Bicycle b1=new Bicycle();
		b1.isMoving=true;
		b1.speed=100;
		System.out.println("now bicycle is moving and speed is " + b1.speed);
		b1.ApplyBreak(b1 );
		System.out.println("now bicycle is moving and after applying break speed is " +
		b1.speed);
		b1.isMoving=false;
		b1.ApplyBreak(b1 );
		System.out.println("now bicycle is stopped and speed is " + b1.speed);
	}
	void ApplyBreak(Bicycle b1)
	{
		if(isMoving==true)
		{
			b1.speed=70;
		}
		else
		{
			b1.speed=0;
		}
	}

}