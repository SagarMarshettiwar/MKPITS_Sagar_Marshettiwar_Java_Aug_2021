/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThisKeywordExample;

/**
 *
 * @author SAGAR
 */
class Children{
    int rollno;
    String name,course;
    float fee;

    public Children(int rollno, String name, String course) {
        this.rollno = rollno;
        this.name = name;
    }

    public Children(int rollno, String name, String course, float fee) {
        this(rollno,name,course);
        this.fee = fee;
    }
    void display(){
        System.out.println(rollno+" "+name+" "+course+" "+fee);
    }
}
public class TestThis7 {
    public static void main(String args[]){  
    Children s1=new Children(111,"ankit","java");  
    Children s2=new Children(112,"sumit","java",6000f);  
    s1.display();  
    s2.display();  
    }
}
