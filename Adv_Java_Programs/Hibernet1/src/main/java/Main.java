import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Starting Transaction");

        entityManager.getTransaction().begin();
        Employees1 ob=new Employees1();
        ob.setId(2l);
        ob.setName("ram");
        ob.setAddress("ram");

        Employees1 ob1=new Employees1();
        ob1.setId(3l);
        ob1.setName("sagar");
        ob1.setAddress("xyz");
        System.out.println("Saving Employee to Database");

        entityManager.persist(ob);
        entityManager.persist(ob1);
        entityManager.getTransaction().commit();

//        close the entity manager
        entityManager.close();
        entityManagerFactory.close();
    }
}
