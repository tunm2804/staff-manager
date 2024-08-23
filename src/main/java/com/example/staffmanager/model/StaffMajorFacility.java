package com.example.staffmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "staff_major_facility")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMajorFacility extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_major_facility")
    private MajorFacility idMajorFacility;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_staff")
    private Staff idStaff;
}