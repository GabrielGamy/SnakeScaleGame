package com.uqam.inf5153.tp3.gestionnaires;

import com.uqam.inf5153.tp3.parties.Partie;
import java.io.File;
import java.util.Arrays;
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
import org.w3c.dom.Element;



public class GestionPartie implements GestionPartieImpl {

     PartieBuilder partieBuilder;
    public GestionPartie() {
        partieBuilder= new PartieBuilder();
    }

    @Override
    public void sauvegarderPartie(Partie partie) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("partie");
            doc.appendChild(rootElement);

            // elements strategie
            Element strategie = doc.createElement("Strategie");
            strategie.appendChild(doc.createTextNode(partie.getStrategie().getClass().getSimpleName()));
            rootElement.appendChild(strategie);

            // elements des
            Element des = doc.createElement("Des");
            des.appendChild(doc.createTextNode(partie.getDes().getTypeDes().name()));
            rootElement.appendChild(des);

            // elements listeJoueurs
            Element listeJoueurs = doc.createElement("Joueurs");
            rootElement.appendChild(listeJoueurs);

            for (int i = 0; i < partie.getJoueurs().length; i++) {
                Element joueur = doc.createElement("joueur");
                joueur.setAttribute("id", String.valueOf(i));
                listeJoueurs.appendChild(joueur);
                for (int j = 0; j < 4; j++) {
                    String[][] attributsJoueur = Arrays.asList(partie.getJoueurs()).get(i).getAttributs();
                    Element attributJoueur = doc.createElement(attributsJoueur[j][0]);
                    attributJoueur.appendChild(doc.createTextNode(attributsJoueur[j][1]));
                    joueur.appendChild(attributJoueur);
                }
            }

            // elements listeElements
            Element listeElements = doc.createElement("Elements");
            rootElement.appendChild(listeElements);

            for (int i = 0; i < partie.getlisteElements().size(); i++) {
                Element element = doc.createElement("element");
                element.setAttribute("id", String.valueOf(i));
                listeElements.appendChild(element);
                for (int j = 0; j < 3; j++) {
                    String[][] attributsElement = partie.getlisteElements().get(i).getAttributs();
                    Element attributElement = doc.createElement(attributsElement[j][0]);
                    attributElement.appendChild(doc.createTextNode(attributsElement[j][1]));
                    element.appendChild(attributElement);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("partie.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException pce) {
        }
    }

    @Override
    public Partie chargerPartie(Partie partieCourante, int nbCases) {
        
        return partieBuilder.chargerPartie(partieCourante,nbCases);
    }

    @Override
    public boolean IsFichierPartieExiste() {
      return  partieBuilder.IschargerPartieExiste();
    }

}
