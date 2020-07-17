package com.gautam.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gautam.clinicals.model.ClinicalData;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Integer> {

}
