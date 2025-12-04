package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.AllergiesDto;

import java.util.List;

public interface AllergyServiceInterface {
    AllergiesDto createAllergy(AllergiesDto dto);
    AllergiesDto getAllergy(Long id);
    List<AllergiesDto> getAllAllergies();
}
