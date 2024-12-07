package main.java.com.example.cse360;

import java.util.Map;

public class AdminController {
    private ArticleManager articleManager;

    public AdminController() {
        this.articleManager = new ArticleManager();
    }

    // Assign roles to users
    public void assignRole(String userId, String role) {
        articleManager.assignUserRole(userId, role);
    }

    // Delete an article with enhanced role-based safeguards
    public String deleteArticle(String groupName, HelpArticle article, String userId) {
        try {
            articleManager.deleteArticle(groupName, article, userId);
            return "Article deleted successfully.";
        } catch (SecurityException e) {
            return "Error: You do not have permission to delete this article.";
        }
    }

    // Provide role-based insights for groups
    public Map<String, String> getUserRoles() {
        return articleManager.getUserRoles(); // Added getter in ArticleManager
    }
}