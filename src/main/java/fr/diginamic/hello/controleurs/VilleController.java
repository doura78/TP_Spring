package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.ville.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {


    private VilleController villeController;

    @GetMapping
    public List<Ville> getVilles() {
        ArrayList<Ville> villes = new ArrayList<>();

                villes.add(new Ville("Montpellier", 307101));
                villes.add(new Ville("Marseille", 350751));

                return villes;
    }
}
