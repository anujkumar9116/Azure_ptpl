package com.test.runHiddenApi;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryption {
	
	//  static Logger logger = LoggerFactory.getLogger(EncryptionDecryption.class);

	public static SecretKeySpec setKey(String myKey) throws UnsupportedEncodingException, NoSuchAlgorithmException  {
        byte[] key = myKey.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
        
	}

	public static String encrypt(String uid, String salt,String pwd) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec skKey=setKey(salt);
		IvParameterSpec iv = new IvParameterSpec(pwd.getBytes(), 0, ciper.getBlockSize());
		ciper.init(Cipher.ENCRYPT_MODE, skKey, iv);

		byte[] encValue = ciper.doFinal(uid.getBytes());
		
	
		byte[] encodedBytes = Base64.encodeBase64(Base64.encodeBase64(encValue));
	//	String encodedString = Base64.getEncoder().encodeToString(Base64.getEncoder().encode(encValue));
        return new String(encodedBytes);
	}

	public static String decrypt(String value, String salt,String pwd) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException  {

	    Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    SecretKeySpec skKey=setKey(salt);
		IvParameterSpec iv = new IvParameterSpec(pwd.getBytes(), 0, ciper.getBlockSize());
		ciper.init(Cipher.DECRYPT_MODE, skKey,iv);
		byte[] keydec = Base64.encodeBase64(skKey.getEncoded());
		String s = new String(keydec, "UTF-8");
		System.out.println("key"+s);
		byte[] ivdec = iv.getIV();
		String s1 = new String(ivdec, "UTF-8");
		System.out.println("iv   "+s1);
		
		byte[] decodedBytes = Base64.decodeBase64(Base64.decodeBase64(value));
		byte[] decValue = ciper.doFinal(decodedBytes);

		return new String(decValue);
	}

    public static String convertToMD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException   {

	    MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] md5hash = md.digest();

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < md5hash.length; i++) {
            int halfbyte = (md5hash[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = md5hash[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }


    public static String encryptUid(String uid, String pwd ) {

        try {
            String saltedDBPassword = convertToMD5(pwd);
            String uidEnc = EncryptionDecryption.encrypt(uid, saltedDBPassword, pwd);
            return uidEnc;
        } catch (Exception ex) {
            return ex.getLocalizedMessage();
        }

    }

    /**
     *
     * @param uidEnc         - encrypted UID
     * @param pwd            - secret_pwd from client table
     * @return
     */
    public static String decryptUid( String uidEnc,String pwd) {

        try {
            String saltedDBPassword = convertToMD5(pwd);
            String uidDec = EncryptionDecryption.decrypt(uidEnc, saltedDBPassword, pwd);
            return uidDec;
        } catch (Exception ex) {
         //   logger.error( EncryptionDecryption.class.getName());
            return "";
        }

    }

//   public static void main(String args[]) {
////"dFVnTitYckQyVk1hcXltSlR6b2F0Zz09"
//        String pwd="nic@impds#dedup05613"; //change accordingly and share to client for decryption
//        //String encUID =  "Tav6RxTBkFMRj8ATKgAUXw==";
//         String encUID = "dFVnTitYckQyVk1hcXltSlR6b2F0Zz09";
//        String uid = "228455771148" ; //"815927839200";//"699191320155";
//        //uid 337484239587  ZWh6OEhTZlY4azU4VDJIS0FaVEZMQT09
//        //240080089942  NEFIRjV1dWpVanFHL2pMczhKUDRFQT09
//        //   778788810190  ckJmdGl1NHFKdTcvRXMxZXRMU3ZyQT09
//        // 373032599963   OHMvU3FzKzFYdkJ2MWthTXRPdW9SZz09
//       String tt = encryptUid(uid,pwd);
//
//       System.out.println( tt);
//     //  if(tt.equalsIgnoreCase("NUgyam11YW9WZ2Jyd2p3OHdjRXVBZz09")) {
//    //	   System.out.println("ok");
//     //  }else {
//    	//   System.out.println("fail");
//      // }
//    System.out.println( decryptUid(encUID,pwd));
//
//
//    }

}