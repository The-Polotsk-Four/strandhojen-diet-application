package dk.polotsk.backend.Catalog.dto;

import dk.polotsk.backend.Catalog.model.Allergies;
import dk.polotsk.backend.Catalog.model.Diet;
import dk.polotsk.backend.Catalog.model.FoodConsisatency;
import dk.polotsk.backend.Catalog.model.Preference;

import java.time.LocalDate;
import java.util.List;

public record ResidentDto(Long id,
                          String name,
                          FoodConsisatency foodConsisatency,
                          LocalDate age,
                          double weight,
                          double height,
                          double bmi,
                          List<PreferenceDto> preference,
                          List<DietDto> diet,
                          List<AllergiesDto> allergies,
                          int floor,
                          int roomNumber,
                          boolean status,
                          String comment) {
}
