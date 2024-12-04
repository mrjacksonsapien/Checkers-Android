package cstjean.mobile.tpdame;

/**
 * Permet de garder la référence des cibles d'un pion (Passer l'objet d'une méthode à l'autre).
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Cibles {
    /**
     * Contient les cibles potentiels d'un pion (Taille 4 pour chaque direction).
     */
    private final int[] directions;

    public int[] getDirections() {
        return directions;
    }

    /**
     * Constructeur.
     */
    public Cibles() {
        directions = new int[4];
    }
}
