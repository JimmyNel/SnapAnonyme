package com.SnapAnonyme;

import java.io.Serializable;
import java.util.ArrayList;

public class Reaction implements Serializable {

    private int id;
    private User user;
    private Article article;
    private Reaction_Type reaction_type;
    public static final String FILE_PATH= "Reaction.ser";
    private static ArrayList listIdReaction = Function.ReadIds(FILE_PATH);

    //region Accesseurs
    public int getId() {
        return id;
    }

    private void setId() {
        this.id = Function.generateId(listIdReaction);
        listIdReaction.add(this.id);
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

    public Reaction_Type getReaction_type() {
        return reaction_type;
    }

    public void setReaction_type(Reaction_Type reaction_type) {
        this.reaction_type = reaction_type;
    }

    public static ArrayList getListIdReaction() {
        return listIdReaction;
    }

    //endregion

    //region Constructeurs

    public Reaction(User user, Article article, Reaction_Type reaction_type) {
        this.setId();
        this.user = user;
        this.article = article;
        this.reaction_type = reaction_type;
        Function.Create(this, FILE_PATH);
    }

    //endregion

    @Override
    public String toString() {
        return "Reaction{" +
                "id=" + id +
                ", user=" + user +
                ", article=" + article +
                ", reaction_type=" + reaction_type +
                '}';
    }
}
