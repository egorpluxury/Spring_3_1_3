package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class DbInit implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DbInit(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        User admin = new User();
        admin.setPassword(passwordEncoder.encode("12345"));
        admin.setRoles(adminRoles);
        admin.setLastName("Ivanov");
        admin.setAge(32);
        admin.setFirstName("Ivan");
        admin.setEmail("admin@gmail.com");

        userRepository.save(admin);

        User user = new User();
        user.setPassword(passwordEncoder.encode("54321"));
        user.setRoles(userRoles);
        user.setLastName("Kirillov");
        user.setAge(32);
        user.setFirstName("Kirill");
        user.setEmail("user@gmail.com");

        userRepository.save(user);
    }
}
