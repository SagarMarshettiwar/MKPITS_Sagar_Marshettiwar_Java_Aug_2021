import java.util.Scanner;
public class Percentage {
    public static void main(String[] args) {
        int total=0;
        int i=1;
        while(i<=3){
            Scanner s=new Scanner(System.in);
            System.out.println("enter subjects marks");
            int marks1=s.nextInt();
            total=total+marks1;
            i++;
        }
        System.out.println("Total ="+total);
        float per=(float)(total/300.0f)*100.0f;
        System.out.println("percentage ="+per);
    }
}