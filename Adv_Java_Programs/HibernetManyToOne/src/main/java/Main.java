import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("default");
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");

        entityManager.getTransaction().begin();
        Vehi2 vob1=new Vehi2();
        vob1.setVname("Maruti800");

        Vehi2 vob2=new Vehi2();
        vob2.setVname("Weganr");

        Usr2 uob1=new Usr2();
        uob1.setUname("Sagar");

        uob1.getVob().add(vob1);
        uob1.getVob().add(vob2);

        //vehi add user

        vob1.setUob(uob1);
        vob2.setUob(uob1);

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
