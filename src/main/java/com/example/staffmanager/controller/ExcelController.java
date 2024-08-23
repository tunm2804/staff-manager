package com.example.staffmanager.controller;

import com.example.staffmanager.dto.StaffData;
import com.example.staffmanager.model.Department;
import com.example.staffmanager.model.Facility;
import com.example.staffmanager.model.Major;
import com.example.staffmanager.service.DepartmentMajorService;
import com.example.staffmanager.service.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController {
    @Value("${excel.prefix.staffCode}")
    private String staffCode;
    @Value("${excel.prefix.name}")
    private String staffName;
    @Value("${excel.prefix.accountFPT}")
    private String accountFPT;
    @Value("${excel.prefix.accountFE}")
    private String accountFe;
    @Value("${excel.prefix.contentType}")
    private String contentType;

    final DepartmentMajorService departmentMajorService;

    final ExcelService excelService;

    @PostMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<StaffData> staffDataList = readExcelFile(file);
            // TODO Save to database
            this.excelService.save(staffDataList);
        } catch (IOException ignored) {
            // TODO Handle error
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/download-template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=template_%s.xlsx", staffCode));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Template");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
        headerRow.createCell(1).setCellValue("Mã Nhân Viên");
        headerRow.createCell(2).setCellValue("Họ và Tên");
        headerRow.createCell(3).setCellValue("Email FPT");
        headerRow.createCell(4).setCellValue("Email FE");
        headerRow.createCell(5).setCellValue("Bộ Môn - Chuyên Ngành");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("1");
        dataRow.createCell(1).setCellValue(staffCode);
        dataRow.createCell(2).setCellValue(staffName);
        dataRow.createCell(3).setCellValue(accountFPT);
        dataRow.createCell(4).setCellValue(accountFe);

        addValidationToCell(sheet, getDepartmentMajors());

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private List<StaffData> readExcelFile(MultipartFile file) throws IOException {
        List<StaffData> staffDataList = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                StaffData staffData = StaffData.builder()
                        .staffCode(row.getCell(1).getStringCellValue())
                        .staffName(row.getCell(2).getStringCellValue())
                        .emailFPT(row.getCell(3).getStringCellValue())
                        .emailFE(row.getCell(4).getStringCellValue())
                        .departmentMajor(row.getCell(5).getStringCellValue())
                        .build();
                staffDataList.add(staffData);
            }
        }
        workbook.close();
        return staffDataList;
    }

    private void addValidationToCell(Sheet sheet, List<String> options) {
        String[] optionsArray = options.toArray(new String[0]);
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(optionsArray);
        CellRangeAddressList addressList = new CellRangeAddressList(1, 1, 5, 5);
        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
        dataValidation.setShowErrorBox(true);
        sheet.addValidationData(dataValidation);
    }

    private List<String> getDepartmentMajors() {
        List<String> departmentMajors = new ArrayList<>();
        for (Facility f : this.departmentMajorService.getFacilityByStaff()) {
            for (Department d : this.departmentMajorService.getDepartment()) {
                for (Major m : this.departmentMajorService.getMajors()) {
                    departmentMajors.add(String.format("%s - %s - %s", f.getName(), d.getName(), m.getName()));
                    if (departmentMajors.size() >= 2) { //Handle error: A valid formula or a list of values must be less than or equal to 255 characters
                        break;
                    }
                }
            }
        }
        return departmentMajors;
    }
}
