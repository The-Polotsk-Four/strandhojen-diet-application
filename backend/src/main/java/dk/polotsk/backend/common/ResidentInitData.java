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
//        resident01.addAllergy(nuts);
        resident01.setFloor(1);
        resident01.setRoomNumber(101);
        residentRepository.save(resident01);

        Resident resident02 = new Resident();
        resident02.setName("Line");
        resident02.setAge(LocalDate.parse("1949-04-20"));
        resident02.setWeight(55.00);
        resident02.setHeight(157.00);
        resident02.calculateBmi();
//        resident02.addDiet(vegetarian);
        resident02.setFloor(2);
        resident02.setRoomNumber(201);
        residentRepository.save(resident02);

        Resident resident03 = new Resident();
        resident03.setName("Lucas");
        resident03.setAge(LocalDate.parse("1942-06-06"));
        resident03.setWeight(82.00);
        resident03.setHeight(182.00);
        resident03.calculateBmi();
//        resident02.addDiet(vegetarian);
        resident03.setFloor(0);
        resident03.setRoomNumber(001);
        residentRepository.save(resident03);

        Resident resident04 = new Resident();
        resident04.setName("Laura");
        resident04.setAge(LocalDate.parse("1945-02-12"));
        resident04.setWeight(76.00);
        resident04.setHeight(179.00);
        resident04.calculateBmi();
//        resident04.addPreference(tomato);
        resident04.setFloor(2);
        resident04.setRoomNumber(202);
        residentRepository.save(resident04);

        Resident resident05 = new Resident();
        resident05.setName("Luka");
        resident05.setAge(LocalDate.parse("1939-03-03"));
        resident05.setWeight(80.00);
        resident05.setHeight(178.00);
        resident05.calculateBmi();
//        resident02.addAllergy(gluten);
//        resident02.addAllergy(lactose);
//        resident02.addDiet(vegetarian);
//        resident04.addPreference(banana);
        resident05.setFloor(2);
        resident05.setRoomNumber(203);
        residentRepository.save(resident05);

        Resident resident06 = new Resident();
        resident06.setName("Luna");
        resident06.setFoodConsistency(FoodConsistency.SOFTFOOD);
        resident06.setAge(LocalDate.parse("1935-06-08"));
        resident06.setWeight(99.00);
        resident06.setHeight(169.00);
        resident06.calculateBmi();
        resident06.setFloor(1);
        resident06.setRoomNumber(102);
        residentRepository.save(resident06);

        Resident resident07 = new Resident();
        resident07.setName("Lily");
        resident07.setAge(LocalDate.parse("1947-02-02"));
        resident07.setWeight(42.00);
        resident07.setHeight(150.00);
        resident07.calculateBmi();
        resident07.setFloor(3);
        resident07.setRoomNumber(302);
        residentRepository.save(resident07);

        Resident resident08 = new Resident();
        resident08.setName("Louie");
        resident06.setFoodConsistency(FoodConsistency.SOFTFOOD);
        resident08.setAge(LocalDate.parse("1951-05-21"));
        resident08.setWeight(55.00);
        resident08.setHeight(155.00);
        resident08.calculateBmi();
        resident08.setFloor(1);
        resident08.setRoomNumber(104);
        residentRepository.save(resident08);

        Resident resident09 = new Resident();
        resident09.setName("Liam");
        resident09.setAge(LocalDate.parse("1932-08-30"));
        resident09.setWeight(78.00);
        resident09.setHeight(178.00);
        resident09.calculateBmi();
        resident09.setFloor(1);
        resident09.setRoomNumber(105);
        residentRepository.save(resident09);

        Resident resident10 = new Resident();
        resident10.setName("Lauge");
        resident10.setAge(LocalDate.parse("1928-11-01"));
        resident10.setWeight(99.00);
        resident10.setHeight(190.00);
        resident10.calculateBmi();
        resident10.setFloor(2);
        resident06.setFoodConsistency(FoodConsistency.TUBEFEEDING);
        resident10.setRoomNumber(203);
        residentRepository.save(resident10);

        Resident resident11 = new Resident();
        resident11.setName("Lærke");
        resident11.setAge(LocalDate.parse("1933-03-09"));
        resident11.setWeight(76.00);
        resident11.setHeight(188.00);
        resident11.calculateBmi();
        resident11.setFloor(3);
        resident11.setRoomNumber(303);
        residentRepository.save(resident09);

    }
}
