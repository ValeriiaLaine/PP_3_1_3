package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.*;
@Repository
public interface RoleRepository {
    List<Role> findAll();
    <S extends Role> S save(S entity);
    void delete(Role entity);
}
