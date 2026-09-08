package fr.diginamic.hello.service;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.exceptions.DepartementException;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DepartementServiceTest {

    @Autowired
    private DepartementService service;

    @Autowired
    private DepartementRepository departementRepository;

    @Test
    void testExtractDepartementParCode() {
        Departement departement = new Departement();
        departement.setCode("01");
        departement.setNom("Ain");

        departementRepository.save(departement);

        Departement result = service.extractDepartementParCode("01");

        assertNotNull(result);
        assertEquals("Ain", result.getNom());
    }

    @Test
    void testExtractDepartementParNom() {
        Departement departement = new Departement();
        departement.setCode("34");
        departement.setNom("Herault");

        departementRepository.save(departement);

        Departement result = service.extractDepartementParNom("Her");

        assertNotNull(result);
        assertEquals("Herault", result.getNom());
    }

    @Test
    void testInsertDepartement() {
        Departement departement = new Departement();
        departement.setCode("75");
        departement.setNom("Paris");

        List<Departement> result = service.insertDepartement(departement);

        assertFalse(result.isEmpty());
        assertNotNull(service.extractDepartementParCode("75"));
    }

    @Test
    void testModifierDepartement() {
        Departement departement = new Departement();
        departement.setCode("69");
        departement.setNom("Rhone");

        departement = departementRepository.save(departement);

        departement.setNom("Rhône");

        service.modifierDepartement(departement);

        Departement result = service.extractDepartementParCode("69");

        assertNotNull(result);
        assertEquals("Rhône", result.getNom());
    }

    @Test
    void testSupprimerDepartement() throws DepartementException {
        Departement departement = new Departement();
        departement.setCode("83");
        departement.setNom("Var");

        departement = departementRepository.save(departement);

        Integer id = departement.getId();

        List<Departement> result = service.supprimerDepartement(id);

        assertNotNull(result);
        assertNull(service.extractDepartement(id));
    }

    @Test
    void testSupprimerDepartementInexistant() {
        DepartementException exception = assertThrows(
                DepartementException.class,
                () -> service.supprimerDepartement(99999)
        );

        assertEquals("Département introuvable", exception.getMessage());
    }
}