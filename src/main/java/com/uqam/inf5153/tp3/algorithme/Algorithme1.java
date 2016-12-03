package com.uqam.inf5153.tp3.algorithme;

import java.util.HashMap;
import java.util.Map;

public class Algorithme1 extends StrategyJeu {

    private final int nbCases;

    public Algorithme1(int nbCases) {
        this.nbCases = nbCases;
    }

    @Override
    public Map<String, Object> determinerGagnant(int position) {
        Map<String, Object> resultat = new HashMap<>();
        boolean victoire = (position >= nbCases);
        if (victoire) {
            position = nbCases;
        }
        resultat.put("nouvellePosition", position);
        resultat.put("victoire", victoire);
        resultat.put("passerLeTour", false);// Toujours faux pour l'algorithme 1
        return resultat;
    }
}
