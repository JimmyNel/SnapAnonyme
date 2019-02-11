package com.SnapAnonyme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Comment implements Serializable {

    private int id;
    private User user;
    private Article article;
    private Comment comment;
    private String content;
    private Date date;
    public static final String FILE_PATH= "Comment.ser";
    private static final ArrayList listIdComment = Function.ReadIds(FILE_PATH);

    //region Accesseurs;
    public int getId() {
        return id;
    }

    private void setId() {
        this.id = Function.generateId(listIdComment);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static ArrayList getListIdComment() {
        return listIdComment;
    }
    //endregion

    //region Constructeurs

    public Comment(User user, Article article, Comment comment, String content) {
        this.setId();
        this.user = user;
        this.article = article;
        this.comment = comment;
        this.content = content;
        this.date = new Date();
        Function.Create(this, FILE_PATH);
    }

    //endregion

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", article=" + article +
                ", comment=" + comment +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
