package com.uqam.inf5153.tp3.parties;

import java.util.ArrayList;

class GardienMemento {

    private final ArrayList<JoueurMemento> backupUndo = new ArrayList();
    private final ArrayList<JoueurMemento> backupRedo = new ArrayList();

    private int indexUndo = -1;
    private int indexRedo = -1;

    public void addUndoMemento(JoueurMemento memento) {
        backupUndo.add( ++indexUndo,memento);   
    }

    public void addRedoMemento(JoueurMemento memento) {
        backupRedo.add(++indexRedo, memento);//add(memento);
        eraseUnnecessaryRedo();
    }

    private void eraseUnnecessaryRedo(){
        for(int i =backupRedo.size()-1; i > indexRedo;--i )
            backupRedo.remove(i);
    }
    
    public JoueurMemento getUndoMemento() {
        if (indexUndo != -1) {
            JoueurMemento memento = backupUndo.get(indexUndo);
            indexUndo--;
            indexRedo--;
            return memento;
        }
        return null;
    }

    public JoueurMemento getRedoMemento() {
        if (indexRedo < (backupRedo.size() - 1)) {
            JoueurMemento memento = backupRedo.get(indexRedo + 1);
            indexRedo++;
            indexUndo++;
            return memento;
        }
        return null;
    }
}
