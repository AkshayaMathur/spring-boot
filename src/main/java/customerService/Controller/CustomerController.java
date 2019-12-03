package customerService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import brave.sampler.Sampler;
import customerService.Entity.Customer;
import customerService.Entity.CustomerDTO;
import customerService.Exception.CustomerException;
import customerService.Service.CustomerServiceLayer;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerServiceLayer customerService;
	
	@GetMapping()
	public ResponseEntity<CustomerDTO> getAllCustomer()
	{
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) throws CustomerException
	{
		return customerService.getCustomerById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer c)
	{
		return customerService.createNewCustomer(c);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer c) throws CustomerException
	{
		return customerService.updateCustomer(id, c);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) throws CustomerException
	{
		return customerService.deleteCustomer(id);
	}	
}
