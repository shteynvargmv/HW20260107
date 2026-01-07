package com.example.demo.dto;

import com.example.demo.entity.Person;
import com.example.demo.entity.Profession;
import jakarta.validation.Valid;

public class PersonProfessionDto {
    @Valid
    private Person person;
    @Valid
    private Profession profession;

    public PersonProfessionDto() {
        this.person = new Person();
        this.profession = new Profession();
    }

    public PersonProfessionDto(Person person, Profession profession) {
        this.person = person;
        this.profession = profession;
    }

    public Person getPerson() {
        return person;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }


}
