package com.SnapAnonyme;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Function implements Serializable {
    public static int generateId(ArrayList ids){
        Random rdm = new Random();
        int newId;

        do {
            newId = rdm.nextInt(10000) + 1;
        } while (ids.contains(newId));
        /** Ecriture simplifiée
         * while (ids.contains((id = (nbAleatoire.nextInt(1000)+1))));
         */
        return newId;
    }

    public static void Create(Object o, String filepath) {
        ObjectOutputStream out = null;
        try{
            FileOutputStream file = new FileOutputStream(filepath, true);
            try{
                out =  new ObjectOutputStream(          /** stockage d'objet */
                            new BufferedOutputStream(file));     /** permet d'écrire plus rapidement un objet dans le fichier */
                out.writeObject(o);
                out.close();
                System.out.println(o + "mis à jour");
            }
            catch (IOException e){
                System.out.println(o + " n'a pas pu être créé");
            }
            finally{
                out.close();
            }
        }
        catch (IOException e){
            System.out.println("Erreur");
        }
    }

    public static ArrayList Read(String filepath) {
        ObjectInputStream ois = null;
        Object o;
        boolean bool = true;
        ArrayList<Object> listObject = new ArrayList<>();
        BufferedInputStream bif = null;

        try{
            bif = new BufferedInputStream(new FileInputStream(filepath));
            try {
                while (bool) {
                    ois = new ObjectInputStream(bif);
                    if (ois != null) {
                        o = ois.readObject();
                        listObject.add(o);
                    } else {
                        bool = false;
                        ois.close();
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            finally {
                ois.close();
            }
        }
        catch (IOException e3){

        }
        return listObject;
    }

    public static ArrayList ReadIds(String filepath) {
        ObjectInputStream ois = null;
        Object o;
        boolean bool = true;
        ArrayList listObject = new ArrayList();
        BufferedInputStream bif = null;

        try{
            bif = new BufferedInputStream(new FileInputStream(filepath));
            try {
                while (bool) {
                    ois = new ObjectInputStream(bif);
                    if (ois != null) {
                        o = ois.readObject();
                        listObject.add(((Article) o).getId());
                    } else {
                        bool = false;
                        ois.close();
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            finally {
                ois.close();
            }
        }
        catch (IOException e3){

        }
        return listObject;
    }

    public static void ReportArticle(Article article, String filepath) {

        ArrayList<Article> listObject = Function.Read(filepath);
        Iterator<Article> itr = listObject.iterator();

        while (itr.hasNext()) {
            Article obj = itr.next();
            if (article.getId() == (obj.getId())) {
                obj.setNbReport();
            }
        }

        File file = new File(filepath);
        file.delete();

        // réinjecter les objets un à un dans le fichier
        for (Article obj: listObject) {
            Create(obj, Article.FILE_PATH);
        }
    }

    public static void UpdateVisibility(Article article, boolean visibility, String filepath){

        ArrayList<Article> listObject = Function.Read(filepath);
        Iterator<Article> itr = listObject.iterator();

        while (itr.hasNext()) {
            Article obj = itr.next();
            if (article.getId() == obj.getId()) {
                obj.setVisible(visibility);
            }
        }

        File file = new File(filepath);
        file.delete();

        // réinjecter les objets un à un dans le fichier
        for (Object obj: listObject) {
            Create(obj, filepath);
        }
    }

    public static void Delete(Object o, String filepath) {

        ArrayList<Object> listObject = Function.Read(filepath);
        Iterator<Object> itr = listObject.iterator();

        while (itr.hasNext()) {
            Object obj = itr.next();
            if (o.toString().equals(obj.toString())) {
                itr.remove();
            }
        }

        File file = new File(filepath);
        if (file.delete())
            System.out.println("élément supprimé");

        //réinjecter les objets un à un dans le fichier
        if (!listObject.isEmpty()){
            for (Object obj: listObject) {
                Create(obj, filepath);
            }
        }
    }
}