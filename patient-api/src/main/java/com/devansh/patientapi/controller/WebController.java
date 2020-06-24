package com.devansh.patientapi.controller;


import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
import com.devansh.patientapi.vault.CallVault;

@RestController
@RequestMapping("/api")
public class WebController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private CallVault vaultService;
	
	/*
	 * APIs
	 */
	@GetMapping("/patients")
	public List<Patient> getPatients() {
		List<Patient> patientsList = patientService.getPatients();
		try {
			for (Patient patient : patientsList) {
				String decryptedMobileNumber = getPlainText(patient.getMobileNumber());
				patient.setMobileNumber(decryptedMobileNumber);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return patientsList;
	}
	
	@GetMapping("/patients/{patientId}")
	public Patient getPatient(@PathVariable String patientId) {
		Patient patient =  patientService.getPatient(Long.parseLong(patientId));
		try {
			String decryptedMobileNumber = getPlainText(patient.getMobileNumber());
			patient.setMobileNumber(decryptedMobileNumber);
		} catch (Exception e) {
			System.out.println(e.toString());
			patient.setMobileNumber("00");
		}
		return patient;
	}
	
	@PostMapping("/patients")
	public ResponseEntity<Patient> addPatient(@RequestBody Patient newPatient) {
		try {
			String encryptedMobileNumber = getCipherText(newPatient.getMobileNumber());

			newPatient.setMobileNumber(encryptedMobileNumber);
			// saving the patient in database
			Patient patient = patientService.addPatient(newPatient);
			long patientId = patient.getId();
			return ResponseEntity.created(
					new URI("/api/patients/" + patientId)).body(patient);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.toString());
			return new ResponseEntity<Patient>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/patients")
	public String updatePatient(@RequestBody Patient patient) {
		try {
			String encryptedMobileNumber = getCipherText(patient.getMobileNumber());

			patient.setMobileNumber(encryptedMobileNumber);
			return patientService.updatePatient(patient);
		} catch (Exception e) {
			return "Error Occured";
		}
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
	
	/*
	 * Calling Vault Services
	 */
	private String getCipherText(String plainText) throws Exception {
		// default value 
		String cipherText = "00";
		// converting to base64
		String encodedData = Base64.getEncoder().encodeToString(plainText.getBytes());
		
		// calling vault service
		CompletableFuture<String> asyncData = vaultService.encryptData(encodedData);
		
		// waiting for response
		CompletableFuture.allOf(asyncData).join();
		
		cipherText = asyncData.get();
		if (cipherText == null) {
			System.out.println("Vault Encryption Response is null");
			// default mobile number 
			cipherText = "00";
		}
		
		return cipherText;
	}
	
	private String getPlainText(String cipherText) throws Exception {
		// default value
		String plainText = "00";
		try {
			// receiving decrypted value from Vault
			CompletableFuture<String> asyncData = vaultService.decryptData(cipherText);
			
			// waiting for completion
			CompletableFuture.allOf(asyncData).join();
			
			String encodedData = asyncData.get();
			if (encodedData != null) {
				plainText = new String(Base64.getDecoder().decode(encodedData));
			} else {
				System.out.println("No Response from Vault");
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.toString());
		}
		return plainText;
	}
	
}
