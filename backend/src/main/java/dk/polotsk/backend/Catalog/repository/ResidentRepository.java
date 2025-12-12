package dk.polotsk.backend.Catalog.repository;

import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {

    List<Resident> findResidentByName(String name);
}
