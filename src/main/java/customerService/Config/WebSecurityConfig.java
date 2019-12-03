package customerService.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	/*
	 * @Autowired public void configGlobal(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.userDetailsService(jwtUserDetailsService).passwordEncoder(
	 * passwordEncoder()); }
	 */
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}



	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	 @Override
	@Bean 
	public AuthenticationManager authenticationManagerBean() throws Exception
	 { // TODO Auto-generated method stub return
		 return super.authenticationManagerBean(); 
	  }
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeRequests().antMatchers("/authenticate", "/register").permitAll()
		.and()
		.authorizeRequests().antMatchers("/customer/add").hasAuthority("ROLE_ADMIN").and()
		.authorizeRequests().antMatchers("/customer/update").hasAuthority("ROLE_AMIN").and()
		.authorizeRequests().antMatchers("/customer/delete").hasAuthority("ROLE_ADMIN").and()
		.authorizeRequests().antMatchers("/customer").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN").and()
		//.authorizeRequests().antMatchers("/user").hasAuthority("ROLE_USER").and()
		//.authorizeRequests().antMatchers("/admin").hasAuthority("ROLE_ADMIN").and()
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//.authorizeRequests().antMatchers("").hasAnyAuthority("ROLE_ADMIN").and()
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		
	}

	
	
}
