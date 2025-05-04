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

import com.Mapping.EntityOTM.Address;
import com.Mapping.EntityOTM.Employee;
import com.Mapping.Repository.EmployeeRepo;

@RestController
public class OTMController {

	@Autowired
	EmployeeRepo Erepo;
	
	@PostMapping("/Employee")
	public Employee addEmployee(@RequestBody Employee e) {
		for (Address address : e.getAddresslist()) {
			address.setEmployeeid(e);
	    }
	    return Erepo.save(e);
	}
	
	@GetMapping("/Employee")
	public List<Employee> getAllEmployees() {
		List<Employee> all = Erepo.findAll();
	    return all;
	}
	
	@GetMapping("/Employee/{ID}")
	public Optional<Employee> getEmployeeBYID(@PathVariable("ID") int id) {
		return Erepo.findById(id);
	}
	
	@DeleteMapping("/Employee/{ID}")
	public List<Employee> deleteEmployee(@PathVariable("ID")int id) {
		Erepo.deleteById(id);
		List<Employee> all = Erepo.findAll();
	    return all;
	}
	
	@PutMapping("/Employee/{ID}")
	public List<Employee> updateEmployeebyid(@RequestBody Employee e,@PathVariable("ID")int id) {
		Optional<Employee> byId = Erepo.findById(id);
        if (byId.isPresent()) {
            Employee employee = byId.get();
            employee.setName(e.getName());
            employee.setAbout(e.getAbout());
            List<Address> addresslist = e.getAddresslist();
            if (addresslist != null) {
                for (int i = 0; i < addresslist.size(); i++) {
                    Address address = addresslist.get(i);
                    address.setEmployeeid(employee);
                }
                employee.setAddresslist(addresslist);
            }
            Erepo.save(employee);
        }
        return Erepo.findAll();
	}
}
