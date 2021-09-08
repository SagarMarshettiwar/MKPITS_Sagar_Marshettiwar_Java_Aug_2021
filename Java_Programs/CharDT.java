import java.util.*;
class CharDT
{
	public static void main(String[] arg)
	{
	char b1='A';
	System.out.println("char value " + b1);
	Scanner s=new Scanner(System.in);
	System.out.println("enter character. ");
	b1=s.next().charAt(0);
	System.out.println("char value " + b1);
	}
}