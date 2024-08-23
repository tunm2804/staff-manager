package com.example.staffmanager.service.impl;

import com.example.staffmanager.dto.DataRequest;
import com.example.staffmanager.model.*;
import com.example.staffmanager.repository.*;
import com.example.staffmanager.service.DepartmentMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentMajorServiceImpl implements DepartmentMajorService {
    final StaffMajorFacilityRepository staffMajorFacilityRepository;

    final FacilityRepository facilityRepository;

    final DepartmentRepository departmentRepository;

    final MajorRepository majorRepository;

    final StaffRepository staffRepository;

    final DepartmentFacilityRepository departmentFacilityRepository;

    final MajorFacilityRepository majorFacilityRepository;

    @Override
    public List<StaffMajorFacility> getInfoByStaff(String idStaff) {
        return this.staffMajorFacilityRepository.getByIdStaff(idStaff);
    }

    @Override
    public List<Department> getDepartment() {
        return this.departmentRepository.findAll();
    }

    @Override
    public List<Facility> getFacilityByStaff(String idStaff) {
        List<Facility> temp = this.facilityRepository.findAll();
        getInfoByStaff(idStaff).forEach(i -> {
            temp.removeIf(department -> department.getName().equals(i.getIdMajorFacility().getIdDepartmentFacility().getIdFacility().getName()));
        });
        return temp;
    }

    @Override
    public List<Facility> getFacilityByStaff() {
        return this.facilityRepository.findAll();
    }

    @Override
    public List<Major> getMajors() {
        return this.majorRepository.findAll();
    }

    @Override
    @Transactional
    public void storeData(DataRequest request) {
        Optional<Facility> facility = this.facilityRepository.findById(request.getFacility());
        Optional<Department> department = this.departmentRepository.findById(request.getDepartment());
        Optional<Major> major = this.majorRepository.findById(request.getMajor());
        Optional<Staff> staff = this.staffRepository.findById(request.getIdStaff());
        try {
            if (facility.isPresent() && department.isPresent() && major.isPresent() && staff.isPresent()) {
                DepartmentFacility departmentFacility = this.departmentFacilityRepository.save(DepartmentFacility.builder()
                        .idFacility(facility.get())
                        .idFacility(facility.get())
                        .idStaff(staff.get())
                        .idDepartment(department.get())
                        .build());
                MajorFacility majorFacility = this.majorFacilityRepository.save(MajorFacility.builder()
                        .idDepartmentFacility(departmentFacility)
                        .idMajor(major.get())
                        .build());
                this.staffMajorFacilityRepository.save(StaffMajorFacility.builder()
                        .idMajorFacility(majorFacility)
                        .idStaff(staff.get())
                        .build());
            } else {
                throw new IllegalStateException("Invalid data");
            }
        } catch (Exception ignored) {
            //TODO
        }
    }

    @Override
    @Transactional
    public void deleteData(String idStaffMajorFacility) {
        Optional<StaffMajorFacility> staffMajorFacility = this.staffMajorFacilityRepository.findById(idStaffMajorFacility);
        if (staffMajorFacility.isPresent()) {
            this.staffMajorFacilityRepository.delete(staffMajorFacility.get());
            this.majorFacilityRepository.delete(staffMajorFacility.get().getIdMajorFacility());
            this.departmentFacilityRepository.delete(staffMajorFacility.get().getIdMajorFacility().getIdDepartmentFacility());
        }
    }
}
