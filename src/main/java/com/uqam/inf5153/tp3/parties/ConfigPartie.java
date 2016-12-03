package com.uqam.inf5153.tp3.parties;

public class ConfigPartie {

    private final int typeDes;
    private final int nbJoueurs;
    private final int nbJoueursMachine;
    private final int numeroAlgorithme;
    private final int nbCases;

    public ConfigPartie(int typeDes, int nbJoueurs, int nbJoueursMachine, int numeroAlgorithme, int nbCases) {
        this.typeDes = typeDes;
        this.nbJoueurs = nbJoueurs;
        this.nbJoueursMachine = nbJoueursMachine;
        this.numeroAlgorithme = numeroAlgorithme;
        this.nbCases = nbCases;
    }

    public int getTypeDes() {
        return typeDes;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public int getNbJoueursMachine() {
        return nbJoueursMachine;
    }

    public int getNumeroAlgorithme() {
        return numeroAlgorithme;
    }

    public int getNbCases() {
        return nbCases;
    }
}
