package com.uqam.inf5153.tp3.gestionnaires;

import com.uqam.inf5153.tp3.utilitaires.Element;
import java.util.ArrayList;

public interface GestionPanneauImp {

    public ArrayList<Element> construireElements();

    public void chargerConfigurationsPanneau();

    public void sauvegarderConfigurationsPanneau(int nbCases, int nbSerpents, int nbEchelles);

    public Boolean isConfigExiste();
    
    public int getNbCases();
    public int getNbSerpents();
    public int getNbEchelles();
    
}
