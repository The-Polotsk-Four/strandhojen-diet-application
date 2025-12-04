package dk.polotsk.backend.common;

import dk.polotsk.backend.Catalog.model.*;
import dk.polotsk.backend.Catalog.repository.ResidentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResidentInitData implements CommandLineRunner {

    private final ResidentRepository residentRepository;

    public ResidentInitData(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Preference> preferenceList = new ArrayList<>();
        Preference tomato = new Preference();
//        tomato.setId(1L);
        tomato.setName("tomat");
        preferenceList.add(tomato);
        Preference cheese = new Preference();
//        cheese.setId(2L);
        cheese.setName("ost");
        preferenceList.add(cheese);
        Preference banana = new Preference();
//        banana.setId(3L);
        banana.setName("banan");
        preferenceList.add(banana);
        Preference fish = new Preference();
//        fish.setId(4L);
        fish.setName("fisk");
        preferenceList.add(fish);

        List<Diet> dietList = new ArrayList<>();
        Diet halal = new Diet();
//        halal.setId(1L);
        halal.setName("halal");
        dietList.add(halal);
        Diet vegetarian = new Diet();
        vegetarian.setId(2L);
        vegetarian.setName("vegetar");
        dietList.add(vegetarian);

        List<Allergies> allergiesList = new ArrayList<>();
        Allergies nuts = new Allergies();
//        nuts.setId(1L);
        nuts.setName("nødder");
        allergiesList.add(nuts);
        Allergies lactose = new Allergies();
//        lactose.setId(2L);
        lactose.setName("laktose");
        allergiesList.add(lactose);
        Allergies gluten = new Allergies();
//        gluten.setId(3L);
        gluten.setName("gluten");
        allergiesList.add(gluten);
        Allergies shellfish = new Allergies();
//        shellfish.setId(4L);
        shellfish.setName("skaldyr");
        allergiesList.add(shellfish);

        Resident resident01 = new Resident();
        resident01.setName("Lars");
        resident01.setAge(LocalDate.parse("1956-12-02"));
        resident01.setWeight(69.00);
        resident01.setHeight(169.00);
        resident01.calculateBmi();
//        resident01.setFoodConsistency(FoodConsistency.SOLID);
//        resident01.addAllergy(nuts);
        resident01.setFloor(1);
        resident01.setRoomNumber(101);
//        resident01.setStatus(true);
        residentRepository.save(resident01);

        Resident resident02 = new Resident();
        resident02.setName("Line");
        resident02.setAge(LocalDate.parse("1949-04-20"));
        resident02.setWeight(55.00);
        resident02.setHeight(157.00);
        resident02.calculateBmi();
//        resident02.setFoodConsistency(FoodConsistency.SOLID);
//        resident02.addAllergy(nuts);
//        resident02.addDiet(vegetarian);
        resident02.setFloor(2);
        resident02.setRoomNumber(201);
//        resident02.setStatus(true);
        residentRepository.save(resident02);

        Resident resident03 = new Resident();
        resident03.setName("Lucas");
        resident03.setAge(LocalDate.parse("1942-06-06"));
        resident03.setWeight(82.00);
        resident03.setHeight(182.00);
        resident03.calculateBmi();
//        resident02.setFoodConsistency(FoodConsistency.SOLID);
//        resident02.addAllergy(nuts);
//        resident02.addDiet(vegetarian);
        resident03.setFloor(3);
        resident03.setRoomNumber(301);
//        resident02.setStatus(true);
        residentRepository.save(resident03);

        Resident resident04 = new Resident();
        resident04.setName("Laura");
        resident04.setAge(LocalDate.parse("1945-02-12"));
        resident04.setWeight(76.00);
        resident04.setHeight(179.00);
        resident04.calculateBmi();
//        resident02.setFoodConsistency(FoodConsistency.SOLID);
//        resident02.addAllergy(nuts);
//        resident02.addDiet(vegetarian);
//        resident04.addPreference(tomato);
        resident04.setFloor(2);
        resident04.setRoomNumber(202);
//        resident02.setStatus(true);
        residentRepository.save(resident04);

        Resident resident05 = new Resident();
        resident04.setName("Luka");
        resident04.setAge(LocalDate.parse("1939-03-03"));
        resident04.setWeight(80.00);
        resident04.setHeight(178.00);
        resident04.calculateBmi();
//        resident02.setFoodConsistency(FoodConsistency.SOLID);
//        resident02.addAllergy(gluten);
//        resident02.addAllergy(lactose);
//        resident02.addDiet(vegetarian);
//        resident04.addPreference(banana);
        resident04.setFloor(2);
        resident04.setRoomNumber(202);
//        resident02.setStatus(true);
        residentRepository.save(resident05);

    }
}
