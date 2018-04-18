package com.example.concretepage.dao;

import com.example.concretepage.entity.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class ArticleDAO implements IArticleDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public Article getArticleById(int articleId){
        return entityManager.find(Article.class, articleId);
    }

    @SuppressWarnings("unchecked")

    public List<Article> getAllArticles(){
        String hql = "FROM Article as atcl ORDER BY atcl.articleId";
        return (List<Article>) entityManager.createQuery(hql);
    }

    public void addArticle(Article article){
        entityManager.persist(article);
    }

    public void updateArticle(Article article){
        Article artc1 = getArticleById(article.getArticleId());
        artc1.setTitle(article.getTitle());
        artc1.setCategory(article.getCategory());
        entityManager.flush();
    }

    public void deleteArticle(int articleId){
        entityManager.remove(getArticleById(articleId));
    }

    public boolean articleExists(String title, String category){
        String hql = "FROM Article as atcl WHERE atcl.title=? anda atcl.category=?";
        int count = entityManager.createQuery(hql).setParameter(1, title)
                .setParameter(2, category).getResultList().size();
        return count>0 ? true:false;
    }
}
