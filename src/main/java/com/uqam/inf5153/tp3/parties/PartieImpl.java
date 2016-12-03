package com.uqam.inf5153.tp3.parties;

import com.uqam.inf5153.tp3.utilitaires.Element;
import com.uqam.inf5153.tp3.utilitaires.TypePion.Pion;
import java.util.ArrayList;

public interface PartieImpl {

    public Joueur[] nouvellePartie(ConfigPartie configurations, ArrayList<Element> listeElements);

    public boolean estUnJoueurMachine(Pion pion);

    public void jouerCoup(Pion pion);

    public void sauvegarderPartie();

    public Partie chargerPartie(int nbCases);
    
    public boolean chargerPartieExiste();

    public boolean annulerCoup();

    public boolean revenirCoup();
}
