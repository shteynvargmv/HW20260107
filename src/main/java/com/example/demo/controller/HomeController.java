package com.example.demo.controller;

import com.example.demo.dto.PersonProfessionDto;
import com.example.demo.dto.PersonProfessionListDto;
import com.example.demo.entity.Person;
import com.example.demo.entity.Profession;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProfessionRepository;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private Faker fakerData;
    @Autowired
    private ProfessionRepository professionRepository;

    @GetMapping("/people")
    public String getAllPage(@RequestParam(defaultValue = "1") Integer page, Model model) {
        List<PersonProfessionDto> dtos = new ArrayList<>();
        List<Person> people = personRepository.findAll();
        if (!people.isEmpty()) {
            dtos = people.stream().map(person -> {
                Optional<Profession> optProfession = professionRepository.findById(person.getProfession().getId());
                if (optProfession.isPresent()) {
                    return new PersonProfessionDto(person, optProfession.get());
                } else {
                    return new PersonProfessionDto(person, new Profession());
                }
            }).toList();
        }
        Integer pagesCount = (int) Math.ceil((double) dtos.size() / PersonProfessionListDto.PERSONS_ON_PAGE);
        int from = (page - 1) * PersonProfessionListDto.PERSONS_ON_PAGE;
        int to = from + PersonProfessionListDto.PERSONS_ON_PAGE;
        if (page.equals(pagesCount)){
            to = dtos.size();
        }
        model.addAttribute("dtos", new PersonProfessionListDto(dtos.subList(from,to), pagesCount, page));
        model.addAttribute("pageName", "Home page");
        return "home";
    }

    @GetMapping("/addAll")
    public String addAll(Model model) {
        generationData(30);
        return "redirect:/home/people";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteByIdAjax(@PathVariable Long id) {
        Optional<Person> optPerson = personRepository.findById(id);
        if (optPerson.isPresent()) {
            Person person = optPerson.get();
            Profession profession = person.getProfession();
            personRepository.delete(person);
            String result = "Person deleted => " + person;
            result += deleteProfessionIfNotUsed(profession.getId());
            return result;
        } else {
            return "User not found => " + id;
        }
    }

    private String deleteProfessionIfNotUsed(Long id) {
        Optional<Profession> optProfession = professionRepository.findById(id);
        if (optProfession.isPresent()) {
            Profession profession = optProfession.get();
            List<Person> persons = personRepository.findByProfessionId(profession.getId());
            System.out.println("Количество ");
            System.out.println(persons.size() + " id ");
            persons.stream().forEach(person -> {
                System.out.println(" " + person.getId());
            });
            if (persons == null || persons.isEmpty()) {
                professionRepository.delete(profession);
                return "\nProfession deleted => " + profession;
            }
        }
        return "";
    }

//    @GetMapping("/delete/{id}")
//    public String deleteById(@PathVariable Long id, Model model) {
//        personRepository.deleteById(id);
//        List<Person> people = personRepository.findAll();
//        model.addAttribute("people", people);
//        model.addAttribute("pageName", "Home page");
//
//        return "home";
//    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("pageName", "Create page");
        model.addAttribute("dto", new PersonProfessionDto());
        return "create";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Person> optPerson = personRepository.findById(id);
        if (optPerson.isPresent()) {
            Person person = optPerson.get();
            Optional<Profession> optProfession = professionRepository.findById(person.getProfession().getId());
            if (optProfession.isPresent()) {
                Profession profession = optProfession.get();
                model.addAttribute("dto", new PersonProfessionDto(person, profession));
            }
        }
        model.addAttribute("pageName", "Update page");
        return "edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid PersonProfessionDto dto, BindingResult bindir, Model model) {
        if (!bindir.hasErrors()) {
            Person person = dto.getPerson();
            Profession profession = dto.getProfession();
            Long oldProfessionId = profession.getId();
            Profession existed = professionRepository.findFirstByNameEquals(profession.getName());
            if (existed == null) {
                profession.setId(null);
                existed = professionRepository.save(profession);
            }
            person.setProfession(existed);
            personRepository.save(person);
            if (!existed.getId().equals(oldProfessionId)) {
                deleteProfessionIfNotUsed(oldProfessionId);
            }
            return "redirect:/home/people";
        } else {
            model.addAttribute("org.springframework.validation.BindingResult.dto", bindir);
            model.addAttribute("dto", dto);
            return "edit";
        }
    }

    @PostMapping("/create")
    public String add(@Valid PersonProfessionDto dto, BindingResult bindir, Model model) {

        if (bindir.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.dto", bindir);
            model.addAttribute("dto", dto);
            return "create";
        } else {
            Profession profession = professionRepository.findFirstByNameEquals(dto.getProfession().getName());
            if (profession == null) {
                profession = professionRepository.save(dto.getProfession());
            }
            dto.getPerson().setProfession(profession);
            Person person = personRepository.save(dto.getPerson());
        }
        return "redirect:/home/people";
    }

    @GetMapping("/clear")
    public String clear(Model model) {
        personRepository.deleteAll();
        professionRepository.deleteAll();
        return "redirect:/home/people";
    }


    private void generationData(int count) {
        if (personRepository.count() == 0) {
            for (int i = 0; i < count; i++) {
                String name = fakerData.name().name();
                String firstName = fakerData.name().firstName();
                int age = fakerData.number().numberBetween(18, 100);
                String email = fakerData.internet().emailAddress();
                Person person = new Person(name, firstName, email, age);
                String professionName = fakerData.job().title();
                Profession profession = professionRepository.findFirstByNameEquals(professionName);
                if (profession == null) {
                    profession = professionRepository.save(new Profession(professionName));
                }
                person.setProfession(profession);
                personRepository.save(person);
            }
        }
    }
}
