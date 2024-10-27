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
        boolean lignePair = (position / 5) % 2 == 0;
        boolean colleCoteGauche = (position - 6) % 10 == 0;
        boolean colleCoteDroit = position % 5 == 0 && position % 10 != 0;

        return new boolean[] {lignePair, colleCoteGauche, colleCoteDroit};
    }

    private int[] calculerDirections(boolean rangeePair) {
        int nordOuest = -(rangeePair ? 5 : 6);
        int nordEst = -(rangeePair ? 4 : 5);
        int sudOuest = rangeePair ? 5 : 4;
        int sudEst = rangeePair ? 6 : 5;

        return new int[] {nordOuest, nordEst, sudOuest, sudEst};
    }

    /*
    Problèmes à régler:
        - Les dizaines (10, 20, 30...) sont sur des lignes impaires, mais la vérification de la parité de la ligne de
        la position retourne paire pour les dizaines.
     */
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

        for (int direction = 0; direction < 4; direction++) {
            // Reset current position to original and recalculate
            int positionPossible = position;
            boolean[] positionsCalcule = calculerPosition(positionPossible);
            int[] directionsCalcule = calculerDirections(positionsCalcule[0]);
            boolean nePeutPasBouger = direction % 2 == 0 ? positionsCalcule[1] : positionsCalcule[2]; // Calcule si il est possible de bouger dans la direction actuelle basé sur la position actuelle.

            // Check if there's a possible next move else end loop
            while (!nePeutPasBouger) {
                // Move to the next position
                positionPossible += directionsCalcule[direction];

                // Recalculate with new position
                positionsCalcule = calculerPosition(positionPossible);
                directionsCalcule = calculerDirections(positionsCalcule[0]);
                nePeutPasBouger = direction % 2 == 0 ? positionsCalcule[1] : positionsCalcule[2];

                // Check if new position is in range else end loop
                if (positionPossible > 0 && positionPossible <= 50) {
                    deplacements[direction].add(positionPossible);
                } else {
                    break;
                }
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
