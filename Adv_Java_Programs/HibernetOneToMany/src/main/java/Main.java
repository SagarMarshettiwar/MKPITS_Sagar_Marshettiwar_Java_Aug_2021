import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("default");
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");

        entityManager.getTransaction().begin();
        Vehi1 vob1=new Vehi1();
        vob1.setVname("Maruti800");

        Vehi1 vob2=new Vehi1();
        vob2.setVname("Weganr");

        Usr1 uob1=new Usr1();
        uob1.setUname("Priya");
        uob1.getVob().add(vob1);
        uob1.getVob().add(vob2);

        System.out.println("Saving data");

        entityManager.persist(uob1);
        entityManager.persist(vob1);
        entityManager.persist(vob2
        );
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
