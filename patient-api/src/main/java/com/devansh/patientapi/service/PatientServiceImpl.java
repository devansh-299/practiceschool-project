 package com.devansh.patientapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.devansh.patientapi.model.Patient;

@Service
public class PatientServiceImpl implements PatientService {
	
	List<Patient> patientList;
	
	public PatientServiceImpl () {
		patientList = new ArrayList<Patient>();
		patientList.add(new Patient(1, "devansh", 20, "male", "healthy"));
		patientList.add(new Patient(2, "abc", 21, "male", "healthy"));
		patientList.add(new Patient(3, "xyz", 22, "female", "healthy"));
	}

	@Override
	public List<Patient> getPatients() {
		return patientList;
	}

	@Override
	public Patient getCourse(long courseId) {
		
		Patient patient = null;
		
		for (Patient p : patientList) {
			if(p.getId() == courseId) {
				patient = p;
				break;
			}
		}
		return patient;
	}

	@Override
	public Patient addPatient(Patient patient) {
		patientList.add(patient);
		return patient;
	}

}
