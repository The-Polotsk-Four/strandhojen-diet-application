package dk.polotsk.backend.Catalog.controller;

import dk.polotsk.backend.Catalog.Service.DietService;
import dk.polotsk.backend.Catalog.dto.AllergiesDto;
import dk.polotsk.backend.Catalog.dto.DietDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diets")
public class DietController {


    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }


    @GetMapping
    public ResponseEntity<List<DietDto>> getAll() {
        return ResponseEntity.ok(dietService.getAllDiets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dietService.getDiet(id));
    }

    @PostMapping("/create")
    public ResponseEntity<DietDto> create(@RequestBody DietDto dto) {
        return ResponseEntity.ok(dietService.createDiet(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dietService.deleteDiet(id);
        return ResponseEntity.noContent().build();
    }
}
