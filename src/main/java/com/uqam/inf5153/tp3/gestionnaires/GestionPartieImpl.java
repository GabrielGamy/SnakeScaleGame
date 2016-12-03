package com.uqam.inf5153.tp3.gestionnaires;

import com.uqam.inf5153.tp3.parties.Partie;

public interface GestionPartieImpl {

    public void sauvegarderPartie(Partie partie);

    public Partie chargerPartie(Partie partieCourante, int nbCases);
    
    public boolean IsFichierPartieExiste();
}
