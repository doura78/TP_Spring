package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    List<Departement> findByNomStartingWithIgnoreCase(String prefixe);

    Departement findByCode(String code);
}