\\wap to accept empname,basic_salary and designation and then calculate bonus using switch statement
import java.util.*;
class StringSwitchDemo
{
	public static void main(String[] arg)
	{
	Scanner s=new Scanner(System.in);
	String empname,designation ;
	int basic_salary;
	int bonus=0;
	System.out.println("enter empname");
	empname=s.next();
	System.out.println("enter designation");
	designation=s.next();
	System.out.println("enter basic salary");
	basic_salary=s.nextInt();
	switch(designation) {
	case "manager" :
		bonus=1200;
		break;
	case "clerk" :
		bonus=500;
		break;
	case "peon" :
		bonus=100;
		break;
	default:
		System.out.println("invalid destination");
		break;
	}
	System.out.println("empname " + empname);
	System.out.println("designation " + designation);
	System.out.println("salary " + basic_salary);
	System.out.println("bonus " + bonus);
	}
}