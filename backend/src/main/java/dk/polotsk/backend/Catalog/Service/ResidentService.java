package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.ResidentDto;
import dk.polotsk.backend.Catalog.dto.UserDto;
import dk.polotsk.backend.Catalog.exception.NotFoundException;
import dk.polotsk.backend.Catalog.mapper.Mapper;
import dk.polotsk.backend.Catalog.model.Resident;
import dk.polotsk.backend.Catalog.model.User;
import dk.polotsk.backend.Catalog.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final Mapper mapper;

    public ResidentService(ResidentRepository residentRepository, Mapper mapper) {
        this.residentRepository = residentRepository;
        this.mapper = mapper;
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


    public ResidentDto getResident(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resident not found with id: " + id));

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
            throw new NotFoundException("Resident not found with id: " + id);
        }
        residentRepository.deleteById(id);
    }

    public List<ResidentDto> findResidentByName(String name){
        List<Resident> residents = residentRepository.findResidentByName(name);
        if (residents.isEmpty()){
            throw new RuntimeException("Can't find resident with name: "+name);
        }

        List<ResidentDto> residentDtos = new ArrayList<>();
        for (Resident resident : residents){
            residentDtos.add(Mapper.toDto(resident));
        }
        return residentDtos;
    }

//    public List<UserDto> getByUserLogin(String login){
//        List<User> users = userRepository.findUserByLogin(login);
//        if (users.isEmpty()){
//            throw new RuntimeException("Can't find user with login "+login);
//        }
//        List<UserDto> userDtos = new ArrayList<>();
//        for (User user : users){
//            userDtos.add(Mapper.toDto(user));
//        }
//        return userDtos;


}
