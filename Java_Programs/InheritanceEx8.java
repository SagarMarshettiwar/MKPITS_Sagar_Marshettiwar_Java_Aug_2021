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
class person1
{
    String name;
    String address;
    void insertpersondata1(String name,String address) {
        this.name=name;
        this.address=address;
    }
    void displaypersondata1() {
        System.out.println("name " + name);
        System.out.println("address " + address);
    }
}
class student1 extends person1
{
    int rno;
    int marks;
    void insertstudentdata1(int rno,int marks) {
        this.rno=rno;
        this.marks=marks;
    }
    void displaystudentdata1() {
        System.out.println("rno " + rno);
        System.out.println("marks " + marks);
    }
}
class employee1 extends person1
{
    int empno;
    int salary;
    void insertemployeedata1(int empno,int salary) {
        this.empno=empno;
        this.salary=salary;
    }
    void displayemployeedata1() {
        System.out.println("empno " + empno);
        System.out.println("salary " + salary);
    }
}
public class InheritanceEx8 {
    public static void main(String[] arg)
    {
        System.out.println("----enter employee details----");
        employee1 s1=new employee1();
        java.util.Scanner scan=new java.util.Scanner(System.in);
        System.out.println("enter empname");
        String empname=scan.next();
        System.out.println("enter emp address ");
        String empaddress=scan.next();
        s1.insertpersondata1(empname,empaddress);
        System.out.println("enter empno");
        int empno=scan.nextInt();
        System.out.println("enter emp salary");
        int salary=scan.nextInt();
        s1.insertemployeedata1(empno,salary);
        System.out.println("=================================================");
        s1.displaypersondata1();
        System.out.println("=================================================");
        s1.displayemployeedata1();
        System.out.println("-------------------------------------------------");
        System.out.println("----enter student details----");
        student1 s2=new student1();
        System.out.println("enter student name");
        String sname=scan.next();
        System.out.println("enter student address ");
        String saddress=scan.next();
        s2.insertpersondata1(sname,saddress);
        System.out.println("enter rno");
        int rno=scan.nextInt();
        System.out.println("enter marks");
        int marks=scan.nextInt();
        s2.insertstudentdata1(rno,marks);
        System.out.println("=================================================");
        s2.displaypersondata1();
        System.out.println("=================================================");
        s2.displaystudentdata1();

}
}
