package com.example.staffmanager.repository;

import com.example.staffmanager.model.DepartmentFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentFacilityRepository extends JpaRepository<DepartmentFacility, String> {
}
