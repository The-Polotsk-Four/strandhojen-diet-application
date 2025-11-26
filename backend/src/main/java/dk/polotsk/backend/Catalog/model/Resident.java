package dk.polotsk.backend.Catalog.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.prefs.Preferences;

@Entity
public class Resident {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private FoodConsisatency foodConsistency;

    private LocalDate age;
    private double weight;
    private double height;
    private double bmi;
    @OneToMany
    private List<Preference> preference;
    @OneToMany
    private List<Diet> diet;
    @OneToMany
    private List<Allergies> allergy;
    private int floor;
    private int roomNumber;
    private boolean status;
    private String comment;


    public Resident() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodConsisatency getFoodConsistency() {
        return foodConsistency;
    }

    public void setFoodConsistency(FoodConsisatency foodConsistency) {
        this.foodConsistency = foodConsistency;
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
        this.allergy.add(allergies);
    }

    public void removeAllergy(Allergies allergies){
        this.allergy.remove(allergies);
    }

    public List<Allergies> getAllergy() {
        return allergy;
    }

    public void setAllergy(List<Allergies> allergy) {
        this.allergy = allergy;
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
