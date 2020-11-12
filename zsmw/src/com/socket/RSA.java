package com.socket;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/*
	 * RSA class
	 * 			will generate RSA key pair and save in files locally.
	 * 
	 * 
 */
public final class RSA {

    public Key publicKey;
    public Key privateKey;
    public String IV;


    /*
		 * main method
		 * 			will instantiate an object of RSA class and call the createRSA method.
		 * 
     */
    // ============ Generating key pair =======
    /*
		 * createRSA method
		 * 					will create RSA key pair.
		 * 					the keys will be saved as object in two separate files.
     */
   public void RSAyoyo() throws NoSuchAlgorithmException, GeneralSecurityException, IOException {

        KeyPairGenerator kPairGen = KeyPairGenerator.getInstance("RSA");
        kPairGen.initialize(2048);
        KeyPair kPair = kPairGen.genKeyPair();
        publicKey = kPair.getPublic();
        System.out.println(publicKey);
        privateKey = kPair.getPrivate();

        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = fact.getKeySpec(kPair.getPublic(), RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = fact.getKeySpec(kPair.getPrivate(), RSAPrivateKeySpec.class);
        serializeToFile("public.key", pub.getModulus(), pub.getPublicExponent()); 				// this will give public key file
        serializeToFile("private.key", priv.getModulus(), priv.getPrivateExponent());			// this will give private key file

        
    }

    // ===== Save the keys with  specifications into files ==============
    /*
		 * serializeToFile method
		 * 						will create an ObjectOutput Stream and 
		 * 						save the elements of key pairs into files locally.
		 * 
     */
    void serializeToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
        ObjectOutputStream ObjOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));

        try {
            ObjOut.writeObject(mod);
            ObjOut.writeObject(exp);
            System.out.println("Key File Created: " + fileName);
        } catch (Exception e) {
            throw new IOException(" Error while writing the key object", e);
        } finally {
            ObjOut.close();
        }
    }

    public String generateAESkey() throws NoSuchAlgorithmException {
        SecretKey AESkey = null;
        KeyGenerator Gen = KeyGenerator.getInstance("AES");
        Gen.init(128);
        AESkey = Gen.generateKey();
        System.out.println("\n\n\n\n Generated the AES key : " + AESkey);

        String encodedKey = Base64.getMimeEncoder().encodeToString(AESkey.getEncoded());
        return encodedKey;
    }

    // Encrypt text using AES key
    public String encryptTextUsingAES(String plainText, String aesKeyString) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
        SecretKey originalSecretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        
        IV = "AAAAAAAAAAAAAAAA";
        
        aesCipher.init(Cipher.ENCRYPT_MODE, originalSecretKey, new IvParameterSpec(IV.getBytes()));
        //   aesCipher.init(Cipher.ENCRYPT_MODE, originalSecretKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes("UTF8"));
        return Base64.getEncoder().encodeToString(byteCipherText);
    }

    // Decrypt text using AES key
    public String decryptTextUsingAES(String encryptedText, String aesKeyString) throws Exception {

        byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
        SecretKey originalSecretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        
        IV = "AAAAAAAAAAAAAAAA";
        
        aesCipher.init(Cipher.DECRYPT_MODE, originalSecretKey, new IvParameterSpec(IV.getBytes()));
        //       aesCipher.init(Cipher.DECRYPT_MODE, originalSecretKey);
        byte[] bytePlainText = aesCipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(bytePlainText);
    }

// Decrypt AES Key using RSA public key
    public String decryptAESKey(String encryptedAESKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedAESKey)));
    }

    // Encrypt AES Key using RSA private key
    public String encryptAESKey(String plainAESKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainAESKey.getBytes()));
    }


    public PrivateKey readPrivateKeyFromFile(String fileName) throws IOException {

        FileInputStream in = new FileInputStream(fileName);
        ObjectInputStream readObj = new ObjectInputStream(new BufferedInputStream(in));

        try {
            BigInteger m = (BigInteger) readObj.readObject();
            BigInteger d = (BigInteger) readObj.readObject();
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, d);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PrivateKey priKey = fact.generatePrivate(keySpec);
            return priKey;
        } catch (Exception e) {
            throw new RuntimeException("Some error in reading private key", e);
        } finally {
            readObj.close();
        }
    }

    public PublicKey readPublicKeyFromFile(String fileName) throws IOException {

        FileInputStream in = new FileInputStream(fileName);
        ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));

        try {
            BigInteger m = (BigInteger) oin.readObject();
            BigInteger e = (BigInteger) oin.readObject();
            RSAPublicKeySpec keySpecifications = new RSAPublicKeySpec(m, e);

            KeyFactory kF = KeyFactory.getInstance("RSA");
            PublicKey pubK = kF.generatePublic(keySpecifications);
            return pubK;
        } catch (Exception e) {
            throw new RuntimeException("Some error in reading public key", e);
        } finally {
            oin.close();
        }
    }

}
