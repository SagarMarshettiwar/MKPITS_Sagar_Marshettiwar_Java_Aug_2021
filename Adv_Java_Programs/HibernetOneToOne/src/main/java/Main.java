import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory  entityManagerFactory= Persistence.createEntityManagerFactory("default");
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");

         entityManager.getTransaction().begin();
        Vehi vob=new Vehi();
        vob.setVname("Wegonr");

         Usr uob=new Usr();
         uob.setUname("sagar");
         uob.setVo(vob);


        System.out.println("Saving data");

        entityManager.persist(uob);
        entityManager.persist(vob);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}