package cstjean.mobile.tpdame;

import cstjean.mobile.tpdame.pions.Pion;

public class Mort {
    private Pion pion;
    private int dernierePosition;

    public Pion getPion() {
        return pion;
    }

    public int getDernierePosition() {
        return dernierePosition;
    }

    public Mort(Pion pion, int dernierePosition) {
        this.pion = pion;
        this.dernierePosition = dernierePosition;
    }
}
