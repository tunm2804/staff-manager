package com.example.staffmanager.repository;

import com.example.staffmanager.model.StaffMajorFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StaffMajorFacilityRepository extends JpaRepository<StaffMajorFacility, String> {
    @Query("FROM StaffMajorFacility WHERE idStaff.id = :id")
    List<StaffMajorFacility> getByIdStaff(@Param("id") String id);
}
