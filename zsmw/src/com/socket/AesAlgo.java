/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.socket;

    
   

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import java.util.Scanner;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program to Encrypt/Decrypt String Using AES 128 bit Encryption Algorithm
 */
public class AesAlgo{
    
    public byte[] encryptionKey ;
    public SecretKeySpec secretKey ;
    
    
    AesAlgo(String encKey) throws Exception{
        encryptionKey = encKey.getBytes("UTF-8") ;
    }
  
    public SecretKeySpec generateKey() throws Exception{
        SecretKeySpec key = new SecretKeySpec(encryptionKey , "AES");
        return key ;
    }
    
    public String encrypt(String plainText) throws Exception  {
        //String encryptedText = "";
 
            Cipher cipher   = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            encryptionKey = encKey.getBytes("UTF-8") ;
        //    SecretKeySpec secretKey = new SecretKeySpec(encryptionKey, "AES");
            secretKey = generateKey() ;
            IvParameterSpec ivparameterspec = new IvParameterSpec(encryptionKey);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getMimeEncoder();
            String encryptedText = encoder.encodeToString(cipherText);
            return encryptedText;
      
    }
    
    

    public String decrypt(String encryptedText , byte[] encryptionKey ) throws Exception {
      //  String decryptedText = "";
        
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            encryptionKey = encKey.getBytes("UTF-8") ;
        //    byte[] key = encryptionKey.getBytes("UTF-8");
           // SecretKeySpec secretKey = new SecretKeySpec(encryptionKey, "AES");
            secretKey = generateKey() ;
            IvParameterSpec ivparameterspec = new IvParameterSpec(encryptionKey);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            
            //Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = Base64.getMimeDecoder().decode(encryptedText.getBytes("UTF-8"));
            String decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");
            return decryptedText;
    }
    

    
    
//    public static void main(String[] args) throws Exception{
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter String : ");
//        String plainString = sc.nextLine();
//        AesAlgo ae = new AesAlgo("ABCDEFGHIJKLMNOP") ;
//        String encyptStr   = encrypt(plainString);
//        String decryptStr  = decrypt(encyptStr);
//        
//        System.out.println("Plain   String  : "+plainString);
//        System.out.println("Encrypt String  : "+encyptStr);
//        System.out.println("Decrypt String  : "+decryptStr);
//        
//    }   
}
