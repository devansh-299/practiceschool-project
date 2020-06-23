package com.devansh.patientapi.vault;

public class Data {
	
	public String ciphertext;
	public String plaintext;
	
	public String getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}
	
	public String getPlainText() {
		return plaintext;
	}
	
	public void setPlainText(String plaintext) {
		this.plaintext = plaintext;
	}
}
