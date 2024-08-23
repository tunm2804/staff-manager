package com.example.staffmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@Builder
@ToString
public class ImportExcelHistory {
    private Date createDate;
    private String description;
}
