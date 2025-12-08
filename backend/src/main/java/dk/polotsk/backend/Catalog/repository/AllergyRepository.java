package dk.polotsk.backend.Catalog.repository;

import dk.polotsk.backend.Catalog.model.Allergies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<Allergies, Long> {

    boolean existsByName(String name);

}
