package com.uqam.inf5153.tp3.parties;

final class JoueurMemento {

    private JoueurState state;

    JoueurMemento(JoueurState state) {
        setState(state);
    }

    void setState(JoueurState state) {
        this.state = state.clone();
    }

    JoueurState getState() {
        return state;
    }
}
