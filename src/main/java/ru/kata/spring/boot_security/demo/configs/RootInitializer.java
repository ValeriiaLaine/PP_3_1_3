package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class RootInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RootInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createAdmin();
        createUser();
    }

    private void createAdmin() {
        Role rootRole = new Role("ROLE_ADMIN");
        roleRepository.save(rootRole);

        User root = new User("admin", passwordEncoder.encode("admin"), new ArrayList<>());
        root.getRoles().add(rootRole);
        userRepository.save(root);
    }
    private void createUser() {
        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);

        User user = new User("user", passwordEncoder.encode("user"), new ArrayList<>());
        user.getRoles().add(userRole);
        userRepository.save(user);
    }
}
