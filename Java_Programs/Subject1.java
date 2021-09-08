import java.util.Scanner;
public class Subject1 {
    public static void main(String[] args) {
        int total=0;
         int i=1;
        while(i<=3){
            Scanner s=new Scanner(System.in);
            System.out.println("enter 3 subjects marks");
            int marks1=s.nextInt();
            total=total+marks1;
            i++;
        }
        System.out.println("Total ="+total);
    }
}