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
			// converting to base64
			String encodedMobileNumber = Base64.getEncoder().encodeToString(
					newPatient.getMobileNumber().getBytes());
			
			// receiving encrypted value from Vault
			CompletableFuture<String> asyncMobileNumber = 
					vaultService.encryptData(encodedMobileNumber);
			
			CompletableFuture.allOf(asyncMobileNumber).join();
			
			String encryptedMobileNumber = asyncMobileNumber.get();
			if (encryptedMobileNumber == null) {
				System.out.println("Vault Encryption Response is null");
				// default mobile number 
				encryptedMobileNumber = "0";
			}

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
	
	@PostMapping("/patients/decryptData")
	public String decryptData(@RequestBody Patient patient) {
		// default mobile number 
		String mobileNumber = "0";
		try {
			// receiving decrypted value from Vault
			CompletableFuture<String> asyncEncodedMobileNumber = 
					vaultService.decryptData(patient.getMobileNumber());
			
			CompletableFuture.allOf(asyncEncodedMobileNumber).join();
			
			String encodedMobileNumber = asyncEncodedMobileNumber.get();
			if (encodedMobileNumber == null) {
				System.out.println("Vault Decryption Response is null");
			} 
			mobileNumber = new String(
					Base64.getDecoder().decode(encodedMobileNumber));
			
			return mobileNumber;
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e.toString());
			return mobileNumber;
		}
	}
}
