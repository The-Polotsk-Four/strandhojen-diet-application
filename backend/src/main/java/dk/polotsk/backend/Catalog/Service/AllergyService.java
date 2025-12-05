package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.AllergiesDto;
import dk.polotsk.backend.Catalog.model.Allergies;
import dk.polotsk.backend.Catalog.repository.AllergyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllergyService implements AllergyServiceInterface {

    private final AllergyRepository allergyRepository;

    public AllergyService(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    @Override
    public AllergiesDto createAllergy(AllergiesDto dto) {
        Allergies allergies = new Allergies();
        allergies.setName(dto.name());
        allergies = allergyRepository.save(allergies);

        return new AllergiesDto(allergies.getId(), allergies.getName());
    }

    @Override
    public AllergiesDto getAllergy(Long id) {
        Allergies a = allergyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Allergi kan ikke findes"));
        return new AllergiesDto(a.getId(), a.getName());
    }

    @Override
    public List<AllergiesDto> getAllAllergies() {
        return allergyRepository.findAll()
                .stream()
                .map(a -> new AllergiesDto(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }


    public AllergiesDto deleteAllergy(Long id) {
        if (!allergyRepository.existsById(id)) {
            throw new RuntimeException("Allergy not found " + id);
        }
        allergyRepository.deleteById(id);
        return null;
    }
}