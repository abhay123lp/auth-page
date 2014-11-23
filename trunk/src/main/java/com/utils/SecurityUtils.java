package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurityUtils {
	
	public static String hashPassword(String password, String salt) {
		String generatedPassword = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");

			// Add password bytes to digest
			md.update(salt.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public static String getSalt() {
		SecureRandom sr;
		byte[] salt = new byte[16];
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.nextBytes(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return salt.toString();
	}
}
