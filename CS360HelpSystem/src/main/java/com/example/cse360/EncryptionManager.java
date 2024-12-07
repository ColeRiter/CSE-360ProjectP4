package main.java.com.example.cse360;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class EncryptionManager {
    private SecretKey secretKey;

    // Constructor: Generates a new key by default
    public EncryptionManager() throws Exception {
        generateNewKey();
    }

    // Constructor: Load an existing key
    public EncryptionManager(String keyFilePath) throws Exception {
        loadKey(keyFilePath);
    }

    // Generate a new AES encryption key
    public void generateNewKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES with 128-bit key
        this.secretKey = keyGen.generateKey();
    }

    // Encrypt data using the current key
    public String encrypt(String data) throws Exception {
        validateInput(data, "Data to encrypt");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Decrypt data using the current key
    public String decrypt(String encryptedData) throws Exception {
        validateInput(encryptedData, "Encrypted data to decrypt");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decrypted);
    }

    // Save the current key to a file
    public void saveKey(String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] encodedKey = secretKey.getEncoded();
            fos.write(encodedKey);
        }
    }

    // Load a key from a file
    public void loadKey(String filePath) throws IOException {
        File keyFile = new File(filePath);
        if (!keyFile.exists() || !keyFile.canRead()) {
            throw new IOException("Key file does not exist or cannot be read.");
        }
        try (FileInputStream fis = new FileInputStream(keyFile)) {
            byte[] encodedKey = fis.readAllBytes();
            this.secretKey = new SecretKeySpec(encodedKey, "AES");
        }
    }

    // Utility: Validate input for encryption/decryption
    private void validateInput(String input, String errorMessage) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException(errorMessage + " cannot be null or empty.");
        }
    }

    // Utility: Get Base64-encoded version of the current key (for debugging or advanced scenarios)
    public String getEncodedKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}