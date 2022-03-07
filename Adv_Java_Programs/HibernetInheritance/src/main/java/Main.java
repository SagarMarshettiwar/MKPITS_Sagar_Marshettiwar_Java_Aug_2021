import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("default");
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");

        entityManager.getTransaction().begin();
        Vehi vob1=new Vehi();
        vob1.setVname("Vehicle");

        TwoWheel twob=new TwoWheel();
        twob.setVname("hero honda");
        twob.setSthandle("has a handle");

        FourWheel fwob=new FourWheel();
        fwob.setVname("Maruti");
        fwob.setStrwheel("has a Steering");

        System.out.println("Saving data");

        entityManager.persist(twob);
        entityManager.persist(fwob);
        entityManager.persist(vob1);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
