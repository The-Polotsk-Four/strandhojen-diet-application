package dk.polotsk.backend.Catalog.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FoodConsistency {
    SOLID,
    TUBEFEEDING,
    SOFTFOOD;

    @JsonCreator
    public static FoodConsistency from(String value) {
        return FoodConsistency.valueOf(value.toUpperCase());
    }
}


