import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {
    public static EntityManagerFactory entityManagerFactory;
    public static EntityManager entityManager;
    public static Scanner sc;
    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Start Transaction");
        entityManager.getTransaction().begin();

        Student sob = new Student();
        Methods m = new Methods();

        String s;
        do {
            System.out.println("press 1 = TO INSERT" + "/n" + "press 2 = TO UPDATE" + "/n" + "press 3 = TO Delete" + "/n" + "press 4 = TO DISPLAY" + "/n" + "press 5 = TO EXIT" + "/n");
            sc = new Scanner(System.in);
            System.out.println("enter your choice");
            int ch = sc.nextInt();

            if (ch == 1) {
                m.InsertRec(sob);

            } else if (ch == 2) {
                m.UpdateRec();

            } else if (ch == 3) {
                m.DeleteRec();

            } else if (ch == 4) {
                m.DisplayRec();

            } else if (ch == 5) {
                m.ExitRec();
                break;
            }
            System.out.println("do you want to cotinue y or n");
            s = sc.next();
        } while (s.equals("y") || s.equals("Y"));

        entityManager.close();
        entityManagerFactory.close();
    }
}
