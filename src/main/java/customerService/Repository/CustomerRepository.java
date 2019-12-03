package customerService.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import customerService.Entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
