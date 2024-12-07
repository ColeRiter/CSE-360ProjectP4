public void searchArticles(String query, String group, String level) {
    // Searching logic for articles by query, group, and level
    List<HelpArticle> articles = articleManager.getArticlesByGroup(group); // Assuming you fetch the list based on group
    boolean found = false;

    for (HelpArticle article : articles) {
        // Search the title first
        if (article.getTitle().contains(query)) {
            try {
                // Decrypt the article body if necessary (if it's encrypted)
                String decryptedBody = HelpArticle.decrypt(article.getBody(), "your-encryption-key");

                // Display the article with the decrypted body
                System.out.println("Title: " + article.getTitle());
                System.out.println("Body: " + decryptedBody);
                found = true;
            } catch (Exception e) {
                System.out.println("Error decrypting article: " + e.getMessage());
            }
        }
    }

    if (!found) {
        System.out.println("No articles found matching your search.");
    }
}