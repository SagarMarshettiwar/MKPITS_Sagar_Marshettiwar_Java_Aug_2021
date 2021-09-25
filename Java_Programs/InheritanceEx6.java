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
class P{
    public String name;
    public String address;
    void view(String name,String address){
        this.name=name;
        this.address=address;
    }
    void pDisplay(){
        System.out.println("Name ="+name);
        System.out.println("Address ="+address);
    }
}
class C extends P{
    public int eno;
    public int salary;
    void show(int eno,int salary){
        this.eno=eno;
        this.salary=salary;
    }
    void cDisplay(){
        System.out.println("Emp No ="+eno);
        System.out.println("Salary ="+salary);
        System.out.println("Name ="+name);
        System.out.println("Address ="+address);
    }
}
class T extends C{
    public long mobile;
    public String job;
    void disp(long mobile,String job){
        this.mobile=mobile;
        this.job=job;
    }
    void tDisplay(){
        System.out.println("Mobile ="+mobile);
        System.out.println("Job ="+job);
        System.out.println("Emp No ="+eno);
        System.out.println("Salary ="+salary);
        System.out.println("Name ="+name);
        System.out.println("Address ="+address);
    }
}
public class InheritanceEx6 {
    public static void main(String[] args) {
        T t=new T();
        t.view("sagar", "abcdef");
        t.show(111, 10000);
        t.disp(1234567890, "Hr");
        System.out.println("=================================================");
        t.tDisplay();
        System.out.println("=================================================");
        t.cDisplay();
        System.out.println("=================================================");
        t.pDisplay();
    }
}
