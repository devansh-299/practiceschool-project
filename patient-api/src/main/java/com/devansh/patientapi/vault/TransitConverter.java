package com.devansh.patientapi.vault;

import javax.persistence.AttributeConverter;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;

public class TransitConverter implements AttributeConverter<String, String> {
	
	@Override
	public String convertToDatabaseColumn(String inputString) {	
		VaultOperations vaultOps = BeanUtil.getBean(VaultOperations.class);
		Plaintext plaintext = Plaintext.of(inputString);	
		String cipherText = vaultOps.opsForTransit().encrypt("patient", plaintext).getCiphertext();
		return cipherText;
	}

	@Override
	public String convertToEntityAttribute(String inputString) {
		VaultOperations vaultOps = BeanUtil.getBean(VaultOperations.class);
		Ciphertext ciphertext = Ciphertext.of(inputString);
        String plaintext = vaultOps.opsForTransit().decrypt("patient", ciphertext).asString();
		return plaintext;
	}

}