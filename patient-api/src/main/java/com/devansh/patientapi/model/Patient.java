package com.devansh.patientapi.model;

public class Patient {
	
	private long id;
	private String name;
	private long age;
	private String gender;
	private String description;
	
	public Patient(long id, String name, long age, String gender, String description) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.description = description;
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
