package com.example.staffmanager.service.impl;

import com.example.staffmanager.model.Staff;
import com.example.staffmanager.model.StaffMajorFacility;
import com.example.staffmanager.repository.StaffMajorFacilityRepository;
import com.example.staffmanager.repository.StaffRepository;
import com.example.staffmanager.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    final StaffRepository staffRepository;

    final StaffMajorFacilityRepository staffMajorFacilityRepository;

    @Override
    public List<?> getAllEmp() {
        return this.staffRepository.findAll();
    }

    @Override
    @Transactional
    public void updateStatus(String id) {
        try {
            Staff staff = this.staffRepository.getReferenceById(id);
            this.staffRepository.updateStatus(staff.getId(), staff.getStatus() == 0 ? 1 : 0);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Staff> getStaffById(String id) {
        return this.staffRepository.findById(id);
    }

    @Override
    public Staff saveEmp(Staff staff) {
        return this.staffRepository.save(staff);
    }

    @Override
    public boolean existsByAccountFe(String accountFe) {
        return this.staffRepository.existsByAccountFe(accountFe);
    }

    @Override
    public boolean existsByAccountFpt(String accountFpt) {
        return this.staffRepository.existsByAccountFpt(accountFpt);
    }

    @Override
    public boolean existsByStaffCode(String staffCode) {
        return this.staffRepository.existsByStaffCode(staffCode);
    }
}
