package dk.polotsk.backend.Catalog.Controller;

import dk.polotsk.backend.Catalog.Service.ResidentService;
import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.model.Allergies;
import dk.polotsk.backend.Catalog.model.Resident;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;

public ResidentController(ResidentService residentService) {
    this.residentService = residentService;
}

@PostMapping("/create")
    public ResponseEntity<ResidentDto> create(@RequestBody ResidentDto residentDto){
    return ResponseEntity.ok(residentService.createResident(residentDto));
}

    @PutMapping("/update/{residentId}/addAllergy/{allergyId}")
    public ResidentDto addAllergy(
            @PathVariable Long residentId,
            @PathVariable Long allergyId
    ) {
    return residentService.addAllergy(residentId, allergyId);
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
