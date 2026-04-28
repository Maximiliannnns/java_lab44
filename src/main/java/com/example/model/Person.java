package com.example.model;

import java.time.LocalDate;

public class Person 
{
    private int id;
    private String name;
    private String gender;
    private Division division;
    private double salary;
    private LocalDate birthDate;

    public Person(int id, String name, String gender, Division division, 
                  double salary, LocalDate birthDate) 
    {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.division = division;
        this.salary = salary;
        this.birthDate = birthDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Division getDivision() { return division; }
    public void setDivision(Division division) { this.division = division; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    @Override
    public String toString() 
    {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", division=" + division +
                ", salary=" + salary +
                ", birthDate=" + birthDate +
                '}';
    }
}