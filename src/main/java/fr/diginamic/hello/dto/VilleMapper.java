package fr.diginamic.hello.dto;

import fr.diginamic.hello.entities.Ville;

public class VilleMapper {

    public static VilleDto toDto(Ville ville) {
        if (ville == null) {
            return null;
        }

        VilleDto dto = new VilleDto();
        dto.setId(ville.getId());
        dto.setNom(ville.getNom());
        dto.setPopulation(ville.getPopulation());

        if (ville.getDepartement() != null) {
            dto.setIdDepartement(ville.getDepartement().getId());
            dto.setCodeDepartement(ville.getDepartement().getCode());
        }

        return dto;
    }
}