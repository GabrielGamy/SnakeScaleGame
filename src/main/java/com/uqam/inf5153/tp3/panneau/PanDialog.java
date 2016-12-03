package com.uqam.inf5153.tp3.panneau;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

class PanDialog extends JDialog implements ActionListener {

    public PanDialog(JFrame parent, String titre) {
        super(parent, titre, true);
        super.setBounds(0, 0, 400, 200);
        super.setResizable(false);
        btnValiderConfigurations = new JButton("Valider");
    }

    public Map demanderConfigurationsPanneau() {
        configPanneauLabels = new HashMap();
        configPanneauInputs = new HashMap();

        configPanneauLabels.put("nbCases", new JLabel("Nombre de cases (40-200): ", JLabel.RIGHT));
        configPanneauInputs.put("nbCases", new JTextField(3));

        configPanneauLabels.put("nbSerpents", new JLabel("Nombre de serpents: ", JLabel.RIGHT));
        configPanneauInputs.put("nbSerpents", new JTextField(3));

        configPanneauLabels.put("nbEchelles", new JLabel("Nombre echelles: ", JLabel.RIGHT));
        configPanneauInputs.put("nbEchelles", new JTextField(3));

        btnValiderConfigurations.setActionCommand("VALIDER_CONFIG_PANNEAU");
        btnValiderConfigurations.addActionListener(this);

        contenu = getContentPane();
        contenu.setLayout(new GridLayout(4, 2, 15, 15));

        contenu.add((JLabel) configPanneauLabels.get("nbCases"));
        contenu.add((JTextField) configPanneauInputs.get("nbCases"));

        contenu.add((JLabel) configPanneauLabels.get("nbSerpents"));
        contenu.add((JTextField) configPanneauInputs.get("nbSerpents"));

        contenu.add((JLabel) configPanneauLabels.get("nbEchelles"));
        contenu.add((JTextField) configPanneauInputs.get("nbEchelles"));

        contenu.add(btnValiderConfigurations);

        setVisible(true);
        return listeConfigurations;
    }

