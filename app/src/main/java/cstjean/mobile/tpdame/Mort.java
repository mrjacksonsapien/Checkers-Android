package cstjean.mobile.tpdame;

import cstjean.mobile.tpdame.pions.Pion;

/**
 * Contient les informations sur la mort d'un pion.
 */
public class Mort {
    /**
     * Le pion mort.
     */
    private final Pion pion;
    /**
     * La position du pion à sa mort.
     */
    private final int dernierePosition;

    public Pion getPion() {
        return pion;
    }

    public int getDernierePosition() {
        return dernierePosition;
    }

    /**
     * Créer une nouvelle mort.
     *
     * @param pion Le pion mort.
     * @param dernierePosition La position du pion.
     */
    public Mort(Pion pion, int dernierePosition) {
        this.pion = pion;
        this.dernierePosition = dernierePosition;
    }
}
