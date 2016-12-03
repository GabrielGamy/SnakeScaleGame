package com.uqam.inf5153.tp3.panneau;

import com.uqam.inf5153.tp3.utilitaires.Element;
import com.uqam.inf5153.tp3.utilitaires.TypeElement;
import com.uqam.inf5153.tp3.utilitaires.TypePion.Pion;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

public class PanJeu extends JPanel {

    public PanJeu(ArrayList elements, int nbCases) {
        this.elements = elements;
        this.nbCases = nbCases;
        this.listePositions = new int[0];
        super.setBackground(Color.ORANGE);
    }

    @Override
    public void paint(Graphics pen) {
        super.paint(pen);
        pen.setColor(Color.white);
        for (int position = 0; position < nbCases; ++position) {
            pen.setColor(couleurInverse(pen.getColor()));
            pen.fillRect(getX(position), getY(position), LARGEUR_CARRE, LONGUEUR_CARRE);
        }
        dessinerElements(pen);
        mise_a_jour_pions(pen);
    }

    private void dessinerElements(Graphics pen) {
        Color couleurEchelle = Color.BLUE;
        Color couleurSerpent = Color.RED;

        for (Element elem : elements) {
            if (elem.getType() == TypeElement.typeElement.ECHELLE) {
                pen.setColor(couleurEchelle);
                pen.drawLine(getX(elem.getDebut()) + (ajustementX(elem.getDebut())), getY(elem.getDebut()) + ajustementY(elem.getDebut()), getX(elem.getFin()) + ajustementX(elem.getFin()), getY(elem.getFin()) + ajustementY(elem.getFin()));
            } else if (elem.getType() == TypeElement.typeElement.SERPENT) {
                pen.setColor(couleurSerpent);
                pen.drawLine(getX(elem.getDebut()) + ajustementX(elem.getDebut()), getY(elem.getDebut()) + ajustementY(elem.getDebut()), getX(elem.getFin()) + ajustementX(elem.getFin()), getY(elem.getFin()) + ajustementY(elem.getFin()));
            }
        }
    }

    private int ajustementX(int position) {
        int ligne = position / NB_COLONNES;
        if ((ligne % 2) == 0) {
            if (position % 10 == 0) {
                return +15;
            }
            return -15;
        } else {
            if (position % 10 == 0) {
                return +15;
            }
            return +45;
        }
    }

    private int ajustementY(int position) {

        if (position % 10 == 0) {
            return +45;
        }
        return +15;

    }

    private void mise_a_jour_pions(Graphics pen) {
        System.out.println("size() :" + listePositions.length + ", elements : " + Arrays.toString(listePositions));
        for (int i = 0; i < listePositions.length; i++) {
            int position = listePositions[i] - 1;
            if (position >= 0) {
                pen.setColor((getCouleurPion(LISTE_DES_PIONS[i])));
                pen.fillOval(getX(position), getY(position), LARGEUR_CARRE, LONGUEUR_CARRE);
            }
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

    private Color couleurInverse(Color couleurActuelle) {
        if (couleurActuelle.equals(Color.white)) {
            return Color.black;
        } else {
            return Color.white;
        }
    }

    private int getX(int position) {
        if (estLignePaire(position)) {
            return getX_GaucheVersDroite(position);
        } else {
            return getX_DroiteVersGauche(position);
        }
    }

    private boolean estLignePaire(int position) {
        int ligne = position / NB_COLONNES;
        return (ligne % 2) == 0;
    }

    /**
     * Les elements sont dessines de la gauche vers la droite pour les lignes de
     * chiffre pair
     */
    private int getX_GaucheVersDroite(int position) {
        return (position % NB_COLONNES) * LARGEUR_CARRE;
    }

    /**
     * Les elements sont dessines de la droite vers la gauche pour les lignes de
     * chiffre impair
     */
    private int getX_DroiteVersGauche(int position) {
        int largeurDeLigne = LARGEUR_CARRE * NB_COLONNES;
        int decalage = getX_GaucheVersDroite(position) + LARGEUR_CARRE;
        return largeurDeLigne - decalage;
    }

    private int getY(int position) {
        int taillePanneau = 600;
        return taillePanneau - (position / NB_COLONNES) * LONGUEUR_CARRE;
    }

    public int[] getListePositions() {
        return listePositions;
    }

    public void setListePositions(int[] listePositions) {
        this.listePositions = listePositions;
        update(this.getGraphics());
    }

    private int[] listePositions;
    private ArrayList<Element> elements;
    private int nbCases;
    private final int NB_COLONNES = 10;
    private final int LONGUEUR_CARRE = 30;
    private final int LARGEUR_CARRE = 30;
    private final Pion[] LISTE_DES_PIONS = new Pion[]{
        Pion.BLEU_CIEL, Pion.VERT,
        Pion.ROSE, Pion.ORANGE,
        Pion.BLEU, Pion.ROUGE
    };
}
