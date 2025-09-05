package in.gourav.elite.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.gourav.elite.exception.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter  extends OncePerRequestFilter{
	
	
	  @Autowired
	   private JWTService jwtService;	
	  

	    @Autowired
	    private MyDetailService userDetailService; 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSYW15YSIsImlhdCI6MTc0NDEyMjY4MiwiZXhwIjoxNzQ0MTIyNzkwfQ.jaxBQokjWjzTHv5rl7GSZdImJm4VYatQJ5Q5LqxGcP4
		// TODO Auto-generated method stub
		System.out.println("entering jwt");
		
		String path = request.getServletPath();
		if (path.startsWith("/auth/") || path.equals("/auth/adminRegister") || path.startsWith("/api/contact/register") || (path.startsWith("/api/product/") && (!path.equals("/api/product/update") && (!path.equals("/api/product/add"))))) {
		    filterChain.doFilter(request, response);
		    return;
		}

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		if(authHeader !=null && authHeader.startsWith("Bearer ")) { 
			token = authHeader.substring(7);
			username =  jwtService.extractUserName(token);
		}else {
			throw new UserException("Unauthorized");
		}
			 
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				  UserDetails userDetails =   userDetailService.loadUserByUsername(username);
			
			
				 if(jwtService.validateToken(token,userDetails)) {
					 
					 Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
					 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
					 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				 
				  }
			 }
			 
			
		
		filterChain.doFilter(request, response);
		
	}
	
	
	
	

}
