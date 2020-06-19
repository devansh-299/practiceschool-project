package com.devansh.patientapi.controller;


import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devansh.patientapi.model.Patient;
import com.devansh.patientapi.service.PatientService;

@RestController
@RequestMapping("/api")
public class WebController {
	
	@Autowired
	private PatientService patientService;
	
	@GetMapping("/patients")
	public List<Patient> getPatients() {
		return patientService.getPatients();
	}
	
	@GetMapping("/patients/{patientId}")
	public Patient getPatient(@PathVariable String patientId) {
		return patientService.getCourse(Long.parseLong(patientId));
	}
	
	@PostMapping("/patients")
	public ResponseEntity<Patient> addPatient(@RequestBody Patient newPatient) {
		try {
			Patient patient = patientService.addPatient(newPatient);
			long patientId = patient.getId();
			return ResponseEntity.created(new URI("/api/patients/" + patientId)).body(patient);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Patient>(HttpStatus.CONFLICT);
		}
		}
	
	@PutMapping("/patients")
	public String updatePatient(@RequestBody Patient patient) {
		return patientService.updatePatient(patient);
	}
	
	@DeleteMapping("/patients/{patientId}")
	public ResponseEntity<HttpStatus> deletePatient(@PathVariable String patientId) {
		try {
			patientService.deletePatient(Long.parseLong(patientId));
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
