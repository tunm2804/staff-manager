package com.example.staffmanager.controller;

import com.example.staffmanager.dto.DataRequest;
import com.example.staffmanager.service.EmployeeService;
import com.example.staffmanager.service.DepartmentMajorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/department-major")
@RequiredArgsConstructor
@Slf4j
public class DepartmentMajorController {
    final DepartmentMajorService departmentMajorService;
    final EmployeeService employeeService;

    void setup(Model model, String id) {
        model.addAttribute("listFacility", this.departmentMajorService.getFacilityByStaff(id));
        model.addAttribute("listDepartments", this.departmentMajorService.getDepartment());
        model.addAttribute("listMajors", this.departmentMajorService.getMajors());
        model.addAttribute("info", this.departmentMajorService.getInfoByStaff(id.trim()));
        model.addAttribute("staff", this.employeeService.getStaffById(id));
        model.addAttribute("idStaff", id);
    }

    @GetMapping("/{id}")
    public String major(Model model, @PathVariable String id) {
        setup(model, id);
        model.addAttribute("objectCustom", new DataRequest());
        return "component/department_major/department_major";
    }

    @PostMapping("/add/{id}")
    public String addMajor(@ModelAttribute("objectCustom") DataRequest dataRequest, @PathVariable String id) {
        dataRequest.setIdStaff(id);
        this.departmentMajorService.storeData(dataRequest);
        return "redirect:/department-major/" + id;
    }

    @GetMapping("/delete/{id}/{idStaffMajorFacility}")
    public String deleteMajor(@PathVariable String id, @PathVariable String idStaffMajorFacility) {
        this.departmentMajorService.deleteData(idStaffMajorFacility);
        return "redirect:/department-major/" + id;
    }
}
