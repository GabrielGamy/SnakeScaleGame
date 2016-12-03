package com.uqam.inf5153.tp3.panneau;

public interface PanneauImpl {

    public void demanderConfigurationsPanneau();

    public void demanderConfigurationsPartie();

    public void chargerConfigurationsPanneau();

    public void genererPanneau(boolean chargerPartie);
}
