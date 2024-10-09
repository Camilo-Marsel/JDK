package com.example.JDK.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Passenger {

    private String _id;
    private String name;
    private String surname;
    private int age;
    private String gender;
    private int familyID;
    private String civilStatus;
    private int children;
    private int tripsAchieved;
    private double salary;

    // Constructor por defecto (obligatorio para Jackson)
    public Passenger() {
    }

    // Constructor con todos los parámetros (opcional, pero útil)
    public Passenger(String _id, String name, String surname, int age, String gender, int familyID,
                     String civilStatus, int children, int tripsAchieved, double salary) {
        this._id = _id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.familyID = familyID;
        this.civilStatus = civilStatus;
        this.children = children;
        this.tripsAchieved = tripsAchieved;
        this.salary = salary;
    }

    // Getters y Setters

    @JsonProperty("_id")
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @JsonProperty("Age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @JsonProperty("Gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("FamilyID")
    public int getFamilyID() {
        return familyID;
    }

    public void setFamilyID(int familyID) {
        this.familyID = familyID;
    }

    @JsonProperty("CivilStatus")
    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    @JsonProperty("Children")
    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    @JsonProperty("TripsAchieved")
    public int getTripsAchieved() {
        return tripsAchieved;
    }

    public void setTripsAchieved(int tripsAchieved) {
        this.tripsAchieved = tripsAchieved;
    }

    @JsonProperty("Salary")
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return Integer.toString(this.familyID);  // Aquí puedes ajustar la representación que prefieras, por ejemplo, agregar apellido u otros campos.
    }
}


