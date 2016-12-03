package com.uqam.inf5153.tp3.parties;

import java.util.Observable;
import java.util.Observer;
import com.uqam.inf5153.tp3.utilitaires.TypePion.Pion;
import java.util.HashMap;
import java.util.Map;

public class Joueur extends Observable {

    private JoueurState state;

    public Joueur(Pion pionDuJoueur) {
        state = new JoueurState(pionDuJoueur);
        state.setPosition(0);
        state.setEstGagnant(false);
        state.setEstMachine(false);
    }

    @Override
    public void addObserver(Observer observer) {
        state.setObserver(observer);
    }

    @Override
    public void notifyObservers() {
        Map infosJoueurs = new HashMap();
        infosJoueurs.put("pionDuJoueur", state.getPionDuJoueur());
        infosJoueurs.put("position", state.getPosition());
        infosJoueurs.put("estGagnant", state.isEstGagnant());
        state.getObserver().update(this, infosJoueurs);
    }

    public boolean isEstMachine() {
        return state.isEstMachine();
    }

    public void setEstMachine(boolean estMachine) {
        state.setEstMachine(estMachine);
    }

    public int getPosition() {
        return state.getPosition();
    }

    public void setPosition(int position) {
        state.setPosition(position);
        notifyObservers();
    }

    // Utilisé dans le chargement d'une partie où il n'y a pas encore d'observeurs
    public void setPositionChargement(int position) {
        state.setPosition(position);
    }

    public Pion getPionDuJoueur() {
        return state.getPionDuJoueur();
    }

    @Override
    public String toString() {
        return state.getPionDuJoueur() + "--> " + state.getPosition();
    }

    public boolean isEstGagnant() {
        return state.isEstGagnant();
    }

    public void setEstGagnant(boolean estGagnant) {
        state.setEstGagnant(estGagnant);
    }

    // getter des attributs en string pour le parser XML
    public String[][] getAttributs() {
        String[][] attributs = {{"estMachine", String.valueOf(state.isEstMachine())},
        {"position", String.valueOf(state.getPosition())},
        {"pionDuJoueur", String.valueOf(state.getPionDuJoueur())},
        {"estGagnant", String.valueOf(state.isEstGagnant())}};
        return attributs;
    }

    public JoueurMemento createMemento() {
        return new JoueurMemento(state);
    }

     public void setMemento(JoueurMemento memento) {
        state = memento.getState().clone();
        setPosition(state.getPosition());
    }
}
