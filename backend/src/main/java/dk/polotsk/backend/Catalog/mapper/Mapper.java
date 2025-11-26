package dk.polotsk.backend.Catalog.mapper;

import dk.polotsk.backend.Catalog.dto.*;
import dk.polotsk.backend.Catalog.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    private static final Logger log = LoggerFactory.getLogger(Mapper.class);

    public static AllergiesDto toDto(Allergies allergies){
        return new AllergiesDto(allergies.getId(), allergies.getName());
    }
    public static DietDto toDto(Diet diet){
        return new DietDto((diet.getId()), diet.getName());
    }

    public static PreferenceDto toDto(Preference preference){
        return new PreferenceDto(preference.getId(), preference.getName());
    }

    public static ResidentDto toDto(Resident resident){
        List<AllergiesDto> allergies = new ArrayList<>();
        for (Allergies allergy : resident.getAllergy()){
            allergies.add(toDto(allergy));
        }
        List<DietDto> diets = new ArrayList<>();
        for (Diet diet : resident.getDiet()){
            diets.add(toDto(diet));
        }
        List<PreferenceDto> preferences = new ArrayList<>();
        for (Preference preference : resident.getPreference()){
            preferences.add(toDto(preference));
        }
        return new ResidentDto(resident.getId(),
                resident.getFoodConsistency(),
                resident.getAge(),
                resident.getWeight(),
                resident.getHeight(),
                resident.getBmi(),
                preferences,
                diets,
                allergies,
                resident.getFloor(),
                resident.getRoomNumber(),
                resident.isStatus(),
                resident.getComment());
    }
    public static UserDto toDto(User user){
        return new UserDto(user.getId(), user.getUserrole(), user.getName());
    }
    public static Allergies toEntity(AllergiesDto allergiesDto){
        Allergies allergies = new Allergies();
        allergies.setId(allergiesDto.id());
        return allergies;
    }
    public static Diet toEntity(DietDto dietDto){
        Diet diet = new Diet();
        diet.setId(dietDto.id());
        return diet;
    }
    public static Preference toEntity(PreferenceDto preferenceDto){
        Preference preference = new Preference();
        preference.setId(preferenceDto.id());
        return preference;
    }

    public static Resident toEntity(ResidentDto residentDto){
        Resident resident = new Resident();
        resident.setId(residentDto.id());
        resident.setFoodConsistency(residentDto.foodConsisatency());
        resident.setAge(residentDto.age());
        resident.setWeight(residentDto.weight());
        resident.setHeight(residentDto.height());
        resident.setBmi(residentDto.bmi());
        for (PreferenceDto preferenceDto : residentDto.preference()){
            resident.addPreference(toEntity(preferenceDto));
        }
        for (DietDto dietDto : residentDto.diet()){
            resident.addDiet(toEntity(dietDto));
        }
        for (AllergiesDto allergiesDto : residentDto.allergies()){
            resident.addAllergy(toEntity(allergiesDto));
        }
        resident.setFloor(residentDto.floor());
        resident.setRoomNumber(residentDto.floor());
        resident.setStatus(residentDto.status());
        resident.setComment(residentDto.comment());

        return resident;
    }


    public static User toEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.id());
        return user;
    }

}
