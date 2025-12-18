package dk.polotsk.backend.Catalog.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

@Entity
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private LocalDate age;
    private double weight;
    private double height;
    private double bmi;

    @Enumerated(EnumType.STRING)
    private FoodConsistency foodconsistency = FoodConsistency.SOLID;

    @ManyToMany
    private List<Preference> preference = new ArrayList<>();
    @ManyToMany
    private List<Diet> diet = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "resident_allergy",
            joinColumns = @JoinColumn(name = "resident_id"),
            inverseJoinColumns = @JoinColumn(name = "allergy_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"resident_id", "allergy_id"})
    )
    private List<Allergies> allergies = new ArrayList<>();
    private int floor;
    private int roomNumber;
    private boolean status = true;
    private String comment;


    public Resident() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodConsistency getFoodConsistency() {
        return foodconsistency;
    }

    public void setFoodConsistency(FoodConsistency foodConsistency) {
        this.foodconsistency = foodConsistency;
    }

    public LocalDate getAge() {
        return age;
    }

    public void setAge(LocalDate age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double calculateBmi() {
        // calculates the bmi using the equation weight/height^2
        // height in our system is in cm, equation needs m, so it's divided by 100
        this.bmi = this.weight/(Math.pow((this.height/100), 2));
        return this.bmi;
    }

    public void addPreference(Preference preference){
        this.preference.add(preference);
    }

    public void removePreference(Preference preference){
        this.preference.remove(preference);
    }

    public List<Preference> getPreference() {
        return preference;
    }

    public void setPreference(List<Preference> preference) {
        this.preference = preference;
    }

    public void addDiet(Diet diet){
        this.diet.add(diet);
    }

    public void removeDiet(Diet diet){
        this.diet.remove(diet);
    }

    public List<Diet> getDiet() {
        return diet;
    }

    public void setDiet(List<Diet> diet) {
        this.diet = diet;
    }

    public void addAllergy(Allergies allergies){
        this.allergies.add(allergies);
    }

    public void removeAllergy(Allergies allergies){
        this.allergies.remove(allergies);
    }

    public List<Allergies> getAllergy() {
        return allergies;
    }

    public void setAllergy(List<Allergies> allergy) {
        this.allergies = allergy;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
