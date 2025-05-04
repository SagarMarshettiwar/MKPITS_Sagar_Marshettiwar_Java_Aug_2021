package springJPA;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import springJPA.dao.User;
import springJPA.repo.UserRepository;

@SpringBootApplication
public class SpringJpaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(SpringJpaApplication.class, args);
		UserRepository bean = run.getBean(UserRepository.class);

		/*
		 * //===================For Saving Single Data or List of Data===================
		 * User u=new User(); u.setName("sakshi");
		 * u.setCity("chandrapur"); u.setStatus("poor"); User save = bean.save(u);
		 * System.out.println(save);
		 * 
		 * User u1 = new User(); u1.setName("rohit1"); u1.setCity("bihar1");
		 * u1.setStatus("poor1"); User u2 = new User(); u2.setName("aniket1");
		 * u2.setCity("pune1"); u2.setStatus("poor1"); User u3 = new User();
		 * u3.setName("charan1"); u3.setCity("kanpur1"); u3.setStatus("poor1");
		 * 
		 * List<User> mylist = List.of(u1, u2, u3); Iterable<User> saveAll =
		 * bean.saveAll(mylist); for (User user : saveAll) { System.out.println(user); }
		 */
		 
		/*
		 * ====================For Updating the Data ========================
		 * Optional<User> optional = bean.findById(53); User user = optional.get();
		 * user.setName("sameer"); User save = bean.save(user);
		 * System.out.println(save);
		 */
		
		/*
		 * =================== For getting all data from table===============
		 * Iterable<User> all = bean.findAll(); for(User u:all) { System.out.println(u);
		 * }
		 */
		
		/*
		 * ====================For Deleting Data===================
		 * bean.deleteById(2); System.out.println("Deleted");
		 */
		
		/*
		 * =======================For deleting all data===================
		 * Iterable<User> all = bean.findAll(); for(User u:all) { System.out.println(u);
		 * } bean.deleteAll(all);
		 */
		
		
		
		/*
		 * =======================Custom Finder Methods  (Create Methods in Repository to use)===========================
		 * List<User> name = bean.findByName("sakshi"); for(User u:name) {
		 * System.out.println(u); }
		 * 
		 *List<User> name = bean.findByNameAndCity("sagar","nagpur"); 
		 * for(User u:name) {
		 * System.out.println(u);}
		 */
		
		/*
		 * ====================@Query==================
		 * List<User> allUsers = bean.getAllUsers(); for(User u:allUsers) {
		 * System.out.println(u); }
		 * 
		 * List<User> myUsers = bean.getMyUsers("sagar");
		 * for(User u:myUsers) {
		 * System.out.println(u);}
		 * 
		 * List<User> myUsers = bean.getMyUsers("sagar","nagpur");
		 * for(User u:myUsers) {
		 * System.out.println(u);}
		 */
		
		/*
		 * ===================SQL Native Query==================================
		 * List<User> getdata = bean.getdata(); for(User u:getdata) {
		 * System.out.println(u); }
		 * 
		 * List<User> byYourColumn = bean.findByYourColumn("sagar");
		 * for(User u:byYourColumn) {
		 * System.out.println(u); }
		 */
		
		
	}

}
