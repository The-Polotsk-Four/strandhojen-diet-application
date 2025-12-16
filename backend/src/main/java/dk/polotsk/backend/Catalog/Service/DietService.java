package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.DietDto;
import dk.polotsk.backend.Catalog.model.Diet;
import dk.polotsk.backend.Catalog.repository.DietRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietService {
    private final DietRepository dietRepository;

    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }


    public DietDto createDiet(DietDto dto) {
        Diet diets = new Diet();
        diets.setName(dto.name());
        diets = dietRepository.save(diets);

        return new DietDto(diets.getId(), diets.getName());
    }

    public DietDto getDiet(Long id) {
        Diet d = dietRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diet can't be found"));
        return new DietDto(d.getId(), d.getName());
    }

    public List<DietDto> getAllDiets() {
        return dietRepository.findAll()
                .stream()
                .map(d -> new DietDto(d.getId(), d.getName()))
                .collect(Collectors.toList());
    }


    public DietDto deleteDiet(Long id) {
        if (!dietRepository.existsById(id)) {
            throw new RuntimeException("Diet not found " + id);
        }
        dietRepository.deleteById(id);
        return null;
    }

    public DietDto getDietByName(String name){
        Diet diets = dietRepository.getByName(name);

        return new DietDto(diets.getId(), diets.getName());


    }
}
