package com.devansh.patientapi.vault;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallVault {
	
	@Autowired
    private RestTemplate restTemplate;
	
	private static String encryptionUrl = 
			"http://127.0.0.1:8200/v1/transit/encrypt/patient";
	private static String decryptionUrl = 
			 "http://127.0.0.1:8200/v1/transit/decrypt/patient";
	
	@Bean
	public RestTemplate restTemplate() {
		 String vaultRootToken = "s.XUaDFW8HYaTgjI6m5k5JK83M";
		 return restTemplate = new RestTemplateBuilder(
				 rt-> rt.getInterceptors().add((request, body, execution) -> {
					 request.getHeaders().add("X-Vault-Token",vaultRootToken);
					 return execution.execute(request, body);
				 })).build();
	 }
	
	@Async("asyncExecutor")
	public CompletableFuture<String> encryptData(String plainText) throws Exception {
		EncryptionRequest encryptionRequest = new EncryptionRequest();
		encryptionRequest.setPlainText(plainText);
		VaultResponse vaultResponse = 
				restTemplate.postForObject(encryptionUrl, encryptionRequest, 
						VaultResponse.class);
		String cipherText = vaultResponse.getData().getCiphertext();
		return CompletableFuture.completedFuture(cipherText);
		
	}
	
	@Async("asyncExecutor")
	public CompletableFuture<String> decryptData(String cipherText) throws Exception {
		DecryptionRequest decryptionRequest = new DecryptionRequest();
		decryptionRequest.setCipherText(cipherText);
		VaultResponse vaultResponse = 
				restTemplate.postForObject(decryptionUrl, decryptionRequest, 
						VaultResponse.class);
		String plainText = vaultResponse.getData().getPlainText();
		return CompletableFuture.completedFuture(plainText);
	}
	
}
