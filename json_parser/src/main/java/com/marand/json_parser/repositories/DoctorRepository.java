package com.marand.json_parser.repositories;

import com.marand.json_parser.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
