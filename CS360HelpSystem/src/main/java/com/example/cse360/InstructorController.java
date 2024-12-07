package main.java.com.example.cse360;

package main.java.com.example.cse360;

import java.util.List;
import java.util.stream.Collectors;

public class InstructorController {
    private ArticleManager articleManager;
    private EncryptionManager encryptionManager;

    public InstructorController(ArticleManager articleManager, EncryptionManager encryptionManager) {
        this.articleManager = articleManager;
        this.encryptionManager = encryptionManager;
    }

    /**
     * View articles assigned to the instructor's group(s).
     * @param groupId ID of the group
     * @return List of articles
     */
    public List<HelpArticle> viewArticlesByGroup(long groupId) {
        return articleManager.getArticlesByGroup(groupId);
    }

    /**
     * Add a new article to a specific group.
     * @param article New HelpArticle object
     * @param groupId ID of the group
     * @throws Exception If encryption fails
     */
    public void addArticle(HelpArticle article, long groupId) throws Exception {
        if (isSensitiveGroup(groupId)) {
            String encryptedBody = encryptionManager.encrypt(article.getBody());
            article.setBody(encryptedBody);
        }
        articleManager.addArticleToGroup(groupId, article);
    }

    /**
     * Edit an existing article.
     * @param articleId ID of the article to edit
     * @param newContent New content for the article
     * @throws Exception If encryption/decryption fails
     */
    public void editArticle(long articleId, String newContent) throws Exception {
        HelpArticle article = articleManager.getArticleById(articleId);

        if (article == null) {
            throw new IllegalArgumentException("Article not found with ID: " + articleId);
        }

        if (isSensitiveGroup(article.getGroupId())) {
            String encryptedContent = encryptionManager.encrypt(newContent);
            article.setBody(encryptedContent);
        } else {
            article.setBody(newContent);
        }
        articleManager.updateArticle(article);
    }

    /**
     * Search for articles by keyword.
     * @param keyword Keyword to search for
     * @return List of articles matching the keyword
     */
    public List<HelpArticle> searchArticles(String keyword) {
        return articleManager.getAllArticles().stream()
                .filter(article -> article.getKeywords().contains(keyword))
                .collect(Collectors.toList());
    }

    /**
     * Check if the group requires encryption for articles.
     * @param groupId ID of the group
     * @return True if the group is sensitive, false otherwise
     */
    private boolean isSensitiveGroup(long groupId) {
        // Example: Groups with ID > 100 are sensitive
        return groupId > 100;
    }
}