package com.uqam.inf5153.tp3.gestionnaires;

import com.uqam.inf5153.tp3.gestionnaires.PanneauBuilder;
import com.uqam.inf5153.tp3.utilitaires.Element;
import com.uqam.inf5153.tp3.utilitaires.TypeElement;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class GestionPanneau implements GestionPanneauImp {

    private int nbCases;
    private int nbSerpents;
    private int nbEchelles;
    private ArrayList<Element> listeElement;
    PanneauBuilder builder;
    
    public GestionPanneau() {
        builder = new PanneauBuilder();
        listeElement = new ArrayList<>();
    }

    @Override
    public ArrayList<Element> construireElements() {
        int echellesCrees = this.nbEchelles;
        int serpentsCres = this.nbSerpents;
        Boolean creationEchelle = true;
        Boolean creationTermine = false;
        listeElement.clear();

        while (!creationTermine) {

            if (echellesCrees != 0 && creationEchelle == true) {
                listeElement.add(reglesEchelles());
                echellesCrees--;
                creationEchelle = false;
            } else if (serpentsCres != 0) {
                listeElement.add(reglesSerpents());
                serpentsCres--;
                creationEchelle = true;
            } else if (echellesCrees != 0) {
               creationEchelle = true; 
            } else {
                creationTermine = true;
            }

        }
        return listeElement;
    }

    private Boolean ValidateElement(int positionDebut, int positionFin) {
        int nbElementsProximitesDebut = 0;
        int nbElementsProximitesFin = 0;
        int intervaleInferieurPositionDebut = (positionDebut / 10) * 10;
        int intervaleSuperieurPositionDebut = intervaleInferieurPositionDebut + 10;
        int intervaleInferieurPositionFin = (positionFin / 10) * 10;
        int intervaleSuperieurPositionFin = intervaleInferieurPositionFin + 10;

        for (Element elem : listeElement) {
            if (elem.getDebut() == positionDebut 
                    || elem.getDebut() == positionFin 
                    || elem.getFin() == positionDebut 
                    || elem.getFin() == positionFin) {
                return false;
            }
            if ((elem.getDebut() > intervaleInferieurPositionDebut 
                    && elem.getDebut() <= intervaleSuperieurPositionDebut)) {
                nbElementsProximitesDebut++;
            }
            if ((elem.getFin() > intervaleInferieurPositionDebut 
                    && elem.getFin() <= intervaleSuperieurPositionDebut)) {
                nbElementsProximitesDebut++;
            }
            if ((elem.getFin() > intervaleInferieurPositionFin 
                    && elem.getFin() <= intervaleSuperieurPositionFin)) {
                nbElementsProximitesFin++;
            }
            if ((elem.getDebut() > intervaleInferieurPositionFin 
                    && elem.getDebut() <= intervaleSuperieurPositionFin)) {
                nbElementsProximitesFin++;
            }
        }
        if (intervaleInferieurPositionDebut == intervaleInferieurPositionFin) {
            nbElementsProximitesDebut++;
            nbElementsProximitesFin++;
        }
        return !(nbElementsProximitesDebut >= 2 || nbElementsProximitesFin >= 2);
    }

    /*
    On pourrait tolerer selon le nombre de case, un nombre de serpents et d echelle long et petit
     */
    private Element reglesSerpents() {
        Boolean isValidate = false;
        int positionDepart = 0;
        int positionArrive = 0;
        while (!isValidate) {
            positionDepart = determinerPositionElement();
            positionArrive = determinerPositionElement();
            
            if (positionDepart > positionArrive 
                    && positionDepart < nbCases 
                    && positionDepart != positionArrive) {//peut etre qu'on devrait verifier la position arrive +10
                isValidate = ValidateElement(positionDepart, positionArrive);
            }
        }
        Element serpent = new Element(positionDepart, positionArrive, TypeElement.typeElement.SERPENT);
        return serpent;
    }

    private Element reglesEchelles() {
        Boolean isValidate = false;
        int positionDepart = 0;
        int positionArrive = 0;
        while (!isValidate) {
            positionDepart = determinerPositionElement();
            positionArrive = determinerPositionElement();

            if (positionDepart < positionArrive 
                    && positionArrive < nbCases  
                    && positionDepart != positionArrive) {//peut etre qu'on devrait verifier la position arrive +10
                isValidate = ValidateElement(positionDepart, positionArrive);
            }
        }
        Element echelle = new Element(positionDepart, positionArrive, TypeElement.typeElement.ECHELLE);
        return echelle;
    }

    private int determinerPositionElement() {
        Random rand = new Random();
        return rand.nextInt(nbCases) + 1;
    }

    @Override
    public void chargerConfigurationsPanneau() {
        builder = builder.Charger(); 
        this.nbCases = builder.getNbCases();
        this.nbEchelles = builder.getNbEchelles();
        this.nbSerpents = builder.getNbSerpents();    
    }

    @Override
    public void sauvegarderConfigurationsPanneau(int nbCases, int nbSerpents, int nbEchelles) {
        this.nbCases = nbCases;
        this.nbEchelles = nbEchelles;
        this.nbSerpents = nbSerpents;        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements Configuration
            Document doc = docBuilder.newDocument();
            org.w3c.dom.Element rootElement = doc.createElement("Configuration");
            doc.appendChild(rootElement);

            // elements nombreCase
            org.w3c.dom.Element nombreCase = doc.createElement("NombreCases");
            nombreCase.appendChild(doc.createTextNode(String.valueOf(nbCases)));
            rootElement.appendChild(nombreCase);

            // elements nombreEchelles
            org.w3c.dom.Element nombreEchelles = doc.createElement("NombreEchelles");
            nombreEchelles.appendChild(doc.createTextNode(String.valueOf(nbEchelles)));
            rootElement.appendChild(nombreEchelles);

            // elements nombreSerpents
            org.w3c.dom.Element nombreSerpents = doc.createElement("NombreSerpents");
            nombreSerpents.appendChild(doc.createTextNode(String.valueOf(nbSerpents)));
            rootElement.appendChild(nombreSerpents);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("configuration.xml"));

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException | TransformerException pce) {
        }
    }
       
    @Override
    public Boolean isConfigExiste() {
        return builder.IsConfigExist();
    }

        @Override
    public int getNbCases() {
        return nbCases;
    }

    @Override
    public int getNbSerpents() {
        return nbSerpents;
    }

    @Override
    public int getNbEchelles() {
        return nbEchelles;
    }
}
