package com.uqam.inf5153.tp3.algorithme;

import java.util.Map;

abstract public class StrategyJeu {

    public static StrategyJeu choisirAlgorithme(int numeroAlgorithme, int nbCases) {
        switch (numeroAlgorithme) {
            case 1:
                return new Algorithme1(nbCases);
            case 2:
                return new Algorithme2(nbCases);
            default:
                return new Algorithme3(nbCases);
        }
    }

    public abstract Map<String, Object> determinerGagnant(int position);
}
