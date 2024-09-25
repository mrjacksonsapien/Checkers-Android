package ca.cstjean.mobile.dames.pions;

/**
 * Pion de dames.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Pion {
    public enum Couleur {
        NOIR,
        BLANC,
        NULL
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

    public char getRepresentation() {
        char caractere;

        switch (couleur) {
            case NOIR -> caractere = 'P';
            case BLANC -> caractere = 'p';
            default -> caractere = '-';
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
