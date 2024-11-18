package ca.cstjean.mobile.dames.pions;

/**
 * Pion de dames.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Pion {

    /**
     * Enumération de couleur d'un pion ou d'une dame.
     */
    public enum Couleur {
        /**
         * Couleur noir du pion ou de la dame.
         */
        NOIR,

        /**
         * Couleur blanc du pion ou de la dame.
         */
        BLANC
    }

    /**
     * Couleur du pion.
     */
    private final Couleur couleur;

    /**
     * Retourne la couleur du pion.
     *
     * @return Couleur du pion.
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Retourne le caractère grapgique du pion.
     *
     * @return Le caractère graphique du pion.
     */
    public char getRepresentation() {
        char caractere;

        if (couleur == Couleur.NOIR) {
            caractere = 'P';
        } else {
            caractere = 'p';
        }

        return caractere;
    }

    /**
     * Constructeur.
     *
     * @param couleur Couleur du pion.
     */
    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Constructeur par défaut. Initialise le pion avec la couleur "Blanc".
     */
    public Pion() {
        couleur = Couleur.BLANC;
    }
}
