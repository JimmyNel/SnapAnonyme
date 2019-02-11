package com.SnapAnonyme;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Edition implements Serializable {

    private int id;
    private User user;
    private Article article;
    private Date date;
    private static final String FILE_PATH= "Edition.ser";
    private static final ArrayList listIdEdition = Function.ReadIds(FILE_PATH);

    //region Accesseurs
    public int getId() {
        return id;
    }

    private void setId() {
        this.id = Function.generateId(listIdEdition);
        listIdEdition.add(this.id);
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

    private void setArticle(Article article) {
        this.article = article;
    }

    public Date getDate() {
        return date;
    }

    public static ArrayList getListIdEdition() {
        return listIdEdition;
    }

    /**  Setter inutile car la date est initialisée dans le constructeur
     *   public void setDate(Date date) {
     *   this.date = new Date();
    }*/

    //endregion

    //region Constructeurs
    public Edition(User user, Article article) {
        this.setId();
        this.user = user;
        this.article = article;
        this.date = new Date();
        Function.Create(this, FILE_PATH);
    }
    //endregion

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public String toString() {
        return "Edition{" +
                "id=" + id +
                ", user=" + user +
                ", article=" + dateFormat.format(article) +
                ", date=" + date +
                '}';
    }
}
