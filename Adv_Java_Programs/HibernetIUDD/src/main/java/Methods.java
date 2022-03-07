import java.util.Scanner;

public class Methods {
    public Scanner sc;
    public void InsertRec(Student o){
        sc=new Scanner(System.in);
        System.out.println("Enter ID");
        int id1=sc.nextInt();
        System.out.println("Enter NAME");
        String name1=sc.next();
        System.out.println("Enter ADDRESS");
        String address1=sc.next();

        o.setId(id1);
        o.setName(name1);
        o.setAddress(address1);

        Main.entityManager.persist(o);

        Main.entityManager.getTransaction().commit();


    }
    public void UpdateRec(){


        sc=new Scanner(System.in);
        System.out.println("Enter ID");
        int id1=sc.nextInt();

        Student student = Main.entityManager.find(Student.class, id1);
        System.out.println(student.getId()+"   "+student.getName()+"   "+student.getAddress());


        System.out.println("Enter to update NAME");
        String name1=sc.next();
        System.out.println("Enter to update ADDRESS");
        String address1=sc.next();

        student.setName(name1);
        student.setAddress(address1);

        Main.entityManager.merge(student);

        Main.entityManager.getTransaction().commit();


    }
    public void DeleteRec(){


        sc=new Scanner(System.in);
        System.out.println("Enter ID");
        int id1=sc.nextInt();

        Student student = Main.entityManager.find(Student.class, id1);
        System.out.println(student.getId()+"   "+student.getName()+"   "+student.getAddress());

        Main.entityManager.remove(student);

        Main.entityManager.getTransaction().commit();


    }
    public void DisplayRec(){


        sc=new Scanner(System.in);
        System.out.println("Enter ID");
        int id1=sc.nextInt();
        Student student = Main.entityManager.find(Student.class, id1);
        System.out.println(student.getId()+"   "+student.getName()+"   "+student.getAddress());

    }
    public void ExitRec(){
        System.out.println("Transaction finished");

    }
}
