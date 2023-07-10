package com.marand.json_parser.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

// import javax.persistence.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "patients")
public class Patient {

    @Id
    @Column(name = "id_patient", unique = true, nullable = false)
    private String patientId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "disease")
    private String disease;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_doctor", nullable = false)
    private Doctor doctor;
}
