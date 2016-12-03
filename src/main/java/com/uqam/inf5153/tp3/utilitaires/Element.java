package com.uqam.inf5153.tp3.utilitaires;

public class Element {

    private int debut;
    private int fin;
    private TypeElement.typeElement type;

    public Element(int debut, int fin, TypeElement.typeElement type) {
        this.debut = debut;
        this.fin = fin;
        this.type = type;
    }

    public int getDebut() {
        return debut;
    }

    public void setDebut(int debut) {
        this.debut = debut;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public TypeElement.typeElement getType() {
        return type;
    }

    public void setType(TypeElement.typeElement type) {
        this.type = type;
    }

    // getter des attributs en string pour le parser XML
    public String[][] getAttributs() {
        String[][] attributs = {{"debut", String.valueOf(debut)},
        {"fin", String.valueOf(fin)},
        {"type", type.name()}};
        return attributs;
    }
}
