package com.example.staffmanager.repository;

import com.example.staffmanager.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, String> {
    Optional<Major> findByName(String major);
}
