package com.uqam.inf5153.tp3.parties;

import java.util.Observer;
import com.uqam.inf5153.tp3.utilitaires.TypePion.Pion;

class JoueurState implements Cloneable {

    private Observer observer;
    private boolean estMachine;
    private int position;
    private final Pion pionDuJoueur;
    private boolean estGagnant;

    public JoueurState(Pion pionDuJoueur) {
        this.pionDuJoueur = pionDuJoueur;
    }

    @Override
    public JoueurState clone() {
        JoueurState result = new JoueurState(this.pionDuJoueur);
        result.observer = this.observer;
        result.estGagnant = this.estGagnant;
        result.estMachine = this.estMachine;
        result.position = this.position;
        return result;
    }

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public boolean isEstMachine() {
        return estMachine;
    }

    public void setEstMachine(boolean estMachine) {
        this.estMachine = estMachine;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isEstGagnant() {
        return estGagnant;
    }

    public void setEstGagnant(boolean estGagnant) {
        this.estGagnant = estGagnant;
    }

    public Pion getPionDuJoueur() {
        return pionDuJoueur;
    }
}
