package com.example.JDK.models;

public class Passenger {

    private String id;
    private String name;
    private String surname;
    private int age;
    private String gender;
    private String familyIde;
    private String civilStatus;
    private int children;
    private int tripAchieved;
    private double salary;

    // Constructor
    public Passenger(String id, String name, String surname, int age, String gender, String familyIde,
                  String civilStatus, int children, int tripAchieved, double salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.familyIde = familyIde;
        this.civilStatus = civilStatus;
        this.children = children;
        this.tripAchieved = tripAchieved;
        this.salary = salary;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getfamilyIde() {
        return familyIde;
    }

    public void setfamilyIde(String family) {
        this.familyIde = familyIde;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int isTripAchieved() {
        return tripAchieved;
    }

    public void setTripAchieved(int tripAchieved) {
        this.tripAchieved = tripAchieved;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Sobrescribir el método toString()
    @Override
    public String toString() {
        return "Person{" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", Surname='" + surname + '\'' +
                ", Age=" + age +
                ", Gender='" + gender + '\'' +
                ", Family='" + familyIde + '\'' +
                ", Civil Status='" + civilStatus + '\'' +
                ", Children=" + children +
                ", Trip Achieved=" + tripAchieved + '\'' +
                ", Salary=$" + salary +
                '}';
    }
}


