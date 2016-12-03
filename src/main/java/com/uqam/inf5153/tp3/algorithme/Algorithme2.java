package com.uqam.inf5153.tp3.algorithme;

import java.util.HashMap;
import java.util.Map;

public class Algorithme2 extends StrategyJeu {

    private final int nbCases;

    public Algorithme2(int nbCases) {
        this.nbCases = nbCases;
    }

    @Override
    public Map<String, Object> determinerGagnant(int position) {
        Map<String, Object> resultat = new HashMap<>();
        boolean victoire = (position == nbCases);
        boolean passerLeTour = (position > nbCases);

        resultat.put("nouvellePosition", position);
        resultat.put("victoire", victoire);
        resultat.put("passerLeTour", passerLeTour);
        return resultat;
    }
}
