import java.util.Scanner;
public class Employee {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter employee no. ");
        int empno=s.nextInt();
        System.out.println("enter employee name");
        String empname=s.next();
        System.out.println("enter employee Salary");
        double empsalary=s.nextDouble();
        System.out.println("----------------------------------------------------------------");
        int hra=1200;
        int da=2000;
        double totalSalary=hra+da+empsalary;
        System.out.println("emp no ="+empno);
        System.out.println("empname ="+empname);
        System.out.println("emp salary ,hra and da"+empsalary+" "+hra+" "+da);
        System.out.println("total Salary ="+totalSalary);
    }
}