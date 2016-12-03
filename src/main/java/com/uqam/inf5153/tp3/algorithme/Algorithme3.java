package com.uqam.inf5153.tp3.algorithme;

import java.util.HashMap;
import java.util.Map;

public class Algorithme3 extends StrategyJeu {

    private final int nbCases;

    public Algorithme3(int nbCases) {
        this.nbCases = nbCases;
    }

    @Override
    public Map<String, Object> determinerGagnant(int position) {
        Map<String, Object> resultat = new HashMap<>();
        boolean victoire = (position == nbCases);
        if (position > nbCases) {
            position = nbCases - (position - nbCases);
        }
        resultat.put("nouvellePosition", position);
        resultat.put("victoire", victoire);
        resultat.put("passerLeTour", false);// Toujours faux pour l'algorithme 3
        return resultat;
    }

}
