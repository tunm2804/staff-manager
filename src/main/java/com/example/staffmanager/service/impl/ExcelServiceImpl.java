package com.example.staffmanager.service.impl;

import com.example.staffmanager.dto.ImportExcelHistory;
import com.example.staffmanager.dto.StaffData;
import com.example.staffmanager.model.*;
import com.example.staffmanager.repository.*;
import com.example.staffmanager.service.EmployeeService;
import com.example.staffmanager.service.ExcelService;
import com.example.staffmanager.utils.FileUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    final Validator validator;

    final StaffMajorFacilityRepository staffMajorFacilityRepository;

    final FacilityRepository facilityRepository;

    final DepartmentRepository departmentRepository;

    final MajorRepository majorRepository;

    final EmployeeService employeeService;

    final DepartmentFacilityRepository departmentFacilityRepository;

    final MajorFacilityRepository majorFacilityRepository;

    @Value("${excel.prefix.historyImport}")
    private String path;

    @Override
    public void save(List<StaffData> staffData) {
        staffData.forEach(item -> {
            Facility facility = handleFacility(subString(item.getDepartmentMajor(), 0));
            Department department = handleDepartment(subString(item.getDepartmentMajor(), 1));
            Major major = handleMajor(subString(item.getDepartmentMajor(), 2));
            Staff staff = Staff.builder()
                    .accountFe(item.getEmailFE())
                    .accountFpt(item.getEmailFPT())
                    .name(item.getStaffName())
                    .staffCode(item.getStaffCode())
                    .build();
            saveDepartmentAndMajor(this.saveAndValid(staff), facility, department, major);
        });
    }

    @Override
    public Facility handleFacility(String facility) {
        return this.facilityRepository.findByName(facility.trim()).orElseThrow(() -> new EntityNotFoundException("Facility not found with name: " + facility));
    }

    @Override
    public Department handleDepartment(String department) {
        return this.departmentRepository.findByName(department.trim()).orElseThrow(() -> new EntityNotFoundException("Department not found with name: " + department));
    }

    @Override
    public Major handleMajor(String major) {
        return this.majorRepository.findByName(major.trim()).orElseThrow(() -> new EntityNotFoundException("Major not found with name: " + major));
    }

    @Override
    public String subString(String staffData, int index) {
        return staffData.split("\\s*-\\s*")[index];
    }

    @Override
    public Staff saveAndValid(Staff staff) {
        List<ImportExcelHistory> histories = new ArrayList<>();
        Set<ConstraintViolation<Staff>> violations = validator.validate(staff);
        if (employeeService.existsByAccountFe(staff.getAccountFe())) {
            histories.add(ImportExcelHistory.builder()
                    .createDate(new Date())
                    .description(String.format("Email FE: %s - Lỗi: Email đã tồn tại", staff.getAccountFe()))
                    .build());
        }
        if (employeeService.existsByAccountFpt(staff.getAccountFpt())) {
            histories.add(ImportExcelHistory.builder()
                    .createDate(new Date())
                    .description(String.format("Email FPT: %s - Lỗi: Email đã tồn tại", staff.getAccountFpt()))
                    .build());
        }
        if (employeeService.existsByStaffCode(staff.getStaffCode())) {
            histories.add(ImportExcelHistory.builder()
                    .createDate(new Date())
                    .description(String.format("Mã nhân viên: %s - Lỗi: Mã nhân viên đã tồn tại", staff.getAccountFpt()))
                    .build());
        }
        for (ConstraintViolation<Staff> violation : violations) {
            histories.add(ImportExcelHistory.builder()
                    .createDate(new Date())
                    .description(String.format("Lỗi xác thực: %s - %s", violation.getPropertyPath(), violation.getMessage()))
                    .build());
        }
        if (!histories.isEmpty()) {
            // Handle save to history
            FileUtil.saveHistoriesToFile(histories, this.path);
            return null;
        }
        histories.add(ImportExcelHistory.builder()
                .createDate(new Date())
                .description(String.format("Thêm thành công nhân viên: %s ", staff.getAccountFpt()))
                .build());
        // Handle save to history
        FileUtil.saveHistoriesToFile(histories, this.path);
        return this.employeeService.saveEmp(staff);
    }

    @Override
    public void saveDepartmentAndMajor(Staff staff, Facility facility, Department department, Major major) {
        DepartmentFacility departmentFacility = this.departmentFacilityRepository.save(DepartmentFacility.builder()
                .idFacility(facility)
                .idFacility(facility)
                .idStaff(staff)
                .idDepartment(department)
                .build());
        MajorFacility majorFacility = this.majorFacilityRepository.save(MajorFacility.builder()
                .idDepartmentFacility(departmentFacility)
                .idMajor(major)
                .build());
        this.staffMajorFacilityRepository.save(StaffMajorFacility.builder()
                .idMajorFacility(majorFacility)
                .idStaff(staff)
                .build());
    }
}
