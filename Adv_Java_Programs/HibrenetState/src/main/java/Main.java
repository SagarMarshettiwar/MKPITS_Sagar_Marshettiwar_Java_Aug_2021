import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");

        Student s=new Student();
        s.setId(1);
        s.setName("sagar");
        s.setAddress("xyz nagar");

        entityManager.getTransaction().begin();

        //s.setName("sagar marshettiwar"); before a session (transient object)

        entityManager.persist(s);


        //s.setName("sagar marshettiwar"); within a session (persist object)



        entityManager.getTransaction().commit();
        entityManager.close();
        //s.setName("sagar marshettiwar"); After a session (Detached object)
        entityManagerFactory.close();
    }
}
