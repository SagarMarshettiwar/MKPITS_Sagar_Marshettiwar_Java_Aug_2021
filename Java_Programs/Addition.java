import java.util.Scanner;
public class Addition {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter 2 numbers");
        int num1=s.nextInt();
        int num2=s.nextInt();
        int Add=num1+num2;
        System.out.println("Addition of number is ="+Add);
    }
}