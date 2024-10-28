package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Dame;
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
        boolean lignePair = ((position / 5) % 2 == 0 || position % 5 == 0) && position % 10 != 0;
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

    /**
     * Méthode pour trouver toutes les cases en diagonales d'un pion ou d'une dame.
     *
     * @param position Position du pion ou de la dame.
     * @return Liste des cases où le pion peut aller.
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
            int positionPossible = position;
            boolean[] positionsCalcule = calculerPosition(positionPossible);
            int[] directionsCalcule = calculerDirections(positionsCalcule[0]);
            boolean nePeutPasBouger = direction % 2 == 0 ? positionsCalcule[1] : positionsCalcule[2];

            while (!nePeutPasBouger) {
                positionPossible += directionsCalcule[direction];

                positionsCalcule = calculerPosition(positionPossible);
                directionsCalcule = calculerDirections(positionsCalcule[0]);
                nePeutPasBouger = direction % 2 == 0 ? positionsCalcule[1] : positionsCalcule[2];

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
     * Méthode pour vérifier les déplacements valides d'un pion ou d'une dame.
     *
     * @param position Position du pion ou de la dame.
     * @return La liste des déplacements qui sont valides.
     */
    public List<Integer> deplacementValide(int position) {
        Pion pion = getPion(position);
        List<Integer> deplacements = new ArrayList<>();
        List<Integer>[] deplacementsPossibles = deplacementsPossibleSansLimite(position);

        if (pion instanceof Dame) {
            for (List<Integer> deplacement : deplacementsPossibles) {
                for (Integer cible : deplacement) {
                    if (getPion(cible) == null) {
                        deplacements.add(cible);
                    } else {
                        break;
                    }
                }
            }
        } else {
            for (Integer deplacement : deplacementsPossibles[0]) {
                if (getPion(deplacement) == null) {
                    deplacements.add(deplacement);
                }
            }
        }
        return deplacements;
    }

    /**
     * Méthode pour le déplacement avec prise.
     *
     * @param position Position du pion ou de la dame.
     * @return La liste des deplacements avec prise.
     */
    public List<Integer> deplacementAvecPrise(int position) {
        Pion pion = getPion(position);
        List<Integer> deplacementsAvecPrises = new ArrayList<>();

        List<Integer>[] deplacementsPossibles = deplacementsPossibleSansLimite(position);
        verifierPrises(deplacementsPossibles, pion, deplacementsAvecPrises);

        return deplacementsAvecPrises;
    }

    private void verifierPrises(List<Integer>[] deplacementsPossibles, Pion pion,
                                List<Integer> deplacementsAvecPrises) {
        for (List<Integer> direction : deplacementsPossibles) {
            for (int i = 0; i < direction.size() - 1; i++) {
                Integer cible = direction.get(i);
                Integer caseSuivante = direction.get(i + 1);

                if (getPion(cible) != null && getPion(cible).getCouleur() != pion.getCouleur() &&
                        getPion(caseSuivante) == null) {
                    deplacementsAvecPrises.add(caseSuivante);
                    if (pion instanceof Dame) {
                        for (int j = i + 2; j < direction.size(); j++) {
                            Integer caseLibre = direction.get(j);
                            if (getPion(caseLibre) == null) {
                                deplacementsAvecPrises.add(caseLibre);
                            } else {
                                break;
                            }
                        }
                    }

                    verifierPrises(deplacementsPossibleSansLimite(caseSuivante), pion, deplacementsAvecPrises);
                }
            }
        }
    }

    private List<Integer> genererNouveauxDeplacements(Integer position) {
        return deplacementsPossibleSansLimite(position)[0];
    }

    /**
     * Méthode pour vérifier si la partie est terminée ou non.
     *
     * @return True si la partie est terminée, false sinon.
     */
    public boolean estTerminee() {
        if (getNbPions() == 1) {
            return true;
        }

        for (int caseDamier = 1; caseDamier <= 50; caseDamier++) {
            Pion pion = getPion(caseDamier);
            if (pion != null && !deplacementValide(caseDamier).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode gérant la promotion en dame d'un pion.
     *
     * @param pion Le pion.
     * @param position La position du pion.
     */
    public void promotionDame(Pion pion, int position) {
        if ((pion.getCouleur() == Pion.Couleur.BLANC && position <= 5) ||
                (pion.getCouleur() == Pion.Couleur.NOIR && position >= 46)) {
            cases[position - 1] = new Dame(pion.getCouleur());
        }
    }

    /**
     * Constructeur du damier.
     */
    public Damier() {
        cases = new Pion[50];
    }
}