    public Map demanderConfigurationsPartie() {
        configPartieLabels = new HashMap();
        configPartieInputs = new HashMap();

        configPartieLabels.put("nbJoueurs", new JLabel("Nombre de joueurs (2-6): ", JLabel.RIGHT));
        configPartieInputs.put("nbJoueurs", new JTextField(3));

        configPartieLabels.put("nbJoueursMachine", new JLabel("Nombre de joueurs artificiels: ", JLabel.RIGHT));
        configPartieInputs.put("nbJoueursMachine", new JTextField(3));

        configPartieLabels.put("typeDes", new JLabel("Type de des: ", JLabel.RIGHT));
        configPartieLabels.put("typeAlgo", new JLabel("Algorithme du jeu: ", JLabel.RIGHT));

        typeDes = new JComboBox();
        typeDes.addItem("6 faces");
        typeDes.addItem("8 faces");
        typeDes.addItem("20 faces");

        typeAlgorithme = new JComboBox();
        typeAlgorithme.addItem("Algorithme 1");
        typeAlgorithme.addItem("Algorithme 2");
        typeAlgorithme.addItem("Algorithme 3");

        btnValiderConfigurations.setActionCommand("VALIDER_CONFIG_PARTIE");
        btnValiderConfigurations.addActionListener(this);

        contenu = getContentPane();
        contenu.setLayout(new GridLayout(5, 2, 10, 10));

        contenu.add((JLabel) configPartieLabels.get("nbJoueurs"));
        contenu.add((JTextField) configPartieInputs.get("nbJoueurs"));

        contenu.add((JLabel) configPartieLabels.get("nbJoueursMachine"));
        contenu.add((JTextField) configPartieInputs.get("nbJoueursMachine"));

        contenu.add((JLabel) configPartieLabels.get("typeDes"));
        contenu.add(typeDes);

        contenu.add((JLabel) configPartieLabels.get("typeAlgo"));
        contenu.add(typeAlgorithme);

        contenu.add(btnValiderConfigurations);

        setVisible(true);
        return listeConfigurations;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("VALIDER_CONFIG_PANNEAU")
                && configPanneauValides()) {

            listeConfigurations = new HashMap();
            listeConfigurations.put("nbCases", getNbCases());
            listeConfigurations.put("nbSerpents", getNbSerpents());
            listeConfigurations.put("nbEchelles", getNbEchelles());
            setVisible(false);

        } else if (e.getActionCommand().equals("VALIDER_CONFIG_PARTIE")
                && configPartieValides()) {

            listeConfigurations = new HashMap();
            listeConfigurations.put("nbJoueurs", getNbJoueurs());
            listeConfigurations.put("nbJoueurMachines", getNbJoueursMachine());
            listeConfigurations.put("typeDes", getTypeDes());
            listeConfigurations.put("typeAlgorithme", getTypeAlgorithme());
            setVisible(false);
        }
    }

    private boolean configPanneauValides() {
        boolean nbCaseValide = false, nbEchelleValide = false, nbSerpentValide = false, nbTotalSerpentsEchelles=false;
        String messageErreur = "";
        try {
            int NbCases=getNbCases();
            int NbSerpents=getNbSerpents();
            int NbEchelles=getNbEchelles();
            nbCaseValide = (NbCases >= 40) && (NbCases <= 200);
            nbSerpentValide = NbSerpents >= 0;
            nbEchelleValide = NbEchelles >= 0;            
            
            nbTotalSerpentsEchelles=(NbSerpents+NbEchelles <= NbCases/10);
            
            
        } catch (Exception e) {
        }

        
        if (!nbCaseValide) {
            messageErreur = "Le nombre de cases est invalide";
        } else if(!nbTotalSerpentsEchelles){
           messageErreur = "Le nombre total de serpents et échelles est plus grand que 10% du nombre de cases";
        }else if (!nbSerpentValide) {
            messageErreur = "Le nombre de serpents est invalide";
        } else if (!nbEchelleValide) {
            messageErreur = "Le nombre d'echelles est invalide";
        }
        
        if (messageErreur.length() > 0) {
            afficherMessage(messageErreur);
        }
        return nbCaseValide && nbEchelleValide && nbSerpentValide && nbTotalSerpentsEchelles;
    }

    private boolean configPartieValides() {
        boolean nbJoueursValide = false, nbMachineValide = false;
        String messageErreur = "";
        try {
            nbJoueursValide = (getNbJoueurs() >= 2) && (getNbJoueurs() <= 6);
            nbMachineValide = (getNbJoueursMachine() >= 0) && (getNbJoueursMachine() <= getNbJoueurs());
        } catch (Exception e) {
        }

        if (!nbJoueursValide) {
            messageErreur = "Le nombre de joueurs est invalide";
        } else if (!nbMachineValide) {
            messageErreur = "Le nombre de joueurs artificiels est invalide";
        }

        if (messageErreur.length() > 0) {
            afficherMessage(messageErreur);
        }
        return nbJoueursValide && nbMachineValide;
    }

    private void afficherMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Choix Invalide", JOptionPane.ERROR_MESSAGE);
    }

    private int getNbJoueurs() {
        JTextField saisie = (JTextField) configPartieInputs.get("nbJoueurs");
        return Integer.parseInt(saisie.getText());
    }

    private int getNbJoueursMachine() {
        JTextField saisie = (JTextField) configPartieInputs.get("nbJoueursMachine");
        return Integer.parseInt(saisie.getText());
    }

    private int getTypeDes() {
        switch (typeDes.getSelectedIndex()) {
            case 0:
                return 6;
            case 1:
                return 8;
            default:
                return 20;
        }
    }

    private int getTypeAlgorithme() {
        return typeAlgorithme.getSelectedIndex() + 1;
    }

    private int getNbCases() {
        JTextField saisie = (JTextField) configPanneauInputs.get("nbCases");
        return Integer.parseInt(saisie.getText());
    }

    private int getNbSerpents() {
        JTextField saisie = (JTextField) configPanneauInputs.get("nbSerpents");
        return Integer.parseInt(saisie.getText());
    }

    private int getNbEchelles() {
        JTextField saisie = (JTextField) configPanneauInputs.get("nbEchelles");
        return Integer.parseInt(saisie.getText());
    }

    private final JButton btnValiderConfigurations;

    private JComboBox typeDes;
    private JComboBox typeAlgorithme;

    private Map configPanneauLabels;
    private Map configPanneauInputs;

    private Map configPartieLabels;
    private Map configPartieInputs;

    private Map listeConfigurations;
    private Container contenu;
}
