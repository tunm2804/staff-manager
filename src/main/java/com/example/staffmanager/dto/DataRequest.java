package com.example.staffmanager.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataRequest {
    private String idStaff;
    private String facility;
    private String department;
    private String major;
}
