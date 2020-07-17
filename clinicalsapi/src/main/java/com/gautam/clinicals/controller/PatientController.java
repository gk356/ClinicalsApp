package com.gautam.clinicals.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.clinicals.model.ClinicalData;
import com.gautam.clinicals.model.Patient;
import com.gautam.clinicals.repos.PatientRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {

	private PatientRepository prepo;
	Map<String, String> filters = new HashMap<>();

	@Autowired
	PatientController(PatientRepository prepo) {
		this.prepo = prepo;
	}

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public List<Patient> getPatients() {
		return prepo.findAll();
	}

	@RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
	public Patient getPatient(@PathVariable("id") int id) {
		return prepo.findById(id).get();
	}

	@RequestMapping(value = "/patients", method = RequestMethod.POST)
	public Patient savePatient(@RequestBody Patient patient) {
		return prepo.save(patient);
	}

	@RequestMapping(value = "/patients/analyze/{id}", method = RequestMethod.GET)
	public Patient analyze(@PathVariable("id") int id) {
		Patient patient = prepo.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();

		ArrayList<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);

		for (ClinicalData eachEntry : duplicateClinicalData) {

			if (filters.containsKey(eachEntry.getComponentName())) {
				clinicalData.remove(eachEntry);
				continue;
			} else {
				filters.put(eachEntry.getComponentName(), null);
			}

			if (eachEntry.getComponentName().equals("hw")) {

				String[] heightAndWeight = eachEntry.getComponentValue().split("/");

				if (heightAndWeight != null && heightAndWeight.length > 1) {
					float heightInMeters = Float.parseFloat(heightAndWeight[0]) * 0.4536F;
					float bmi = Float.parseFloat(heightAndWeight[1]) / (heightInMeters * heightInMeters);
					ClinicalData bmiData = new ClinicalData();
					bmiData.setComponentName("bmi");
					bmiData.setComponentValue(Float.toString(bmi));
					clinicalData.add(bmiData);
				}
			}
		}

		return patient;
	}

}
