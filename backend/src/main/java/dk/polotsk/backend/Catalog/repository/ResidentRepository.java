package dk.polotsk.backend.Catalog.repository;

import dk.polotsk.backend.Catalog.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
}
