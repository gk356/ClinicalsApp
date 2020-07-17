package com.gautam.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gautam.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
