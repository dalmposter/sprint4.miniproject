package springData;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springData.domain.Organizer;
import springData.domain.OrganizerUser;
import springData.domain.Role;
import springData.repository.RoleRepository;
import springData.repository.UserRepository;

@SpringBootApplication
public class OrganizerApp implements CommandLineRunner  { 
	/**
	 * An organizer object for everyone to use.
	 */
	public static Organizer organizer = new Organizer();
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	UserRepository userRepo;
	
	public static void main(String[] args) {
        SpringApplication.run(OrganizerApp.class, args);
        
    }

	@Override
	public void run(String... args) throws Exception {
		
		// TODO Task 
		//Create roles
		
		Role r = new Role(1, "MANAGER");
		roleRepo.save(r);
		
		r = new Role(2, "ASSISTANT");
		roleRepo.save(r);
		
		r = new Role(0, "ADMIN");
//		roleRepo.save(r);
		
		
		//create admin user
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		OrganizerUser u = new OrganizerUser();
		u.setLogin("admin");
		u.setPassword(pe.encode("admin"));
		u.setRole(r);
//		r.setUsers(new HashSet<>());
//		r.getUsers().add(u);
		userRepo.save(u);
	}
}
