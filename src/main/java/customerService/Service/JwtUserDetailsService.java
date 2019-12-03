package customerService.Service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import customerService.Entity.Users;
import customerService.Entity.UsersDTO;
import customerService.Repository.UsersRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users foundUser = usersRepo.findByUsername(username);
		
		if(foundUser == null)
		{
			throw new UsernameNotFoundException("User Not Found With Username: " + username);
		}
		
		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(foundUser.getUserAuthority()));
		
		return new User(foundUser.getUsername(), foundUser.getPassword(), authorities);
	}
	
	
	public Users saveNewUser(UsersDTO newUser)
	{
		Users temp = new Users();
		temp.setUsername(newUser.getUsername());
		temp.setPassword(bcryptEncoder.encode(newUser.getPassword()));
		temp.setUserAuthority(newUser.getUserAuthority());
		usersRepo.save(temp);
		return temp;
	}
	
	
	
}
