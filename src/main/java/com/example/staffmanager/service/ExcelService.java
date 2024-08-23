package com.example.staffmanager.service;

import com.example.staffmanager.dto.StaffData;
import com.example.staffmanager.model.Department;
import com.example.staffmanager.model.Facility;
import com.example.staffmanager.model.Major;
import com.example.staffmanager.model.Staff;

import java.util.List;

public interface ExcelService {
    void save(List<StaffData> staffData);

    Facility handleFacility(String facility);

    Department handleDepartment(String department);

    Major handleMajor(String major);

    String subString(String staffData, int index);

    Staff saveAndValid(Staff staff);

    void saveDepartmentAndMajor(Staff staff, Facility facility, Department department, Major major);
}
