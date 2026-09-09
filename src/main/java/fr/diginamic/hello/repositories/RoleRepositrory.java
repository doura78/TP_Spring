package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositrory extends JpaRepository<Role, Integer> {
}
