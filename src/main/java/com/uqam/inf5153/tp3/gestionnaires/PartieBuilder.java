
package com.uqam.inf5153.tp3.gestionnaires;

import com.uqam.inf5153.tp3.algorithme.StrategyJeu;
import com.uqam.inf5153.tp3.parties.Des;
import com.uqam.inf5153.tp3.parties.Joueur;
import com.uqam.inf5153.tp3.parties.Partie;
import com.uqam.inf5153.tp3.utilitaires.TypeElement;
import com.uqam.inf5153.tp3.utilitaires.TypePion;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PartieBuilder {

    public PartieBuilder() {
    }
    
     public  Boolean IschargerPartieExiste(){
         File fXmlFile = new File("partie.xml");
         return fXmlFile.exists();
     }
    
    public  Partie chargerPartie(Partie partieCourante, int nbCases){
         Element element = null;

        try {
            //définir la destination du fichier
            File fXmlFile = new File("partie.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            Element rootElement = doc.getDocumentElement();
            NodeList strategieNode = rootElement.getElementsByTagName("Strategie");
            String strategieString = strategieNode.item(0).getFirstChild().getNodeValue();
            int numAlgo;
            switch (strategieString) {
                case "Algorithme1": numAlgo = 1;
                    break;
                case "Algorithme2": numAlgo = 2;
                    break;
                case "Algorithme3": numAlgo = 3;
                    break;
                default: numAlgo = 1;
                    break;
            }
            partieCourante.setStrategie(StrategyJeu.choisirAlgorithme(numAlgo, nbCases));

            NodeList desNode = rootElement.getElementsByTagName("Des");
            String desString = desNode.item(0).getFirstChild().getNodeValue();
            int desToInt;
            switch (desString) {
                case "SIX": desToInt = 6;
                    break;
                case "HUIT": desToInt = 8;
                    break;
                case "VING": desToInt = 20;
                    break;
                default: desToInt = 6;
                    break;
            }
            partieCourante.setDes(new Des(desToInt));

            NodeList joueursNode = rootElement.getElementsByTagName("joueur");
            Joueur[] listeDesJoueurs = new Joueur[joueursNode.getLength()];

            for (int i = 0; i < joueursNode.getLength(); i++) {
                Node nNode = joueursNode.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Pour tous les joueurs, on set les attributs
                    TypePion.Pion pion;
                    switch(eElement.getElementsByTagName("pionDuJoueur").item(0).getTextContent()){
                        case "BLEU_CIEL": pion = TypePion.Pion.BLEU_CIEL;
                            break;
                        case "VERT": pion = TypePion.Pion.VERT;
                            break;
                        case "ROSE": pion = TypePion.Pion.ROSE;
                            break;
                        case "ORANGE": pion = TypePion.Pion.ORANGE;
                            break;
                        case "BLEU": pion = TypePion.Pion.BLEU;
                            break;
                        case "ROUGE": pion = TypePion.Pion.ROUGE;
                            break;
                        default: pion = TypePion.Pion.BLEU_CIEL;
                            break;
                    }
                    listeDesJoueurs[i] = new Joueur(pion);
                    if ("true".equals(eElement.getElementsByTagName("estMachine").item(0).getTextContent())) {
                        listeDesJoueurs[i].setEstMachine(true);
                    }
                    if("true".equals(eElement.getElementsByTagName("estGagnant").item(0).getTextContent())){
                        listeDesJoueurs[i].setEstGagnant(true);
                    }
                    listeDesJoueurs[i].setPositionChargement(Integer.parseInt(eElement.getElementsByTagName("position").item(0).getTextContent()));
                }
            }
            partieCourante.setJoueurs(listeDesJoueurs);

            NodeList elementsNode = rootElement.getElementsByTagName("element");
            ArrayList<com.uqam.inf5153.tp3.utilitaires.Element> listeElements = new ArrayList<>();
            for (int i = 0; i < elementsNode.getLength(); i++) {
                Node nNode = elementsNode.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    int debut = Integer.parseInt(eElement.getElementsByTagName("debut").item(0).getTextContent());
                    int fin = Integer.parseInt(eElement.getElementsByTagName("fin").item(0).getTextContent());
                    TypeElement.typeElement type;
                    switch(eElement.getElementsByTagName("type").item(0).getTextContent()){
                        case "ECHELLE": type = TypeElement.typeElement.ECHELLE;
                            break;
                        case "SERPENT": type = TypeElement.typeElement.SERPENT;
                            break;
                        default: type = TypeElement.typeElement.ECHELLE;
                            break;
                    }
                    com.uqam.inf5153.tp3.utilitaires.Element elementPanneau = new com.uqam.inf5153.tp3.utilitaires.Element(debut, fin, type);
                    listeElements.add(elementPanneau);
                }
            }
            partieCourante.setElements(listeElements);

        } catch (Exception e) {
        }

        return partieCourante;   
    }
}
