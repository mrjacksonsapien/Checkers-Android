package ca.cstjean.mobile.dames;

/**
 * Pion de dames.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Pion {
    enum Couleur {
        NOIR,
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

    public char getRepresentation() {
        char caractere;

        switch (couleur) {
            case NOIR:
                caractere = 'P';
                break;
            case BLANC:
                caractere = 'p';
                break;
            default:
                caractere = 'n';
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
