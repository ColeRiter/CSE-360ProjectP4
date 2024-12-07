package main.java.com.example.cse360;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BackupManager {
    private List<HelpArticle> articles;
    private String encryptionKey;

    public BackupManager(List<HelpArticle> articles, String encryptionKey) {
        this.articles = articles;
        this.encryptionKey = encryptionKey;
    }

    // Backup method with enhanced filtering and encryption handling
    public void backupArticles(String filename, List<Long> groupIds, String contentLevel, String userRole) throws IOException {
        if (!userRole.equalsIgnoreCase("admin") && !userRole.equalsIgnoreCase("manager")) {
            throw new SecurityException("Unauthorized role for performing backup.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            List<HelpArticle> backupArticles = articles.stream()
                    .filter(article -> 
                        (groupIds.isEmpty() || groupIds.contains(article.getGroupId())) &&
                        (contentLevel == null || contentLevel.equalsIgnoreCase(article.getContentLevel())))
                    .collect(Collectors.toList());

            // Encrypt sensitive articles before backup
            for (HelpArticle article : backupArticles) {
                if (article.getGroupId() == 999) { // Example: Special access group ID
                    String encryptedBody = HelpArticle.encrypt(article.getBody(), encryptionKey);
                    article.setBody(encryptedBody);
                }
            }

            oos.writeObject(backupArticles);
        }
    }

    // Restore method with decryption and merge support
    public void restoreArticles(String filename, boolean merge, String userRole) throws IOException, ClassNotFoundException {
        if (!userRole.equalsIgnoreCase("admin")) {
            throw new SecurityException("Only admin users can perform restoration.");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<HelpArticle> backupArticles = (List<HelpArticle>) ois.readObject();

            // Decrypt sensitive articles after restore
            for (HelpArticle article : backupArticles) {
                if (article.getGroupId() == 999) { // Special access group ID
                    String decryptedBody = HelpArticle.decrypt(article.getBody(), encryptionKey);
                    article.setBody(decryptedBody);
                }
            }

            if (merge) {
                for (HelpArticle backupArticle : backupArticles) {
                    if (!articles.stream().anyMatch(article -> article.getId() == backupArticle.getId())) {
                        articles.add(backupArticle);
                    }
                }
            } else {
                articles.clear();
                articles.addAll(backupArticles);
            }
        }
    }

    // Method to validate the backup file before any operation
    public boolean validateBackupFile(String filename) {
        File file = new File(filename);
        return file.exists() && file.canRead();
    }
}