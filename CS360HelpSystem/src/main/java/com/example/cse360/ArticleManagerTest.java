package com.example.cse360;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ArticleManagerTest {

    @Test
    public void testAddArticle() throws Exception {
        ArticleManager manager = new ArticleManager();
        HelpArticle article = new HelpArticle(1, "Header", "Title", "Short Desc", "Body", List.of("keyword"), 1L);
        manager.addArticleToGroup("General", article);

        assertEquals(1, manager.getArticlesByGroup("General").size());
    }

    @Test
    public void testEncryptAndDecryptArticleBody() throws Exception {
        ArticleManager manager = new ArticleManager();
        HelpArticle article = new HelpArticle(1, "Header", "Title", "Short Desc", "Sensitive Body", List.of("keyword"), 999L); // Special access group

        String encryptionKey = "TestKey123";
        manager.encryptArticleBody(article, encryptionKey);
        String encryptedBody = article.getBody();
        assertNotEquals("Sensitive Body", encryptedBody);

        String decryptedBody = manager.decryptArticleBody(article, encryptionKey);
        assertEquals("Sensitive Body", decryptedBody);
    }

    @Test
    public void testUnauthorizedArticleDeletion() {
        ArticleManager manager = new ArticleManager();
        HelpArticle article = new HelpArticle(1, "Header", "Title", "Short Desc", "Body", List.of("keyword"), 1L);
        manager.addArticleToGroup("General", article);

        String unauthorizedUserId = "user2";
        assertThrows(SecurityException.class, () -> manager.deleteArticle("General", article, unauthorizedUserId));
    }

    @Test
    public void testGroupBasedFiltering() {
        ArticleManager manager = new ArticleManager();
        HelpArticle beginnerArticle = new HelpArticle(1, "Beginner Header", "Beginner Title", "Desc", "Beginner Body", List.of("easy"), 1L);
        HelpArticle advancedArticle = new HelpArticle(2, "Advanced Header", "Advanced Title", "Desc", "Advanced Body", List.of("hard"), 1L);

        manager.addArticleToGroup("General", beginnerArticle);
        manager.addArticleToGroup("General", advancedArticle);

        List<HelpArticle> beginnerResults = manager.filterArticles("General", "easy", "beginner");
        assertEquals(1, beginnerResults.size());
        assertEquals("Beginner Title", beginnerResults.get(0).getTitle());

        List<HelpArticle> advancedResults = manager.filterArticles("General", "hard", "advanced");
        assertEquals(1, advancedResults.size());
        assertEquals("Advanced Title", advancedResults.get(0).getTitle());
    }

    @Test
    public void testContentFormattingInArticle() {
        ArticleManager manager = new ArticleManager();
        HelpArticle article = new HelpArticle(1, "Formatted Header", "Formatted Title", "Short Desc", "<b>Bold Body</b>", List.of("format"), 1L);

        manager.addArticleToGroup("General", article);
        HelpArticle retrievedArticle = manager.getArticlesByGroup("General").get(0);

        assertTrue(retrievedArticle.getBody().contains("<b>"));
        assertEquals("<b>Bold Body</b>", retrievedArticle.getBody());
    }
}