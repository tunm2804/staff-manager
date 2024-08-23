package com.example.staffmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "department_facility")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentFacility extends AbstractEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_department")
    private Department idDepartment;//Required

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_staff")
    private Staff idStaff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_facility")
    private Facility idFacility;//Required
}