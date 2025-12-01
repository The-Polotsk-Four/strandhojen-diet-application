package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.mapper.Mapper;
import dk.polotsk.backend.Catalog.model.Resident;
import dk.polotsk.backend.Catalog.repository.ResidentRepository;
import org.springframework.stereotype.Service;

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

    public ResidentDto updateResident(Long id, ResidentDto residentDto) {
        Resident existing = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found with id: " + id));

        Resident updated = Mapper.toEntity(residentDto);
        updated.setId(id); // ensure the ID is preserved

        return Mapper.toDto(residentRepository.save(updated));
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
