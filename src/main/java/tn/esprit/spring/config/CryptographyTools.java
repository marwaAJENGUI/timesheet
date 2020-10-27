package tn.esprit.spring.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;

public class CryptographyTools {
	
	@Value("${mpCryptoPassword}")
	private static String mpCryptoPassword;
	private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	private static StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();

	public String ecrypt(String value) {
	        encryptor.setPassword(mpCryptoPassword);
	        return encryptor.encrypt(value);
	}
	
	public String decrypt(String value) {
	        decryptor.setPassword(mpCryptoPassword);
	        return decryptor.decrypt(value);
	}

}
