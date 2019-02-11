package com.SnapAnonyme;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IHM {

    static Scanner scan = new Scanner(System.in);

    public static void Welcome() {
        System.out.println("" +
                "************************************************\n" +
                "*** Bienvenue dans l'application SnapAnonyme ***\n" +
                "************************************************\n");

        // si le fichier user.ser existe, afficher le menu Connexion, sinon afficher le menu Inscription
        Start();

    }

    // vérifie la taille/l'existance d'un User et lance l'inscription ou le choix de la connexion
    private static void Start() {
        ArrayList listUsers = Function.Read(User.FILE_PATH);
        System.out.println("Nombre d'inscrits : " + listUsers.size());

        if (listUsers.isEmpty())
            Inscription();
        else{
            StartChoices();
        }
    }

    // permet de choisir entre l'inscription ou la connexion au démarrage de l'application
    private static void StartChoices(){
        int choix = 0;
        while (true) {
            System.out.print("\n\t1. Inscription\n\t2. Connexion\n\t0. Sortir\n\n\tVotre choix : ");
            try{
                choix = scan.nextInt();
                switch (choix){
                    case 1 :
                        scan.nextLine();
                        Inscription();
                        break;
                    case 2 :
                        Connexion();
                        break;
                    case 0 : System.exit(0);
                        break;
                    default:
                        System.out.println("\nImpossible de lire votre choix. Reessayer.");
                        StartChoices();
                        break;
                }
            } catch (InputMismatchException e){
                System.out.println("\nImpossible de lire votre choix. Reessayer.");
                scan.nextLine();
            }
        }
    }

    // Gestion de l'inscription : récupère la saisie de l'utilisateur pour inscription = vérifie l'unicité du login
    private static void Inscription() {
        String userLogin, userPassword;
        System.out.print("\n" +
                "***** Créer compte utilisateur *****\n" +
                "\tLogin : ");

        userLogin = scan.nextLine();

        /** vérifie si le login n'est pas déjà existant*/
        ArrayList<User> ar = Function.Read(User.FILE_PATH);
        boolean loginDup = false;
        for (int i = 0; i < ar.size() ; i++) {
            if (ar.get(i).getLogin().equals(userLogin)){
                loginDup = true;
            }
        }
        if (loginDup == true){
            System.out.println("Ce login est déjà utilisé. Merci de bien vouloir choisir un autre login");
            StartChoices();
        }
        else{
            System.out.print("\tPassword : ");
            userPassword = scan.nextLine();

            User u = new User(userLogin, userPassword);
            System.out.println("Inscription effectuée. Bienvenue " + userLogin + " !");
            HomePage(u);
        }
    }

    // Gestion de la connexion = vérification du Login + password
    private static void Connexion() {
        scan.nextLine();
        String userLogin, userPassword;
        User currentUser = null;
        System.out.print("\n" +
                "***** Connexion *****\n" +
                "\tLogin : ");
        userLogin = scan.nextLine();
        System.out.print("\tPassword : ");
        userPassword = scan.nextLine();

        ArrayList<User> ar = Function.Read(User.FILE_PATH);
        boolean connexionOK = false;
        for (int i = 0; i < ar.size() ; i++) {
            if (ar.get(i).getLogin().equals(userLogin) && ar.get(i).getPassword().equals(userPassword)){
                connexionOK = true;
                currentUser = ar.get(i);
                break;
            }
            /** Appel les articles */
        }
        if (connexionOK == true){
            System.out.println("Connexion effectuée. Bienvenue " + userLogin + " !");
            HomePage(currentUser);
        }
        else {
            System.out.println("Login et/ou password incorrect");
            StartChoices();
        }
    }

    // Après connexion ou inscription : choix entre le menu Explorer et le menu Charger article
    private static void HomePage(User currentUser) {
        int choix=0;
        while (true){
            System.out.print("\n\t1. Explorer\n\t2. Créer article\n\t3. Gérer articles chargés\n\t0. Sortir\n\n\tVotre choix : ");
            try{
                choix = scan.nextInt();
                switch (choix){
                    case 1 :
                        Explore(currentUser);
                        break;
                    case 2 :
                        Upload(currentUser);
                        break;
                    case 3 :
                        GetUserArticles(currentUser);
                        break;
                    case 0 : System.exit(0);
                        break;
                    default:
                        System.out.println("\nImpossible de lire votre choix. Reessayer.");
                        scan.nextLine();
                        HomePage(currentUser);
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nImpossible de lire votre choix. Reessayer.");
                scan.nextLine();
            }
        }
    }

    // Gestion du menu Explorer : charge la liste des articles visibles
    private static void Explore(User currentUser){
        ArrayList<Article> listArticle = Function.Read(Article.FILE_PATH);

        ArrayList<Article> articlesVisibles = new ArrayList<Article>();

        for (Article e: listArticle) {
            if (e.getNbReport() >= 10)
                e.setVisible(false);
            if (e.isVisible()){
                articlesVisibles.add(e);
            }
        }

        System.out.println();
        System.out.println("Nombre d'articles visibles : " + articlesVisibles.size());
        DisplayArticles(currentUser, articlesVisibles);
    }

    private static void DisplayArticles(User currentUser, ArrayList<Article> articlesVisibles) {

        if (articlesVisibles.size() == 0){
            HomePage(currentUser);
        }
        else {
            System.out.println("\nListe des articles :");
            for (int i = 0; i < articlesVisibles.size() ; i++) {
                System.out.println("\t" + articlesVisibles.get(i));
            }
            SelectArticle(currentUser);
        }
    }

    // Gestion de la création des articles
    private static void Upload(User currentUSer){
        scan.nextLine();

        System.out.println("***** Créer un article *****\n");
        System.out.print("\tNom du fichier : ");
        String fileName = scan.nextLine();
        System.out.print("\tContenu : ");
        String content = scan.nextLine();
        boolean isVisible = ToggleArticleVisibility();

        new Article(currentUSer, new Fichier(fileName), content, isVisible);

        HomePage(currentUSer);
    }

    // Gestion du choix de la visibilité de l'article
    private static boolean ToggleArticleVisibility() {
        boolean isVisible = false;
        int choix=0;
        while (choix != 1 && choix != 2){
            System.out.print("\tRendre l'article visible ? \n\t1. Oui\n\t2. Non\n\tVotre choix : ");
            try{
                choix = scan.nextInt();
                switch (choix){
                    case 1 :
                        isVisible = true;
                        break;
                    case 2 :
                        isVisible = false;
                        break;
                    case 0 : System.exit(0);
                        break;
                    default:
                        System.out.println("\nImpossible de lire votre choix. Reessayer.");
                        scan.nextLine();
                        ToggleArticleVisibility();
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nImpossible de lire votre choix. Reessayer.");
                scan.nextLine();
            }
        }
        return isVisible;
    }

    // Récupérer la liste des article de l'utilisateur connecté
    private static void GetUserArticles(User currentUser){
        ArrayList<Article> listArticle = Function.Read(Article.FILE_PATH);

        ArrayList<Article> userArticles = new ArrayList<Article>();

        for (Article e: listArticle) {
            if (e.getUser().getId() == currentUser.getId() && e.getNbReport() < 10){
                userArticles.add(e);
            }
        }

        System.out.println();
        System.out.println("Nombre d'articles de l'utilisateur " + currentUser.getLogin() + " : " + userArticles.size());
        DisplayArticles(currentUser, userArticles);
    }

    // Selection de l'article par son Id
    private static void SelectArticle(User currentUser) {
        scan.nextLine();
        ArrayList<Comment> commList = Function.Read(Comment.FILE_PATH);
        ArrayList<Reaction> reactions = Function.Read(Reaction.FILE_PATH);
        ArrayList<Comment> commListArticle = new ArrayList<>();

        ArrayList<Article> listArticle = Function.Read(Article.FILE_PATH);

        boolean noReaction = true;

        int choix=0;
        while (true){
            System.out.print("\n\tRentrer numéro article pour voir les détails\n\t0. Retour\n\tVotre choix : ");
            try{
                choix = scan.nextInt();
                switch (choix){
                    case 0 : HomePage(currentUser);
                        break;
                    default:
                        for (Article e: listArticle) {
                            if (choix == e.getId()){
                                System.out.println("Voici l'article que vous avez choisi : \n\t" + e);
                                System.out.println("\t\tCommentaire(s) pour cette article : ");
                                for (Comment c:commList) {
                                    if (c.getArticle().getId() == choix){
                                        System.out.println("\t\t\t" + c);
                                            commListArticle.add(c);
                                    }
                                }
                                if (commListArticle.size() == 0)
                                    System.out.println("\t\t\tAucun commentaire pour le moment");
                                System.out.println("\t\tReaction(s) pour cette article : ");
                                for (Reaction r: reactions){
                                    if (r.getArticle().getId() == choix) {
                                        System.out.println("\t\t\t" + r);
                                        noReaction = false;
                                    }
                                }
                                if (noReaction)
                                    System.out.println("\t\t\tAucune reaction pour le moment");
                                if (e.getUser().getId() == currentUser.getId())
                                    ManageUserArticle(currentUser, e, commListArticle);
                                else
                                    ManageArticle(currentUser, e, commListArticle);
                            }
                        }
                        System.out.println("\nImpossible de lire votre choix. Réessayer.");
                        SelectArticle(currentUser);
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nImpossible de lire votre choix. Reessayer.");
                scan.nextLine();
            }
        }
    }

    // Gestion des articles d'un utilisateur
    private static void ManageUserArticle(User currentUser, Article article, ArrayList<Comment> commListArticle){
        scan.nextLine();
        int choix=0;
        while (true){
            System.out.print("\n\t1. Visibilité article\n\t2. Reagir/Commenter/Signaler\n\t3. Supprimer article\n\t0. Retour Accueil\n\tVotre choix : ");
            try{
                choix = scan.nextInt();
                switch (choix){
                    case 0 : HomePage(currentUser);
                        break;
                    case 1 :
                        Function.UpdateVisibility(article, ToggleArticleVisibility(), Article.FILE_PATH);
                        Explore(currentUser);
                        break;
                    case 2: ManageArticle(currentUser, article, commListArticle);
                        break;
                    case 3 :
                        Function.Delete(article, Article.FILE_PATH);
                        Function.Delete(article.getFichier(), Fichier.FILE_PATH);
                        Explore(currentUser);
                        break;
                    default:
                        System.out.println("\nImpossible de lire votre choix. Reessayer.");
                        ManageUserArticle(currentUser, article, commListArticle);
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nImpossible de lire votre choix. Reessayer.");
                scan.nextLine();
            }
        }
    }

    // Interaction avec des articles publiés
    private static void ManageArticle(User currentUser, Article article, ArrayList<Comment> commListArticle) {
        scan.nextLine();
        int choix=0;
        while (true){
            System.out.print("\n\t1. Commenter article\n\t2. Commenter commentaire\n\t3. Réagir\n\t4. Signaler\n\t0. Retour Accueil\n\tVotre choix : ");
            try{
                choix = scan.nextInt();
                switch (choix){
                    case 0 : HomePage(currentUser);
                        break;
                    case 1 :
                        AddComment(currentUser, article);
                        Explore(currentUser);
                        break;
                    case 2 :
                        AddCommentToComment(currentUser, article, commListArticle);
                        Explore(currentUser);
                        break;
                    case 3 :
                        AddReaction(currentUser, article, commListArticle);
                        Explore(currentUser);
                        break;
                    case 4 :
                        scan.nextLine();
                        Function.ReportArticle(article, Article.FILE_PATH);
                        Explore(currentUser);
                        break;
                    default:
                        System.out.println("\nImpossible de lire votre choix. Reessayer.");
                        ManageArticle(currentUser, article, commListArticle);
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nImpossible de lire votre choix. Reessayer.");
            }
        }
    }

    private static void AddComment(User currentUser, Article article){
        scan.nextLine();
        String content;

        System.out.print("\tCommentaire : ");
        content = scan.nextLine();

        new Comment(currentUser, article, null, content);
    }

    private static void AddComment(User currentUser, Article article, Comment comment){
        scan.nextLine();
        String content;

        System.out.print("\tRentrer commentaire : ");
        content = scan.nextLine();

        new Comment(currentUser, article, comment, content);
    }

    private static void AddCommentToComment(User currentUser, Article article, ArrayList<Comment> commListArticle){

        int choix=0;
        while (true){
            System.out.print("\n\tRentrer numéro du commentaire à commenter\n\t0. Retour\n\tVotre choix : ");
            try {
                choix = scan.nextInt();
                switch (choix) {
                    case 0:
                        HomePage(currentUser);
                        break;
                    default:
                        for (Comment c : commListArticle) {
                            if (choix == c.getId()) {
                                System.out.println("Voici le commentaire que vous avez choisi : \n\t" + c);
                                if (c.getComment() == null){
                                    AddComment(currentUser, article, c);
                                    Explore(currentUser);
                                }
                                else {
                                    System.out.println("Impossible de commenter ce commentaire");
                                    Explore(currentUser);
                                }
                            }
                            else {
                                System.out.println("\nImpossible de lire votre choix. Réessayer.");
                                SelectArticle(currentUser);
                                break;
                            }
                        }
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nImpossible de lire votre choix. Reessayer.");
                scan.nextLine();
            }
        }
    }

    // Réagir à un article
    private static void AddReaction(User currentUser, Article article, ArrayList commListArticle){
        scan.nextLine();

        System.out.println("\n***** Réagir *****");
        Reaction_Type rt = null;

        int choix=0;
        while (choix != 1 && choix != 2 && choix != 3 && choix != 4 && choix != 5){
            System.out.print("\n\t1. Aimer\n\t2. Ne pas aimer\n\t3. MDR!\n\t4. Adorer\n\t5. Détester\n\t0. Retour Accueil\n\tVotre choix : ");
            try{
                choix = scan.nextInt();
                switch (choix){
                    case 0 : ManageArticle(currentUser, article, commListArticle);
                        break;
                    case 1 : rt = Reaction_Type.Aimer;
                        break;
                    case 2 : rt = Reaction_Type.Ne_pas_aimer;
                        break;
                    case 3 : rt = Reaction_Type.MDR;
                        break;
                    case 4 : rt = Reaction_Type.Adorer;
                        break;
                    case 5 : rt = Reaction_Type.Détester;
                        break;
                    default:
                        System.out.println("\nImpossible de lire votre choix. Reessayer.");
                        AddReaction(currentUser, article, commListArticle);
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nImpossible de lire votre choix. Reessayer.");
            }
        }
        new Reaction(currentUser, article, rt);
    }
}

