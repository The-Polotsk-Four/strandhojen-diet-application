package dk.polotsk.backend.Catalog.Controller;

import dk.polotsk.backend.Catalog.Service.AllergyService;
import dk.polotsk.backend.Catalog.Service.ResidentService;
import dk.polotsk.backend.Catalog.dto.AllergiesDto;
import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.model.Allergies;
import dk.polotsk.backend.Catalog.model.Resident;
import dk.polotsk.backend.Catalog.repository.AllergyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;
    private final AllergyRepository allergyRepository;
    private final AllergyService allergyService;

    public ResidentController(ResidentService residentService, AllergyRepository allergyRepository, AllergyService allergyService) {
    this.residentService = residentService;
        this.allergyRepository = allergyRepository;
        this.allergyService = allergyService;
    }

@PostMapping("/create")
    public ResponseEntity<ResidentDto> create(@RequestBody ResidentDto residentDto){
    return ResponseEntity.ok(residentService.createResident(residentDto));
}

@PutMapping("/update/{residentId}/addAllergy")
    public ResidentDto addAllergy(
            @PathVariable Long residentId,
            @RequestBody AllergiesDto allergy
    ) {

    AllergiesDto dto;

    System.out.println(residentId);
    if (!allergyRepository.existsByName(allergy.name())){
        dto = allergyService.createAllergy(allergy);
    } else {
        dto = allergyService.getAllergyByName(allergy.name());
        System.out.println("Allergi eksisterer allerede");
    }

    return residentService.addAllergy(residentId, dto.id());
    }

@GetMapping("/{id}")
    public ResponseEntity<ResidentDto> getById(@PathVariable Long id){
    return  ResponseEntity.ok(residentService.getResident(id));
}

    @GetMapping
    public ResponseEntity<List<ResidentDto>> getAll() {
        return ResponseEntity.ok(residentService.getAllResidents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        residentService.deleteResident(id);
        return ResponseEntity.noContent().build();
    }
}
