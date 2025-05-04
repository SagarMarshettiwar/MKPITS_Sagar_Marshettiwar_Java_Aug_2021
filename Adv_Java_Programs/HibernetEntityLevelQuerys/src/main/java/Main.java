import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");


        Student s = new Student();
        s.setId(1);
        s.setName("sagar");
        s.setAddress("abc");

        Student s1 = new Student();
        s1.setId(2);
        s1.setName("rahul");
        s1.setAddress("xyz");

        Student s2 = new Student();
        s2.setId(3);
        s2.setName("sameer");
        s2.setAddress("loi");

        Student s3 = new Student();
        s3.setId(4);
        s3.setName("bhuwan");
        s3.setAddress("klj");

        //entityManager.getTransaction().begin();
        entityManager.persist(s);
        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.persist(s3);

//        String Id="1";
//
//        Query query = (Query) entityManager.createNamedQuery("stud");
//        query.setInteger(1,Integer.parseInt(Id));
//        List <Student>list = query.list();
//        for(Student i:list){
//            System.out.println(i.getName()+" "+i.getId()+" "+i.getAddress());
//        }
//
//        System.out.println(list.size());


//        String Id="1";
//
//        Query query = (Query) entityManager.createNamedQuery("Nstud");
////        query.setInteger(1,Integer.parseInt(Id));
//        List <Student>list = query.list();
//        for(Student i:list){
//            System.out.println(i.getName()+" "+i.getId()+" "+i.getAddress());
//        }
//
//        System.out.println(list.size());
//
//        entityManager.getTransaction().commit();















//        Session session =entityManager.unwrap(Session.class);
//        Transaction transaction=session.beginTransaction();
//        Criteria criteria=session.createCriteria(Student.class);
//
//        List<Student> list=criteria.list();
//        for (Student i:list) {
//            System.out.println(i.getName());
//        }
//
//        transaction.commit();
//        session.close();









            //APPLY RESTRICTION ON DATABASE
        Session session =entityManager.unwrap(Session.class);
        Transaction transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(Student.class);
        criteria.add(Restrictions.gt("id",2))
                .add(Restrictions.eq("name","sameer"));
        List<Student> list=criteria.list();
        for (Student i:list) {
            System.out.println(i.getName());
        }

        transaction.commit();
        session.close();


        entityManager.close();
        entityManagerFactory.close();
    }
}