package customerService.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String userAuthority;
	
	public Users() {}
	
	public Users(String username, String password, String userAuthority) {
		super();
		this.username = username;
		this.password = password;
		this.userAuthority = userAuthority;
	}

	public Users(Long id, String username, String password, String userAuthority) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userAuthority = userAuthority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(String userAuthority) {
		this.userAuthority = userAuthority;
	}

	

	
}
