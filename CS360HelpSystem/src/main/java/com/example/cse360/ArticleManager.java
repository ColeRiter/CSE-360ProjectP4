import java.util.*;

public class ArticleManager {
    private Map<String, List<HelpArticle>> groups;
    private Map<String, Set<String>> groupAccess;

    public ArticleManager() {
        groups = new HashMap<>();
        groupAccess = new HashMap<>();
    }

    // Check if user has access to the article in a group
    public boolean hasAccess(String groupName, String userId) {
        Set<String> allowedUsers = groupAccess.getOrDefault(groupName, new HashSet<>());
        return allowedUsers.contains(userId);
    }

    // Add an article to a group
    public void addArticleToGroup(String groupName, HelpArticle article) {
        groups.computeIfAbsent(groupName, k -> new ArrayList<>()).add(article);
    }

    // Encrypt the article body if it's in a special access group
    public void encryptArticleBody(HelpArticle article, String encryptionKey) throws Exception {
        if (isInSpecialAccessGroup(article)) {
            String encryptedBody = HelpArticle.encrypt(article.getBody(), encryptionKey);
            article.setBody(encryptedBody);
        }
    }

    // Decrypt the article body
    public String decryptArticleBody(HelpArticle article, String encryptionKey) throws Exception {
        if (isInSpecialAccessGroup(article)) {
            return HelpArticle.decrypt(article.getBody(), encryptionKey);
        }
        return article.getBody(); // No encryption in regular groups
    }

    // Check if article belongs to a special access group
    private boolean isInSpecialAccessGroup(HelpArticle article) {
        // Implement logic to determine if the article is in a special access group
        return article.getGroupId() == 999; // Example: 999 could represent special access group ID
    }

    // Methods for listing and updating articles
    public List<HelpArticle> getArticlesByGroup(String groupName) {
        return groups.getOrDefault(groupName, new ArrayList<>());
    }
}