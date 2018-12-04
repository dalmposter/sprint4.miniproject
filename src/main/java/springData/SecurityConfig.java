package springData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	protected void configure(HttpSecurity http) throws Exception
	{
		http
		.requiresChannel()
			.anyRequest()
			.requiresSecure()
		.and().formLogin()
			.loginPage("/user-login")
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/success-login", true)
			.failureUrl("/error-login")
			.permitAll()
		.and().logout()
			.invalidateHttpSession(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/user-login")
			.permitAll()
		.and().authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/create/**").hasRole("MANAGER")
			.antMatchers("/delete/**").hasRole("MANAGER")
			.antMatchers("/list/**").hasAnyRole("ASSISTANT", "MANAGER")
		.and().exceptionHandling().accessDeniedPage("/access-denied");
	}
	
	@Autowired 
	private UserDetailsService userDetailsService; 	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(pe);
		
	}
}
