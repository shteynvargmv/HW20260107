package com.example.demo.repository;

import com.example.demo.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionRepository extends JpaRepository<Profession,Long> {
    List<Profession> findByNameEquals(String name);
    Profession findFirstByNameEquals(String name);
}
