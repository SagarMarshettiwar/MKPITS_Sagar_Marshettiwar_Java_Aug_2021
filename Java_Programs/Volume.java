import java.util.Scanner;
public class Volume {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter Length,Breadth and Height");
        int Length=s.nextInt();
        int Breadth=s.nextInt();
        int Height=s.nextInt();
        int Vol=Length*Breadth*Height;
        System.out.println("volume ="+Vol);
    }
}