import org.hibernate.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");


        Student s=new Student();
        s.setId(1);
        s.setName("sagar");
        s.setAddress("abc");

        Student s1=new Student();
        s1.setId(2);
        s1.setName("rahul");
        s1.setAddress("xyz");

        entityManager.getTransaction().begin();
        entityManager.persist(s);
        entityManager.persist(s1);

//        Query query = (Query) entityManager.createQuery("from Student");
//        List list = query.list();
//
//        System.out.println(list.size());

//        Query query = (Query) entityManager.createQuery("from Student");
//        List <Student>list = query.list();
//        for(Student i:list){
//            System.out.println(i.getName()+" "+i.getId()+" "+i.getAddress());
//        }

//        Query query = (Query) entityManager.createQuery("from Student where id=2");
//        List <Student>list = query.list();


//        Query query = (Query) entityManager.createQuery("from Student where id=:id");
//        query.setInteger("id",2);
//        List <Student>list = query.list();
//
//        System.out.println(list.size());

//        Query query = (Query) entityManager.createQuery("from Student where id>1");
//        //query.setInteger("id",2);
//        List <Student>list = query.list();
//        for(Student i:list){
//            System.out.println(i.getName()+" "+i.getId()+" "+i.getAddress());
//        }
//
//        System.out.println(list.size());


            //SQL INJECTION EXAMPLES


//        String Id="3 or 1=1";
//        Query query = (Query) entityManager.createQuery("from Student where id>"+Id);
//        List <Student>list = query.list();
//        for(Student i:list){
//            System.out.println(i.getName()+" "+i.getId()+" "+i.getAddress());
//        }
//
//        System.out.println(list.size());



            // TO HANDLE SQL INJECTION

//        String Id="1";
//
//        Query query = (Query) entityManager.createQuery("from Student where id>?1");//PARAMETER POSITIONING
//        query.setInteger(1,Integer.parseInt(Id));
//        List <Student>list = query.list();
//        for(Student i:list){
//            System.out.println(i.getName()+" "+i.getId()+" "+i.getAddress());
//        }
//
//        System.out.println(list.size());
//
//        entityManager.getTransaction().commit();






        String Id="1";

        Query query = (Query) entityManager.createQuery("from Student where id>:sId");//NORMAL POSITIONING
        query.setInteger("sId",Integer.parseInt(Id));
        List <Student>list = query.list();
        for(Student i:list){
            System.out.println(i.getName()+" "+i.getId()+" "+i.getAddress());
        }

        System.out.println(list.size());

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}