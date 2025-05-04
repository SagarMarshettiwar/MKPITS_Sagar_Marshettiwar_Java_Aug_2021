import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("default");
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");

        entityManager.getTransaction().begin();

        Employee e=new Employee();
        e.setName("Sagar");
        e.setAddress("xyz");
        e.setJoining(new Date());

        entityManager.persist(e);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

    }
}
