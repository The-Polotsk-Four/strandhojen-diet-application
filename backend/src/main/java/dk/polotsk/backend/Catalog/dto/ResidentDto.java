package dk.polotsk.backend.Catalog.dto;

import dk.polotsk.backend.Catalog.model.FoodConsisatency;
import dk.polotsk.backend.Catalog.model.Preference;

import java.time.LocalDate;

public record ResidentDto(Long id, FoodConsisatency foodConsisatency, LocalDate age, double weight, double height, double bmi, Preference preference, int floor, int roomNumber, boolean status, String comment) {
}
