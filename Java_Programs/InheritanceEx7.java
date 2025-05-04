/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InheritanceExamples;

/**
 *
 * @author SAGAR
 */
class person
{
    String name;
    String address;
    void insertpersondata(String name,String address) {
        this.name=name;
        this.address=address;
    }
    void displaypersondata() {
        System.out.println("name " + name);
        System.out.println("address " + address);
    }
}
//creating inherited class student
class employee extends person
{
    int empno;
    int salary;
    void insertemployeedata(int empno,int salary) {
        this.empno=empno;
        this.salary=salary;
    }
    void displayemployeedata() {
        System.out.println("empno " + empno);
        System.out.println("salary " + salary);

    }
}
class parttimeemployee extends employee
{
    int hours;
    void insertparttimeemployeedata(int hours) {
        this.hours=hours;
    }
    void displayparttimeemployeedata() {
        System.out.println("hours " + hours);
    }
}
public class InheritanceEx7 {
    public static void main(String[] args) {
        parttimeemployee s1=new parttimeemployee();
        java.util.Scanner scan=new java.util.Scanner(System.in);
        System.out.println("enter empname");
        String empname=scan.next();
        System.out.println("enter emp address ");
        String empaddress=scan.next();
        s1.insertpersondata(empname,empaddress);
        System.out.println("enter empno");
        int empno=scan.nextInt();
        System.out.println("enter emp salary");
        int salary=scan.nextInt();
        s1.insertemployeedata(empno,salary);
        System.out.println("enter emp working hours");
        int hours=scan.nextInt();
        s1.insertparttimeemployeedata(hours);
        System.out.println("=================================================");
        s1.displaypersondata();
        System.out.println("=================================================");
        s1.displayemployeedata();
        System.out.println("=================================================");
        s1.displayparttimeemployeedata();
    }
}
