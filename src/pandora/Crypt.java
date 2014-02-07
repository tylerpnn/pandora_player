package pandora;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Crypt {

	private static final String decryptKey = "R=U!LH$O2B#";
	private static final String encryptKey = "6#26FRL$ZWD";
	private final static char[] hexArray = "0123456789abcdef".toCharArray();
	
	private Cipher encrypt_c;
	private Cipher decrypt_c;
	
	public Crypt() {
		try {
			encrypt_c = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			encrypt_c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "Blowfish"));
			
			decrypt_c = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			decrypt_c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "Blowfish"));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] decrypt(byte[] input) {
		byte[] data = null;
		try {
			data = decrypt_c.doFinal(input);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public byte[] encrypt(byte[] input) {
		byte[] data = null;
		try {
			data = encrypt_c.doFinal(input);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public String byteToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public byte[] hexToBytes(String hex) {
		return DatatypeConverter.parseHexBinary(hex);
	}
	
	public String decryptSyncTime(String t) {
		byte[] syncTime = hexToBytes(t);
		byte[] decrypted = decrypt(syncTime);
		byte[] cleaned = new byte[decrypted.length - 4];
		System.arraycopy(decrypted, 4, cleaned, 0, cleaned.length);
		return new String(cleaned);
	}
}
