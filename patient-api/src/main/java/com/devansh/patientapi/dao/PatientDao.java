package com.devansh.patientapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devansh.patientapi.model.Patient;

// <ModelClass, DataType of PrimaryKey> needs to be specified
public interface PatientDao extends JpaRepository<Patient, Long>{

}
