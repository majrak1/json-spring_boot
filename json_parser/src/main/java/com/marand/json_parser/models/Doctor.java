package com.marand.json_parser.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "doctors")
public class Doctor {

    @Id
    @Column(name = "id_doctor", unique = true, nullable = false)
    private String doctorId;

    @Column(name = "department")
    private String department;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Patient> patients;
}
