package com.devansh.patientapi.service;

import java.util.List;

import com.devansh.patientapi.model.Patient;

public interface PatientService {

	public List<Patient> getPatients();

	public Patient getCourse(long parseLong);

	public Patient addPatient(Patient patient);
	
}
