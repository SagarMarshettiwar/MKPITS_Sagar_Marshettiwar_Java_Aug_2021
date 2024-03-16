package springJPA.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import springJPA.dao.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	public List<User> findByName(String name);
	
	public List<User> findByNameAndCity(String name,String city);
	
	@Query("select u from User u")
	public List<User> getAllUsers();
	
	@Query("select u from User u where u.name=:n and u.city=:c")
	public List<User> getMyUsers(@Param("n")String name,@Param("c")String city);
	
	@Query(value="select * from User",nativeQuery=true)
	public List<User> getdata();
	
	 @Query(value = "SELECT * FROM User WHERE name=:n", nativeQuery = true)
	    List<User> findByYourColumn(@Param("n") String name);
}
