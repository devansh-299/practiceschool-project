package com.devansh.patientapi.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devansh.patientapi.model.Patient;
import com.devansh.patientapi.service.PatientService;

@RestController
@RequestMapping("/patientapi")
public class WebController {
	
	@Autowired
	private PatientService patientService;
	
	@GetMapping("/patients")
	public List<Patient> getPatients() {
		return patientService.getPatients();
	}
	
	@GetMapping("/patient/{patientIdId}")
	public Patient getCoursePatient(@PathVariable String patietnId) {
		return patientService.getCourse(Long.parseLong(patietnId));
	}
	
	@PostMapping("/patient")
	public Patient addPatient(@RequestBody Patient patient) {
		return patientService.addPatient(patient);
	}

}
