package in.gourav.elite.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import in.gourav.elite.utils.JwtFilter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
public class SecurityConfig {
	
	  @Value("${frontend.url}")
	  private String frontEndURL;
	  private final UserDetailsService userDetailsService;
	   private final JwtFilter jwtFilter;

	    public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter) {
	        this.userDetailsService = userDetailsService;
	        this.jwtFilter = jwtFilter;
	    }
	
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception {
    	System.out.println("entering security");

        httpSecurity
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> request
            		.requestMatchers("/auth/userRegister"
            						,"/auth/userLogin"
            						,"/auth/adminRegister"
            						,"/auth/forgotPassword"
            						,"/auth/verifyOtp"
            						,"/auth/newPassword"
            						,"/api/contact/register"
            						,"/api/product/*"
            						).permitAll()
            		.anyRequest().authenticated())

            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
    
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList(frontEndURL));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
	
	


    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    	provider.setUserDetailsService(userDetailsService);
    	return provider;
    }

}
