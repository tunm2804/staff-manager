package com.example.staffmanager.service;

import com.example.staffmanager.dto.DataRequest;
import com.example.staffmanager.model.Department;
import com.example.staffmanager.model.Facility;
import com.example.staffmanager.model.Major;
import com.example.staffmanager.model.StaffMajorFacility;

import java.util.List;

public interface DepartmentMajorService {
    List<StaffMajorFacility> getInfoByStaff(String idStaff);

    List<Department> getDepartment();

    List<Facility> getFacilityByStaff(String idStaff);
    List<Facility> getFacilityByStaff();

    List<Major> getMajors();

    void storeData(DataRequest request);

    void deleteData(String idStaffMajorFacility);
}
