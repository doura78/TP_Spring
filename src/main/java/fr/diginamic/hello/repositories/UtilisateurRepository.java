package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Utilisateur findByUsername(String username);
}