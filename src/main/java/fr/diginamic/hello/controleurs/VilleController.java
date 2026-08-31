package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.ville.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {


    private VilleController villeController;

    @GetMapping
    public List<Ville> getVilles() {
        return List.of(
                new Ville("Montpellier", 307101),
                new Ville("Marseille", 350751)
        );
    }
}
