package customerService.Config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import customerService.Service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;


@Component
public class JwtRequestFilter extends GenericFilterBean {

	@Autowired
	private JwtUserDetailsService jwtUserDetailService;
	
	@Autowired
	private jwtTokenUtil jwtTokenUtil;
	
	@Override
	public void doFilter(ServletRequest srequest, ServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final HttpServletRequest request = (HttpServletRequest) srequest;
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwtToken = null;
		
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
		{
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			}
			catch (IllegalArgumentException e) {
				System.out.println("Unable To Get JWT TOken");
			}
			catch (ExpiredJwtException e) {
				System.out.println("JWT Token has Expired");
			}
		}
		else
		{
			logger.warn("JWT Token does not begin with Bearer string");
		}
		
		// Once we get the token validate it.
		if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = this.jwtUserDetailService.loadUserByUsername(username);
			
			// if token is valid configure Spring Security to manually set
			// authentication
			if(jwtTokenUtil.validateToken(jwtToken, userDetails))
			{
				UsernamePasswordAuthenticationToken obj = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				obj.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(obj);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
