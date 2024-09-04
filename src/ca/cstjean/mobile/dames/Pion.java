package ca.cstjean.mobile.dames;

/**
 * Pion de dames.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Pion {
    /**
     * Couleur du pion.
     */
    private final String couleur;

    /**
     * Retourne la couleur du pion.
     *
     * @return Couleur du pion.
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Constructeur.
     *
     * @param couleur Couleur du pion.
     */
    public Pion(String couleur) {
        this.couleur = couleur;
    }

    /**
     * Constructeur par défaut. Initialise le pion avec la couleur "Blanc".
     */
    public Pion() {
        couleur = "Blanc";
    }
}
