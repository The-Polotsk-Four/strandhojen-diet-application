package dk.polotsk.backend.Catalog.controller;

import dk.polotsk.backend.Catalog.Service.ResidentService;
import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.exception.NotFoundException;
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

    @PutMapping("/update/{id}")
    public ResponseEntity<ResidentDto> update(
            @PathVariable Long id,
            @RequestBody ResidentDto residentDto) {
    try {
        return ResponseEntity.ok(residentService.updateResident(id, residentDto));
    } catch (RuntimeException e) {
        throw new NotFoundException(e.getMessage());
    }
    }

@GetMapping("/{id}")
    public ResponseEntity<ResidentDto> getById(@PathVariable Long id){
    try {
        return ResponseEntity.ok(residentService.getResident(id));
    } catch (RuntimeException e) {
        throw new NotFoundException(e.getMessage());
    }
}

    @GetMapping
    public ResponseEntity<List<ResidentDto>> getAll() {
        return ResponseEntity.ok(residentService.getAllResidents());
    }

    @GetMapping("/getresident")
    public ResponseEntity<List<ResidentDto>> getResidentByName(@RequestParam(value = "residentName") String residentName){
    return ResponseEntity.ok(residentService.findResidentByName(residentName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        residentService.deleteResident(id);
        try {
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
