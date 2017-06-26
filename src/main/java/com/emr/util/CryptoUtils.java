package com.emr.util;

import org.jasypt.util.binary.StrongBinaryEncryptor;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by user on 6/25/2017.
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final String myEncryptionPassword = "Mary has one cat";



    public static void jasyptEncrypt(String sourcePath, String encryptedFilePath)
            throws CryptoException, IOException {
        StrongBinaryEncryptor binaryEncryptor = new StrongBinaryEncryptor();
        binaryEncryptor.setPassword(myEncryptionPassword);
        byte[] encryptedBytes = binaryEncryptor.encrypt(convertAsBytes(sourcePath));

        OutputStream out = new FileOutputStream(encryptedFilePath);
        out.write(encryptedBytes);
        out.close();
    }

    public static byte[] jasyptDecrypt(String encryptedFileSourcePath)
            throws CryptoException, IOException {
        StrongBinaryEncryptor binaryEncryptor = new StrongBinaryEncryptor();
        binaryEncryptor.setPassword(myEncryptionPassword);
        return binaryEncryptor.decrypt(convertAsBytes(encryptedFileSourcePath));
    }

    private static byte[] readFully(InputStream stream) throws IOException
    {
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    private static byte[] convertAsBytes(String sourcePath) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(sourcePath);
            return readFully(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }

    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    public static byte[] decrypt(String key, File inputFile)
            throws CryptoException {
        return doCrypto(Cipher.DECRYPT_MODE, key, inputFile);
    }

    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    private static byte[] doCrypto(int cipherMode, String key, File inputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            return cipher.doFinal(inputBytes);

            /*FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);*/

            //inputStream.close();
            //outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}


