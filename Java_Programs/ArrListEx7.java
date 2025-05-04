/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author SAGAR
 */
class Student{
    String name;
    int id;
    String Subject;

    public Student(String name, int id, String Subject) {
        this.name = name;
        this.id = id;
        this.Subject = Subject;
    }
    
}
public class ArrListEx7 {
    public static void main(String[] args) {
        Student s1=new Student("Sagar",1,"java");
        Student s2=new Student("Dolly",2,"Sql");
        Student s3=new Student("Ankita",3,".net");
        ArrayList<Student> arr=new ArrayList<Student>();
        arr.add(s1);
        arr.add(s2);
        arr.add(s3);
        Iterator itr=arr.iterator();
        while(itr.hasNext()){
            Student st=(Student)itr.next();
            System.out.println(st.name+" "+st.id+" "+st.Subject);
        }
    }
}
