package customerService.Repository;

import org.springframework.data.repository.CrudRepository;

import customerService.Entity.Users;

public interface UsersRepository extends CrudRepository<Users, Long> {
	
	Users findByUsername(String username);
}
