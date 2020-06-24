 package com.devansh.patientapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devansh.patientapi.dao.PatientDao;
import com.devansh.patientapi.model.Patient;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private PatientDao patientDao;
	
	public PatientServiceImpl () {
		
	}

	@Override
	public List<Patient> getPatients() {
		return patientDao.findAll();
	}

	@Override
	public Patient getPatient(long patientId) {
		return patientDao.getOne(patientId);
	}

	@Override
	public Patient addPatient(Patient patient) {
		patientDao.save(patient);
		return patient;
	}

	@Override
	public String updatePatient(Patient patient) {
		patientDao.save(patient);
		return "Updated the patient with id: " + patient.getId();
	}

	@Override
	public void deletePatient(long patientId) {
		Patient patient = patientDao.getOne(patientId);
		patientDao.delete(patient);
	}

}
