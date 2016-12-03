package com.uqam.inf5153.tp3.parties;

import com.uqam.inf5153.tp3.algorithme.StrategyJeu;
import com.uqam.inf5153.tp3.gestionnaires.GestionPartie;
import com.uqam.inf5153.tp3.gestionnaires.GestionPartieImpl;
import com.uqam.inf5153.tp3.utilitaires.Element;
import com.uqam.inf5153.tp3.utilitaires.TypePion.Pion;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Partie implements PartieImpl {

    private StrategyJeu strategieDuJeu;
    private Des desDuJeu;
    private Joueur[] listeDesJoueurs;
    private ArrayList<Element> listeElements;
    private GardienMemento mementoList;
    private GestionPartieImpl gestionPartie;

    public Partie() {
        mementoList = new GardienMemento();
        gestionPartie = new GestionPartie();
    }

    @Override
    public Joueur[] nouvellePartie(ConfigPartie configurations, ArrayList<Element> listeElements) {
        int nbJoueurs = configurations.getNbJoueurs();
        int nbJoueursMachines = configurations.getNbJoueursMachine();
        int numeroAlgorithme = configurations.getNumeroAlgorithme();
        int nbCases = configurations.getNbCases();
        this.listeElements = listeElements;
        Pion[] LISTE_DES_PIONS = new Pion[]{
            Pion.BLEU_CIEL, Pion.VERT, Pion.ROSE, Pion.ORANGE, Pion.BLEU, Pion.ROUGE
        };

        strategieDuJeu = StrategyJeu.choisirAlgorithme(numeroAlgorithme, nbCases);
        desDuJeu = new Des(configurations.getTypeDes());
        listeDesJoueurs = new Joueur[nbJoueurs];

        for (int i = listeDesJoueurs.length - 1; i >= 0; i--) {
            listeDesJoueurs[i] = new Joueur(LISTE_DES_PIONS[i]);
            if (i >= (nbJoueurs - nbJoueursMachines)) {
                listeDesJoueurs[i].setEstMachine(true);
            }
        }

        return listeDesJoueurs;
    }

    @Override
    public boolean estUnJoueurMachine(Pion pion) {
        Joueur joueur = getJoueur(pion);
        return joueur.isEstMachine();
    }

    @Override
    public void jouerCoup(Pion pion) {
        Joueur joueur = getJoueur(pion);
        mementoList.addUndoMemento(joueur.createMemento());

        int nouvellePosition = joueur.getPosition() + brasse();
        boolean estGagnant = false;
        boolean passerSonTour = false;

        for (Element elem : listeElements) {
            if (elem.getDebut() == nouvellePosition) {
                nouvellePosition = elem.getFin();
                break;
            }
        }

        Map<String, Object> resultat = strategieDuJeu.determinerGagnant(nouvellePosition);
        nouvellePosition = (int) resultat.get("nouvellePosition");
        estGagnant = (boolean) resultat.get("victoire");
        passerSonTour = (boolean) resultat.get("passerLeTour");

        if (passerSonTour) {
            nouvellePosition = joueur.getPosition(); // Selon l'algorithme 2, on change rien
        }
        joueur.setEstGagnant(estGagnant);
        joueur.setPosition(nouvellePosition);
        mementoList.addRedoMemento(joueur.createMemento());
    }

    @Override
    public void sauvegarderPartie() {
        gestionPartie.sauvegarderPartie(this);
    }

    @Override
    public boolean chargerPartieExiste() {
        return gestionPartie.IsFichierPartieExiste();
    }

    @Override
    public Partie chargerPartie(int nbCases) {
        return gestionPartie.chargerPartie(this, nbCases);
    }

    private int brasse() {
        Random rand = new Random();
        return rand.nextInt(getIntervalle()) + 1;
    }

    private int getIntervalle() {
        switch (desDuJeu.getTypeDes()) {
            case SIX:
                return 6;
            case HUIT:
                return 8;
            default:
                return 20;
        }
    }

    private Joueur getJoueur(Pion pion) {
        Joueur joueur = listeDesJoueurs[0];
        for (int i = 0; i < listeDesJoueurs.length; ++i) {
            if (listeDesJoueurs[i].getPionDuJoueur() == pion) {
                joueur = listeDesJoueurs[i];
                break;
            }
        }
        return joueur;
    }

    // Getters utilisés dans la sauvegarde
    public StrategyJeu getStrategie() {
        return strategieDuJeu;
    }

    public Des getDes() {
        return desDuJeu;
    }

    public Joueur[] getJoueurs() {
        return listeDesJoueurs;
    }

    public ArrayList<Element> getlisteElements() {
        return listeElements;
    }

    // Setters utilisés dans le chargement
    public void setStrategie(StrategyJeu strategie) {
        strategieDuJeu = strategie;
    }

    public void setDes(Des des) {
        desDuJeu = des;
    }

    public void setJoueurs(Joueur[] joueurs) {
        listeDesJoueurs = joueurs;
    }

    public void setElements(ArrayList<Element> elements) {
        listeElements = elements;
    }

    @Override
    public boolean annulerCoup() {
        JoueurMemento leBackUp = mementoList.getUndoMemento();
        if (leBackUp != null) {
            Pion pionDuJoueur = leBackUp.getState().getPionDuJoueur();
            Joueur joueurPrecedent = getJoueur(pionDuJoueur);
            joueurPrecedent.setMemento(leBackUp);
            return true;
        }
        return false;
    }

    @Override
    public boolean revenirCoup() {
        JoueurMemento leBackUp = mementoList.getRedoMemento();
        if (leBackUp != null) {
            Pion pionDuJoueur = leBackUp.getState().getPionDuJoueur();
            Joueur joueurSuivant = getJoueur(pionDuJoueur);
            joueurSuivant.setMemento(leBackUp);
            return true;
        }
        return false;
    }

}
