package com.gautam.clinicals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.clinicals.dto.ClinicalDataRequest;
import com.gautam.clinicals.model.ClinicalData;
import com.gautam.clinicals.model.Patient;
import com.gautam.clinicals.repos.ClinicalDataRepository;
import com.gautam.clinicals.repos.PatientRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {
	
	private ClinicalDataRepository crepo;
	private PatientRepository prepo;
	
	@Autowired
	public ClinicalDataController(ClinicalDataRepository crepo,PatientRepository prepo){
		this.crepo = crepo;
		this.prepo = prepo;		
	}

	@RequestMapping(value ="/clinicals", method = RequestMethod.POST)
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request) {
		
		Patient patient = prepo.findById(request.getPatientId()).get();
		
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		clinicalData.setPatient(patient);
		
		return crepo.save(clinicalData);
		
	}
}
