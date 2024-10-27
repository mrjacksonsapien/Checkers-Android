package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Initialise le damier.
     */
    public void initialiser() {
        for (int i = 0; i < 20; i++) {
            ajouterPion(i + 1, new Pion(Pion.Couleur.NOIR));
        }
        for (int i = 30; i < 50; i++) {
            ajouterPion(i + 1, new Pion());
        }
    }

    private boolean[] calculerPosition(int position) {
        return new boolean[] {
                (position / 5) % 2 == 0,
                (position - 6) % 10 == 0,
                (position - 5) % 10 == 0
        };
    }

    private int[] calculerDirections(boolean rangeePair) {
        return new int[] {
                -(rangeePair ? 5 : 6),
                -(rangeePair ? 4 : 5),
                rangeePair ? 5 : 4,
                rangeePair ? 6 : 5
        };
    }

    public List<Integer>[] deplacementsPossibleSansLimite(int position) {
        Pion pion = getPion(position);

        if (pion == null) {
            throw new NullPointerException();
        } else if (position < 1 || position > 50) {
            throw new ArrayIndexOutOfBoundsException();
        }

        List<Integer>[] deplacements = new List[4];

        for (int i = 0; i < deplacements.length; i++) {
            deplacements[i] = new ArrayList<>();
        }

        int positionImaginaire = position;
        boolean[] positionsCalcule = calculerPosition(positionImaginaire);
        int[] directionsCalcule = calculerDirections(positionsCalcule[0]);

        for (int direction = 0; direction < 4; direction++) {
            positionImaginaire = position;
            boolean estSurLimite = direction % 2 == 0 ? positionsCalcule[1] : positionsCalcule[2];

            while (positionImaginaire > 0 && positionImaginaire <= 50 && !estSurLimite) {
                if (positionImaginaire != position) {
                    deplacements[direction].add(positionImaginaire);
                }
                positionImaginaire += directionsCalcule[direction];
                positionsCalcule = calculerPosition(positionImaginaire);
                directionsCalcule = calculerDirections(positionsCalcule[0]);
                estSurLimite = direction % 2 == 0 ? positionsCalcule[1] : positionsCalcule[2];
            }
        }

        return deplacements;
    }

    /**
     * Constructeur du damier.
     */
    public Damier() {
        cases = new Pion[50];
    }
}
