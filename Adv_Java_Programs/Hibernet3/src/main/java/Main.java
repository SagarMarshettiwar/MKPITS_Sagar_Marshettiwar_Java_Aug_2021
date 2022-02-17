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

        Address a=new Address();
        a.setCity("nagpur");
        a.setState("Maharashtra");
        a.setPincode(440013);


        Employee e=new Employee();
        e.setName("Sagar");
        e.setAob(a);

        Address a1=new Address();
        a1.setCity("nagpur1");
        a1.setState("Maharashtra1");
        a1.setPincode(4400131);

        Employee e1=new Employee();
        e1.setName("Sagar1");
        e1.setAob(a1);


        entityManager.persist(e);
        entityManager.persist(e1);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

    }
}
