import java.util.Scanner;
public class RectArea {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter Length and Breadth");
        int Length=s.nextInt();
        int Breadth=s.nextInt();
        int Area=Length*Breadth;
        System.out.println("Area of Rectangle ="+Area);
    }
}