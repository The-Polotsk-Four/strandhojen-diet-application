package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.AllergiesDto;
import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.mapper.Mapper;
import dk.polotsk.backend.Catalog.model.Allergies;
import dk.polotsk.backend.Catalog.model.Resident;
import dk.polotsk.backend.Catalog.repository.AllergyRepository;
import dk.polotsk.backend.Catalog.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final AllergyRepository allergyRepository;

    public ResidentService(ResidentRepository residentRepository, AllergyRepository allergyRepository) {
        this.residentRepository = residentRepository;
        this.allergyRepository = allergyRepository;
    }

    public ResidentDto createResident(ResidentDto residentDto){
        Resident resident = Mapper.toEntity(residentDto);
        return Mapper.toDto(residentRepository.save(resident));
    }

    public ResidentDto updateResident(Long id, ResidentDto dto) {
        Resident existing = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        existing.setName(dto.name());
        existing.setFoodConsistency(dto.FoodConsistency());
        existing.setAge(dto.age());
        existing.setWeight(dto.weight());
        existing.setHeight(dto.height());
        existing.setBmi(dto.bmi());
        existing.setFloor(dto.floor());
        existing.setRoomNumber(dto.roomNumber());
        existing.setStatus(dto.status());
        existing.setComment(dto.comment());

        existing.getPreference().clear();
        existing.getDiet().clear();
        existing.getAllergy().clear();

        dto.preference().forEach(p -> existing.addPreference(Mapper.toEntity(p)));
        dto.diet().forEach(d -> existing.addDiet(Mapper.toEntity(d)));
        dto.allergies().forEach(a -> existing.addAllergy(Mapper.toEntity(a)));

        return Mapper.toDto(residentRepository.save(existing));
    }

    public ResidentDto addAllergy(Long residentId, Long allergyId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + residentId));

        Allergies allergy = allergyRepository.findById(allergyId)
                        .orElseThrow(() -> new RuntimeException("Allergy not found with id: " + allergyId));


        if (!resident.getAllergy().contains(allergy)) {
            resident.addAllergy(allergy);
            residentRepository.save(resident);
        }

        return Mapper.toDto(resident);
    }

    public ResidentDto getResident(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + id));

        return Mapper.toDto(resident);
    }

    public List<ResidentDto> getAllResidents() {
        return residentRepository.findAll()
                .stream()
                .map(Mapper::toDto)
                .toList();
    }

    public void deleteResident(Long id) {
        if (!residentRepository.existsById(id)) {
            throw new RuntimeException("Resident not found with id: " + id);
        }
        residentRepository.deleteById(id);
    }
}
