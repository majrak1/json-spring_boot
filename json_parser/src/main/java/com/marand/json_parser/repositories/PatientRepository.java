package com.marand.json_parser.repositories;

import com.marand.json_parser.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
