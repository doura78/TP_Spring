package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entities.Ville;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, Integer> {

    List<Ville> findByNomStartingWithIgnoreCase(String prefixe);

    List<Ville> findByPopulationGreaterThanOrderByPopulationDesc(int min);

    List<Ville> findByPopulationBetweenOrderByPopulationDesc(int min, int max);

    List<Ville> findByDepartementIdAndPopulationGreaterThanOrderByPopulationDesc(int idDepartement, int min);

    List<Ville> findByDepartementIdAndPopulationBetweenOrderByPopulationDesc(int idDepartement, int min, int max);

    List<Ville> findByDepartementIdOrderByPopulationDesc(int idDepartement, Pageable pageable);
}