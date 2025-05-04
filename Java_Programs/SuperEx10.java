/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperKeyWordExamples;

/**
 *
 * @author SAGAR
 */
class Person{  
    int id;  
    String name;  
    Person(int id,String name){  
        this.id=id;  
        this.name=name;  
    }  
}  
class Emp extends Person{  
    float salary;  
    Emp(int id,String name,float salary){  
        super(id,name);  
        this.salary=salary;  
    }  
    void display(){
        System.out.println(id+" "+name+" "+salary);
    }  
} 
public class SuperEx10 {
    public static void main(String[] args){  
        Emp e1=new Emp(1,"ankit",45000f);  
        e1.display();  
    }
}  

