package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.mapper.Mapper;
import dk.polotsk.backend.Catalog.model.Allergies;
import dk.polotsk.backend.Catalog.model.Resident;
import dk.polotsk.backend.Catalog.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;

    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
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

    public void addAllergy(Long id, Allergies allergies) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + id));

        resident.addAllergy(allergies);
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
