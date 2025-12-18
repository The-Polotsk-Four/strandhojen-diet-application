package dk.polotsk.backend.Catalog.dto;

import dk.polotsk.backend.Catalog.model.Allergies;
import dk.polotsk.backend.Catalog.model.Diet;
import dk.polotsk.backend.Catalog.model.FoodConsistency;
import dk.polotsk.backend.Catalog.model.Preference;
import dk.polotsk.backend.Catalog.model.FoodConsistency;

import java.time.LocalDate;
import java.util.List;

public record ResidentDto(Long id,
                          String name,
                          FoodConsistency foodConsistency,
                          LocalDate age,
                          Double weight,
                          Double height,
                          Double bmi,
                          List<PreferenceDto> preference,
                          List<DietDto> diet,
                          List<AllergiesDto> allergies,
                          Integer floor,
                          Integer roomNumber,
                          Boolean status,
                          String comment) {
}
