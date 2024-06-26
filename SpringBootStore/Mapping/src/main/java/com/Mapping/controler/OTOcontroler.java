package com.Mapping.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Mapping.Entity.Laptop;
import com.Mapping.Entity.Student;
import com.Mapping.Repository.DataRepo;

@RestController
public class OTOcontroler {
	@Autowired
	DataRepo repo;
	
	@PostMapping("/student")
	public Student addBooks(@RequestBody Student s) {
		Student save = repo.save(s);
		return save;
	}
	
	@GetMapping("/student")
    public List<Student> getAllstudent() {
	 	List<Student> all = repo.findAll();
        return all;
    }
	
	@GetMapping("/student/{ID}")
	public Optional<Student> getBooksBYID(@PathVariable("ID") int id) {
		Optional<Student> byId = repo.findById(id);
		return byId;
	}
 
	@DeleteMapping("/student/{ID}")
	public void deleteBook(@PathVariable("ID")int id) {
		repo.deleteById(id);
	}
	@PutMapping("/student/{ID}")
	public List<Student> updateBooks(@RequestBody Student s,@PathVariable("ID")int id) {
		Optional<Student> byId = repo.findById(id);
		if(!byId.isEmpty()) {
			Student student= byId.get();
			student.setName(s.getName());
			student.setState(s.getState());
			student.setCity(s.getCity());
			 Laptop laptop = s.getLaptop();
		        if (laptop != null) {
		        	Laptop l = student.getLaptop();
		            l.setLname(laptop.getLname());
		            l.setLmodel(laptop.getLmodel());
		            student.setLaptop(l);
		        }
			repo.save(student);
			List<Student> all = (List<Student>) this.repo.findAll();
			return all;
		}else {
			
		}
		return null;
	}
}
