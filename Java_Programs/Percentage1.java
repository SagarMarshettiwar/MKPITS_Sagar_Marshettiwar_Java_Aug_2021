import java.util.Scanner;
public class Percentage1 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter 3 subjects marks");
        int marks1=s.nextInt();
        int marks2=s.nextInt();
        int marks3=s.nextInt();
        int total=marks1+marks2+marks3;
        System.out.println("volume ="+total);
        float per=(float)(total/300.0f)*100.0f;
        System.out.println("percentage ="+per);
    }
}