import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("default");
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");

        entityManager.getTransaction().begin();
        Vehi3 vob1=new Vehi3();
        vob1.setVname("Maruti800");

        Vehi3 vob2=new Vehi3();
        vob2.setVname("Weganr");

        Usr3 uob1=new Usr3();
        uob1.setUname("Sagar");

        Usr3 uob2=new Usr3();
        uob2.setUname("Rasi");

        uob1.getVob().add(vob1);
        uob1.getVob().add(vob2);

        uob2.getVob().add(vob1);
        uob2.getVob().add(vob2);

        //vehi add user
        vob1.getUob().add(uob1);
        vob1.getUob().add(uob2);

        vob2.getUob().add(uob1);
        vob2.getUob().add(uob2);

        System.out.println("Saving data");

        entityManager.persist(uob1);
        entityManager.persist(uob2);
        entityManager.persist(vob1);
        entityManager.persist(vob2);


        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
