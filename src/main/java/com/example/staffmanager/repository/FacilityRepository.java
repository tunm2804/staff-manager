package com.example.staffmanager.repository;

import com.example.staffmanager.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {
    Optional<Facility> findByName(String facility);
}
