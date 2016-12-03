package com.uqam.inf5153.tp3.panneau;

import com.uqam.inf5153.tp3.gestionnaires.GestionPanneauImp;
import com.uqam.inf5153.tp3.gestionnaires.GestionPanneau;
import com.uqam.inf5153.tp3.parties.ConfigPartie;
import com.uqam.inf5153.tp3.parties.Partie;
import com.uqam.inf5153.tp3.utilitaires.Element;
import com.uqam.inf5153.tp3.utilitaires.TypePion.Pion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class Panneau extends JFrame implements PanneauImpl, ActionListener, Observer {

    Timer time;

    public Panneau() {
        super("Jeu de serpent et echelle");
        contenu = super.getContentPane();
        super.setBounds(0, 0, 600, 730);
        super.setResizable(false);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gestionPanneau = new GestionPanneau();
        acceuil();
        super.setVisible(true);
    }

    private void acceuil() {
        if (verifierConfigExiste()) {
            chargerConfigurationsPanneau();
        } else {
            btnConfigurerPanneau = new JButton("Configurer le panneau de jeu");
            btnConfigurerPanneau.addActionListener(this);
            btnConfigurerPanneau.setActionCommand("CONFIGURER_PANNEAU");
            contenu.setLayout(new FlowLayout());
            contenu.add(btnConfigurerPanneau);
        }
    }

    private boolean verifierConfigExiste() {
        return gestionPanneau.isConfigExiste();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("CONFIGURER_PANNEAU")) {
            demanderConfigurationsPanneau();
        } else if (e.getActionCommand().equals("NOUVELLE_PARTIE")) {
            demanderConfigurationsPartie();
        } else if (e.getActionCommand().equals("CHARGER_PARTIE")) {
            preparerChargerPartie();
        } else if (e.getActionCommand().equals("LANCER") && !finDePartie) {
            Pion pionDuJoueur = (Pion) LISTE_DES_PIONS[prochainJoueur++];
            partieCourante.jouerCoup(pionDuJoueur);
        } else if (e.getActionCommand().equals("ENREGISTRER_PARTIE")) {
            if (partieCourante != null) {
                partieCourante.sauvegarderPartie();
            }
        } else if (e.getActionCommand().equals("REDO")) {
            cancelTimer();
            prochainJoueur++;
            if (!partieCourante.revenirCoup()) {
                prochainJoueur--;
                JOptionPane.showMessageDialog(null, "Aucun coup disponible");
            }
        } else if (e.getActionCommand().equals("UNDO")) {
            cancelTimer();
            prochainJoueur--;
            if (!partieCourante.annulerCoup()) {
                prochainJoueur++;
                JOptionPane.showMessageDialog(null, "Aucun coup disponible");
            }
        }
    }

    @Override
    public void demanderConfigurationsPanneau() {
        panDialogue = new PanDialog(this, "Configuration du panneau");
        listAttributsConfigPanneau = panDialogue.demanderConfigurationsPanneau();
        panDialogue.dispose();
        if (listAttributsConfigPanneau == null) {
            if (verifierConfigExiste()) {
                listAttributsConfigPanneau = new HashMap<>();
                chargerConfigurationsPanneau();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Vous devez choisir des configurations de panneau valides pour continuer");
            }
        } else {
            System.out.println("Configuartion du panneau :" + listAttributsConfigPanneau.toString());
            contenu.removeAll();
            contenu.repaint();
            dessinerMenuPrincipal();
            gestionPanneau.sauvegarderConfigurationsPanneau(
                    listAttributsConfigPanneau.get("nbCases"),
                    listAttributsConfigPanneau.get("nbSerpents"),
                    listAttributsConfigPanneau.get("nbEchelles"));
        }
    }

    @Override
    public void chargerConfigurationsPanneau() {
        gestionPanneau.chargerConfigurationsPanneau();
        listAttributsConfigPanneau.put("nbCases", gestionPanneau.getNbCases());
        listAttributsConfigPanneau.put("nbSerpents", gestionPanneau.getNbSerpents());
        listAttributsConfigPanneau.put("nbEchelles", gestionPanneau.getNbEchelles());
        dessinerMenuPrincipal();
    }

    @Override
    public void demanderConfigurationsPartie() {
        panDialogue = new PanDialog(this, "Configuration de la partie");
        listAttributsConfigPartie = panDialogue.demanderConfigurationsPartie();
        panDialogue.dispose();
        if (listAttributsConfigPartie == null) {
            JOptionPane.showMessageDialog(null,
                    "Vous devez choisir des configurations de partie valides pour continuer");
        } else {
            System.out.println("Configuartion de la partie :" + listAttributsConfigPartie.toString());
            if (reconfiguerPanneau() == JOptionPane.YES_OPTION) {
                demanderConfigurationsPanneau();
            }
            nouvellePartie();
        }
    }

    public int reconfiguerPanneau() {
        Object[] options = {"Oui je souhaite le faire", "Non garder cette configuration"};
        String message = "Voulez-vous choisir des nouvelles configurations du panneau ?";
        int choix = JOptionPane.showOptionDialog(null, message,
                "Reconfigurer le panneau", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return choix;
    }

    private void dessinerMenuPrincipal() {
        setLayout(new BorderLayout());
        iconBoutton = new ImageIcon(images_path + "/start.png");
        btnDemarerPartie = new JButton(iconBoutton);
        btnDemarerPartie.setText("Nouvelle Partie");
        btnDemarerPartie.setActionCommand("NOUVELLE_PARTIE");
        btnDemarerPartie.addActionListener(this);

        iconBoutton = new ImageIcon(images_path + "/load.png");
        btnChargerPartie = new JButton(iconBoutton);
        btnChargerPartie.setText("Charger une partie");
        btnChargerPartie.setActionCommand("CHARGER_PARTIE");
        btnChargerPartie.addActionListener(this);

        iconBoutton = new ImageIcon(images_path + "/save.png");
        btnSauvegarderPartie = new JButton(iconBoutton);
        btnSauvegarderPartie.setText("Enregistrer la partie");
        btnSauvegarderPartie.setActionCommand("ENREGISTRER_PARTIE");
        btnSauvegarderPartie.addActionListener(this);

        iconBoutton = new ImageIcon(images_path + "/next16px.png");
        btnRedo = new JButton(iconBoutton);
        btnRedo.setText("Redo");
        btnRedo.setActionCommand("REDO");
        btnRedo.addActionListener(this);
        btnRedo.setHorizontalTextPosition(SwingConstants.LEFT);

        iconBoutton = new ImageIcon(images_path + "/left16px.png");
        btnUndo = new JButton(iconBoutton);
        btnUndo.setText("Undo");
        btnUndo.setActionCommand("UNDO");
        btnUndo.addActionListener(this);

        btnUndo.setEnabled(false);
        btnRedo.setEnabled(false);

        menu = new JToolBar();
        menu.add(btnUndo);
        menu.add(btnRedo);
        menu.add(btnDemarerPartie);
        menu.add(btnChargerPartie);
        menu.add(btnSauvegarderPartie);

        contenu.add(menu, BorderLayout.NORTH);
        validate();
    }

    public void preparerChargerPartie() {
        if (partieCourante == null) {
            partieCourante = new Partie();
        }
        if (partieCourante.chargerPartieExiste()) {
            cancelTimer();
            prochainJoueur = 0;
            finDePartie = false;
            partieCourante = new Partie();
            partieCourante = partieCourante.chargerPartie(listAttributsConfigPanneau.get("nbCases"));

            listeDesSujetsObserves = partieCourante.getJoueurs();

            for (int i = 0; i < listeDesSujetsObserves.length; ++i) {
                listeDesSujetsObserves[i].addObserver(this);
            }

            genererPanneau(true);
            jouer();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Aucune Partie n'est sauvegardée");
        }
    }

    private void nouvellePartie() {
        cancelTimer();
        prochainJoueur = 0;
        finDePartie = false;
        partieCourante = new Partie();
        elementsDuPanneau = gestionPanneau.construireElements();
        ConfigPartie configurations = new ConfigPartie(
                listAttributsConfigPartie.get("typeDes"),
                listAttributsConfigPartie.get("nbJoueurs"),
                listAttributsConfigPartie.get("nbJoueurMachines"),
                listAttributsConfigPartie.get("typeAlgorithme"),
                listAttributsConfigPanneau.get("nbCases"));

        listeDesSujetsObserves = partieCourante.nouvellePartie(configurations, elementsDuPanneau);
        for (int i = 0; i < listeDesSujetsObserves.length; ++i) {
            listeDesSujetsObserves[i].addObserver(this);
        }
        genererPanneau(false);
        jouer();
    }

    @Override
    public void genererPanneau(boolean chargerPartie) {
        reinitialiserPanneau();
        menuJeu = new JToolBar();
        menuJeu.setLayout(new GridLayout(8, 1));
        if (!chargerPartie) {
            elementsDuPanneau = gestionPanneau.construireElements();
        } else {
            elementsDuPanneau = partieCourante.getlisteElements();
        }
        panJeu = new PanJeu(elementsDuPanneau, (int) listAttributsConfigPanneau.get("nbCases"));
        contenu.add(menuJeu, BorderLayout.WEST);
        contenu.add(panJeu);

        initPions(chargerPartie);

        dessinerMenuJeu(chargerPartie);
        validate();
    }

    private void reinitialiserPanneau() {
        if (menuJeu != null) {
            contenu.remove(menuJeu);
            contenu.remove(panJeu);
            panJeu = null;
            menuJeu = null;
        }
        btnUndo.setEnabled(true);
        btnRedo.setEnabled(true);
    }

    private void initPions(boolean chargerPartie) {
        int nbJoueurs = partieCourante.getJoueurs().length;
        positionsDesPions = new int[nbJoueurs];
        for (int i = 0; i < nbJoueurs; ++i) {
            if (!chargerPartie) {
                positionsDesPions[i] = 0;
            } else {
                positionsDesPions[i] = partieCourante.getJoueurs()[i].getPosition();
            }
        }
    }

    private void dessinerMenuJeu(boolean chargerPartie) {

        if (menuJeu.getComponentCount() == 0) {

            iconBoutton = new ImageIcon(images_path + "/thimble.png");
            btnBrasserDes = new JButton(iconBoutton);
            btnBrasserDes.setText("Lancer");
            btnBrasserDes.setActionCommand("LANCER");
            btnBrasserDes.addActionListener(this);
            menuJeu.add(btnBrasserDes);

            prochainTour = new JButton(textProchain());
            menuJeu.add(prochainTour);

            int nbJoueurs = partieCourante.getJoueurs().length;
            liste_btnDesJoueurs = new JButton[nbJoueurs];

            for (int index = 0; index < nbJoueurs; index++) {
                String infos;
                Pion cle = LISTE_DES_PIONS[index];
                if (!chargerPartie) {
                    infos = "Joueur " + (index + 1) + "- Case 0";
                } else {
                    infos = "Joueur " + (index + 1) + "- Case " + partieCourante.getJoueurs()[index].getPosition();
                }
                JButton infosDuJoueur = new JButton(infos);
                infosDuJoueur.setBackground(getCouleurPion(cle));
                liste_btnDesJoueurs[index] = infosDuJoueur;
                menuJeu.add(infosDuJoueur);
            }
        } else {
            updateMenuJeu();
        }
        panJeu.setListePositions(positionsDesPions);
        validate();
    }

    private void updateMenuJeu() {
        int nombre_composants = menuJeu.getComponentCount();
        for (int index = 1; index < nombre_composants; ++index) {
            if (index == 1) {
                // Mise a jour du text Prochain joueur
                prochainTour.setText(textProchain());
            } else {
                int indexJoueur = index - 2;
                int postion = positionsDesPions[indexJoueur];
                String infos = "Joueur " + (indexJoueur + 1) + "- Case " + postion;
                liste_btnDesJoueurs[indexJoueur].setText(infos);
            }
        }
    }

    private String textProchain() {
        if (finDePartie) {
            return "Fin de partie";
        } else {
            return "Prochain : Joueur " + (prochainJoueur + 1);
        }
    }

    private Color getCouleurPion(Pion pion) {
        Color result = Color.PINK;
        switch (pion) {
            case ROUGE:
                result = Color.RED;
                break;
            case VERT:
                result = Color.GREEN;
                break;
            case BLEU:
                result = Color.BLUE;
                break;
            case BLEU_CIEL:
                result = Color.CYAN;
                break;
            case ORANGE:
                result = Color.ORANGE;
                break;
            case ROSE:
                result = Color.PINK;
                break;
        }
        return result;
    }

    private int getIndex(Pion pion) {
        int result = 0;
        switch (pion) {
            case BLEU_CIEL:
                result = 0;
                break;
            case VERT:
                result = 1;
                break;
            case ROSE:
                result = 2;
                break;
            case ORANGE:
                result = 3;
                break;
            case BLEU:
                result = 4;
                break;
            case ROUGE:
                result = 5;
                break;
        }
        return result;
    }

    public void cancelTimer() {
        if (time != null) {
            time.cancel();
            time.purge();
        }
    }

    public void jouer() {
        final Pion pionDuJoueur = (Pion) LISTE_DES_PIONS[prochainJoueur];
        if (partieCourante.estUnJoueurMachine(pionDuJoueur)) {

            btnBrasserDes.setEnabled(false);

            int intervale = 500;
            Date runTime = new Date(System.currentTimeMillis() + intervale);
            time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    cancelTimer();
                    prochainJoueur++;
                    partieCourante.jouerCoup(pionDuJoueur);
                }
            }, runTime);
        } else {
            btnBrasserDes.setEnabled(true);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Map infosJoueurs = (HashMap) arg;
        Pion pionCourant = (Pion) infosJoueurs.get("pionDuJoueur");
        int nouvellePosition = (int) infosJoueurs.get("position");
        boolean estGagnant = (boolean) infosJoueurs.get("estGagnant");
        //int nbJoueurs = (int) listAttributsConfigPartie.get("nbJoueurs");
        int nbJoueurs = partieCourante.getJoueurs().length;

        positionsDesPions[getIndex(pionCourant)] = nouvellePosition;

        if (prochainJoueur >= nbJoueurs) {
            prochainJoueur = 0;
        } else if (prochainJoueur <= -1) {
            prochainJoueur = nbJoueurs - 1;
        }

        dessinerMenuJeu(false);
        if (estGagnant) {
            finDePartie = true;
            JOptionPane.showMessageDialog(null, "Gagnant de la partie : " + pionCourant);
        } else {
            jouer();
        }
    }

    public Partie partieCourante;
    public Observable[] listeDesSujetsObserves;

    private final Container contenu;
    private PanJeu panJeu;
    private PanDialog panDialogue;

    private JToolBar menu;
    private JToolBar menuJeu;

    private ImageIcon iconBoutton;

    private JButton btnSauvegarderPartie;
    private JButton btnChargerPartie;
    private JButton btnDemarerPartie;
    private JButton btnConfigurerPanneau;
    private JButton btnBrasserDes;
    private JButton btnUndo;
    private JButton btnRedo;
    private JButton prochainTour;
    private JButton[] liste_btnDesJoueurs;

    private ArrayList<Element> elementsDuPanneau = new ArrayList<>();
    private Map<String, Integer> listAttributsConfigPanneau = new HashMap();
    private Map<String, Integer> listAttributsConfigPartie = new HashMap();
    private final Pion[] LISTE_DES_PIONS = new Pion[]{
        Pion.BLEU_CIEL, Pion.VERT,
        Pion.ROSE, Pion.ORANGE,
        Pion.BLEU, Pion.ROUGE
    };

    private int[] positionsDesPions;
    private int prochainJoueur = 0;
    private boolean finDePartie = false;

    private final String images_path = "src/resources/images";

    GestionPanneauImp gestionPanneau;
}
