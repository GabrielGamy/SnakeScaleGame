package com.uqam.inf5153.tp3.gestionnaires;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class PanneauBuilder {

    private int nbCases;
    private int nbSerpents;
    private int nbEchelles;

    public PanneauBuilder() {
    }

    public Boolean IsConfigExist() {
        File fXmlFile = new File("configuration.xml");
        return fXmlFile.exists();
    }

    public PanneauBuilder Charger() {

        try {
            //définir un path
            File fXmlFile = new File("configuration.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            Element rootElement = doc.getDocumentElement();
            NodeList nbCa = rootElement.getElementsByTagName("NombreCases");
            String nbCasestemp = nbCa.item(0).getFirstChild().getNodeValue();
            nbCases = Integer.parseInt(nbCasestemp);

            NodeList nbEch = rootElement.getElementsByTagName("NombreEchelles");
            String nbEchellestemp = nbEch.item(0).getFirstChild().getNodeValue();
            nbEchelles = Integer.parseInt(nbEchellestemp);

            NodeList nbSerp = rootElement.getElementsByTagName("NombreSerpents");
            String nbSerpentstemp = nbSerp.item(0).getFirstChild().getNodeValue();
            nbSerpents = Integer.parseInt(nbSerpentstemp);

        } catch (Exception e) {
        }
        return this;
    }

    public int getNbCases() {
        return nbCases;
    }

    public int getNbSerpents() {
        return nbSerpents;
    }

    public int getNbEchelles() {
        return nbEchelles;
    }

}
