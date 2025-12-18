package dk.polotsk.backend.Catalog.controller;
import dk.polotsk.backend.Catalog.Service.EmailSenderService;
import dk.polotsk.backend.Catalog.Service.AllergyService;
import dk.polotsk.backend.Catalog.Service.NotificationService;
import dk.polotsk.backend.Catalog.Service.DietService;
import dk.polotsk.backend.Catalog.Service.ResidentService;
import dk.polotsk.backend.Catalog.dto.AllergiesDto;
import dk.polotsk.backend.Catalog.dto.DietDto;
import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.exception.NotFoundException;
import dk.polotsk.backend.Catalog.model.Diet;
import dk.polotsk.backend.Catalog.repository.AllergyRepository;
import dk.polotsk.backend.Catalog.repository.NotificationRepository;
import dk.polotsk.backend.Catalog.repository.DietRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;
    private final AllergyRepository allergyRepository;
    private final AllergyService allergyService;
    private final EmailSenderService emailSenderService;
    private final NotificationService notificationService;

    public ResidentController(ResidentService residentService, AllergyRepository allergyRepository, AllergyService allergyService, EmailSenderService emailSenderService, NotificationService notificationService) {
    this.residentService = residentService;
        this.allergyRepository = allergyRepository;
        this.allergyService = allergyService;
        this.emailSenderService = emailSenderService;
        this.notificationService = notificationService;
    private final DietRepository dietRepository;
    private final DietService dietService;

    public ResidentController(ResidentService residentService, AllergyRepository allergyRepository, AllergyService allergyService, DietRepository dietRepository, DietService dietService) {
    this.residentService = residentService;
        this.allergyRepository = allergyRepository;
        this.allergyService = allergyService;
        this.dietRepository = dietRepository;
        this.dietService = dietService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResidentDto> create(
            @RequestParam String nurseEmail,
            @RequestBody ResidentDto residentDto
    ) {
        System.out.println("CREATE KALDT");
        ResidentDto saved = residentService.createResident(residentDto);

        notificationService.create(
                "Ny beboer oprettet: " + saved.name() +
                        " (Stue " + saved.roomNumber() + ")"
        );
        System.out.println("EMAIL SERVICE KALDT");
        emailSenderService.sendEmail(
                nurseEmail,
                "Ny beboer oprettet",
                "Der er oprettet en ny beboer: " + saved.name()
        );
        return ResponseEntity.ok(saved);
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

@PutMapping("/update/{residentId}/addAllergy")
    public ResidentDto addAllergy(
            @PathVariable Long residentId,
            @RequestBody AllergiesDto allergy) {

    AllergiesDto dto;

    System.out.println(residentId);
    System.out.println(allergy);
    if (!allergyRepository.existsByName(allergy.name())){
        dto = allergyService.createAllergy(allergy);
    } else {
        dto = allergyService.getAllergyByName(allergy.name());
        System.out.println("Allergi eksisterer allerede");
    }

    return residentService.addAllergy(residentId, dto.id());
    }


    @PutMapping("/update/{residentId}/addDiet")
    public ResidentDto addDiet(
            @PathVariable Long residentId,
            @RequestBody DietDto diet) {

        DietDto dto;

        System.out.println(residentId);
        System.out.println(diet);
        if (!dietRepository.existsByName(diet.name())){
            dto = dietService.createDiet(diet);
        } else {
            dto = dietService.getDietByName(diet.name());
            System.out.println("Diet eksisterer allerede");
        }

        return residentService.addDiet(residentId, dto.id());
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

    @DeleteMapping("/update/{residentId}/removeAllergy/{allergyId}")
    public ResidentDto removeAllergy(
            @PathVariable Long residentId,
            @PathVariable Long allergyId
    ) {

        return residentService.removeAllergy(residentId, allergyId);
    }

    @DeleteMapping("/update/{residentId}/removeDiet/{dietId}")
    public ResidentDto removeDiet(
            @PathVariable Long residentId,
            @PathVariable Long dietId
    ) {

        return residentService.removeDiet(residentId, dietId);
    }

}
