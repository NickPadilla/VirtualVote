/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account.utility;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

import com.monstersoftwarellc.virtualvote.account.AccountLogin;


/**
 * @author nicholas
 *
 */
public class PasswordUtility {

	private static Logger LOG = Logger.getLogger(PasswordUtility.class);
	
	/**
	 * Ensure passwords match. Field passwordEntered will be encrypted before testing for equals.
	 * @param passwordEntered
	 * @param passwordSaved
	 * @return true if passwords match, after encryption
	 */
	public static boolean passwordValidation(String passwordEntered, String passwordSaved){
		return encodePassword(passwordEntered).equals(passwordSaved);
	}
	
	/**
	 * Will update the Password fields so that they are encrypted before being saved.  
	 * @param login
	 * @return
	 */
	public static AccountLogin handlePasswordEncryption(AccountLogin login){
		String passEncrypted = encodePassword(login.getPassword());
		String passVerifyEncrypted = encodePassword(login.getPasswordVerify());
		login.setPassword(passEncrypted);
		login.setPasswordVerify(passVerifyEncrypted);
		return login;
	}
	
	/**
	 * Encypt password to default {@link MessageDigest} Algorithm.
	 * @param password
	 * @return
	 */
	public static String encodePassword(String password){
		return encodePassword(password,EncryptionAlgorithms.SHA256);
	}

	/**
	 * Convenience method for encoding a password or string value, encryption algorithm specified 
	 * by {@link EncryptionAlgorithms}.
	 * @param password
	 * @param algo
	 * @return
	 */
	public static String encodePassword(String password, EncryptionAlgorithms algo) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algo.getName());
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

}
