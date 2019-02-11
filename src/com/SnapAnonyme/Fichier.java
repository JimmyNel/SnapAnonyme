package com.SnapAnonyme;

import java.io.Serializable;
import java.util.ArrayList;

public class Fichier implements Serializable {

    private int id;
    private String url, mimeType;
    private double size;
    public static final String FILE_PATH= "Fichier.ser";
    private static ArrayList listIdFichier = Function.ReadIds(FILE_PATH);

    //region Accesseurs
    public int getId() {
        return id;
    }

    private void setId() {
        this.id = Function.generateId(listIdFichier);
        listIdFichier.add(this.id);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public static ArrayList getListIdFichier() {
        return listIdFichier;
    }

    //endregion

    //region Constructeurs
    public Fichier(String url) {
        this.setId();
        this.url = url;
        this.mimeType = "jpg";
        this.size = 1.0;
        Function.Create(this,FILE_PATH);
    }
    //endregion

    @Override
    public String toString() {
        return "Fichier{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", size=" + size +
                '}';
    }
}
