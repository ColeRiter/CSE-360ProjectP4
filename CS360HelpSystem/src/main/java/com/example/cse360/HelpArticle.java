package main.java.com.example.cse360;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class HelpArticle implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String header;
    private String title;
    private String shortDescription;
    private String body; // This will be encrypted if in a special access group
    private List<String> keywords;
    private long groupId;

    // Constructor
    public HelpArticle(long id, String header, String title, String shortDescription, String body, List<String> keywords, long groupId) {
        this.id = id;
        this.header = header;
        this.title = title;
        this.shortDescription = shortDescription;
        this.body = body;
        this.keywords = keywords;
        this.groupId = groupId;
    }

    // Encryption method with validation
    public static String encrypt(String data, String key) throws Exception {
        validateInput(data, "Data to encrypt");
        validateInput(key, "Encryption key");

        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Decryption method with validation
    public static String decrypt(String encryptedData, String key) throws Exception {
        validateInput(encryptedData, "Encrypted data to decrypt");
        validateInput(key, "Decryption key");

        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }

    // Utility: Validate input strings
    private static void validateInput(String input, String errorMessage) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException(errorMessage + " cannot be null or empty.");
        }
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "HelpArticle{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", body='" + (body != null ? "[ENCRYPTED]" : "null") + '\'' +
                ", keywords=" + keywords +
                ", groupId=" + groupId +
                '}';
    }
}