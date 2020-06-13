package com.devansh.patientapi.model;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.devansh.patientapi.vault.TransitConverter;

@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Convert(converter = TransitConverter.class)
	private String name;
	private long age;
	@Convert(converter = TransitConverter.class)
	private String gender;
	@Convert(converter = TransitConverter.class)
	private String description;
	
	public Patient(long id, String name, long age, String gender, String description) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.description = description;
	}
	
	// default constructor is needed!
	public Patient() {
		super();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
