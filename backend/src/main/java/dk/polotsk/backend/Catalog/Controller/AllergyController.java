package dk.polotsk.backend.Catalog.Controller;

import dk.polotsk.backend.Catalog.Service.AllergyService;
import dk.polotsk.backend.Catalog.dto.AllergiesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allergies")
public class AllergyController {

    private final AllergyService allergyService;

    public AllergyController(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    @GetMapping
    public ResponseEntity<List<AllergiesDto>> getAll() {
        return ResponseEntity.ok(allergyService.getAllAllergies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllergiesDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(allergyService.getAllergy(id));
    }

    @PostMapping("/create")
    public ResponseEntity<AllergiesDto> create(@RequestBody AllergiesDto dto) {
        return ResponseEntity.ok(allergyService.createAllergy(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        allergyService.deleteAllergy(id);
        return ResponseEntity.noContent().build();
    }
}
