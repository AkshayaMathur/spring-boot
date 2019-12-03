package customerService.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import brave.sampler.Sampler;
import customerService.Entity.Customer;
import customerService.Entity.CustomerDTO;
import customerService.Exception.CustomerException;
import customerService.Repository.CustomerRepository;

@Service
public class CustomerServiceLayer {
	
	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private CustomerDTO obj;
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	
	public ResponseEntity<CustomerDTO> getAllCustomers()
	{
		LOG.info("In Method GetAllCustomers");
		List<Customer> temp = new ArrayList<>();
		repo.findAll().forEach(temp::add);
		obj.setCustList(temp);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	public ResponseEntity<Customer> getCustomerById(Long id) throws CustomerException
	{
		LOG.info("In Method GetCustomerById");
		Customer c = repo.findById(id).orElseThrow(()->new CustomerException("Unable to Find Customer By Id"));
		return new ResponseEntity<>(c, HttpStatus.FOUND);
	}
	
	public ResponseEntity<Customer> createNewCustomer(Customer c)
	{
		LOG.info("In Method createNewCustomer");
		repo.save(c);
		return new ResponseEntity<>(c, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Customer> updateCustomer(Long cid, Customer c) throws CustomerException
	{
		LOG.info("In Method UpdateCustomer");
		Customer temp = repo.findById(cid).orElseThrow(()->new CustomerException("Unable To Find Customer By ID"));
		temp.setAddress(c.getAddress());
		temp.setCname(c.getCname());
		repo.save(temp);
		return new ResponseEntity<>(temp, HttpStatus.OK);
	}
	
	public ResponseEntity<Customer> deleteCustomer(Long cid) throws CustomerException
	{
		LOG.info("In Method DeleteCustomer");
		Customer temp = repo.findById(cid).orElseThrow(()->new CustomerException("Unable To Find Customer By ID"));
		repo.deleteById(cid);
		
		return new ResponseEntity<>(temp, HttpStatus.OK);
	}

}
