package com.SnapAnonyme;

import java.io.Serializable;
import java.util.ArrayList;

public class Article implements Serializable {

    private int id;
    private User user;
    private Fichier fichier;
    private String content;
    private boolean isVisible;
    private int nbReport;
    public static final String FILE_PATH= "Article.ser";
    private static ArrayList listIdArticle = Function.ReadIds(FILE_PATH);

    //region Accesseurs
    public int getId() {
        return id;
    }

    private void setId() {
        this.id = Function.generateId(listIdArticle);
        //listIdArticle.add(this.id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getNbReport() {
        return nbReport;
    }

    public void setNbReport() {
        ++this.nbReport;
    }

    public static ArrayList getListIdArticle() {
        return listIdArticle;
    }

    //endregion

    //region Constructeur

    public Article(User user, Fichier fichier, String content, boolean isVisible) {
        this.setId();
        this.user = user;
        this.fichier = fichier;
        this.content = content;
        this.isVisible = isVisible;
        this.nbReport = 0;
        Function.Create(this,FILE_PATH);
    }

    //endregion

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", user=" + user +
                ", fichier=" + fichier +
                ", content='" + content + '\'' +
                ", isVisible=" + isVisible +
                ", nbReport=" + nbReport +
                '}';
    }

}
