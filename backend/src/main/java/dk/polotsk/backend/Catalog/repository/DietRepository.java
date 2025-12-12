package dk.polotsk.backend.Catalog.repository;

import dk.polotsk.backend.Catalog.model.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {

    boolean existsByName(String name);
    Diet getByName(String name);

}
