package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;

import java.util.Arrays;

/**
 * Représente un damier avec 50 cases libres pour des pions.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Damier {
    /**
     * Les cases du jeu de dames (1-50).
     */
    private final Pion[] cases;

    /**
     * Ajoute un pion sur une case spécifique avec une position qui suit la notation de Manoury.
     *
     * @param position 1-50
     * @param pion Pion
     */
    public void ajouterPion(int position, Pion pion) {
        if (position <= 50 && position > 0) {
            cases[position - 1] = pion;
        }
    }

    /**
     * Retourne un pion à une case demandée.
     *
     * @param position 1-50
     * @return Pion ou null si cette case est vide.
     */
    public Pion getPion(int position) {
        Pion pion = null;

        if (position <= 50 && position > 0) {
            pion = cases[position - 1];

            if (pion == null) {
                pion = new Pion(Pion.Couleur.NULL);
            }
        }

        return pion;
    }

    /**
     * Le nombre de pions présentement sur le damier.
     *
     * @return Toutes les cases qui ne sont pas null.
     */
    public int getNbPions() {
        int total = 0;

        for (Pion pion : cases) {
            if (pion != null) {
                total++;
            }
        }

        return total;
    }

    public void initialiser() {
        for (int i = 0; i < 20; i++) {
            ajouterPion(i + 1, new Pion(Pion.Couleur.NOIR));
        }
        for (int i = 30; i < 50; i++) {
            ajouterPion(i + 1, new Pion());
        }
    }

    /**
     * Constructeur du damier.
     */
    public Damier() {
        cases = new Pion[50];

        Arrays.fill(cases, null);
    }
}
