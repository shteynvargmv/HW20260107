package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Профессия не должна быть пустой")
    private String name;

    @OneToMany(mappedBy = "profession")
    private Set<Person> persons = new HashSet<>();

    public Profession() {
    }

    public Profession(String name) {
        this.name = name;
    }

    public Profession(String name, Set<Person> persons) {
        this.name = name;
        this.persons = persons;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Profession info: " + id + " " + name;
    }
}
