package com.uqam.inf5153.tp3.parties;

import com.uqam.inf5153.tp3.utilitaires.TypeDes.typeDes;

public class Des {

    private typeDes typeDes;

    public Des(int leType) {
        switch (leType) {
            case 6:
                this.typeDes = typeDes.SIX;
                break;
            case 8:
                this.typeDes = typeDes.HUIT;
                break;
            default:
                this.typeDes = typeDes.VINGT;
                break;
        }
    }

    public typeDes getTypeDes() {
        return typeDes;
    }
}
