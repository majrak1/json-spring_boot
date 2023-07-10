package com.marand.json_parser.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "document_report")
public class DocumentReport {

    @Id
    @Column(name = "id_time", unique = true, nullable = false)
    private String execution_time;

    @Column(name = "errors")
    private String errors;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_doctor", nullable = false)
    private Doctor doctor;

}
