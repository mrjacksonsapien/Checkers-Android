package cstjean.mobile.tpdame;

import cstjean.mobile.tpdame.pions.Dame;
import cstjean.mobile.tpdame.pions.Pion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Historique des mouvements jeu.
     */
    private final Stack<String> historique;

    /**
     * Historique des pions morts.
     */
    private final Stack<Mort> morts;
    /**
     * Garde dans l'historique si le dernier mouvement a engendré une promotion de damme.
     */
    private final Stack<Boolean> isPromotionDame;

    public Pion[] getCasesClone() {
        return cases.clone();
    }

    /**
     * Méthode pour effectuer un retour en arrière d'un état de jeu.
     */
    public void retournerEnArriere() {
        if (!historique.isEmpty()) {
            Pattern pattern = Pattern.compile("^\\(?(\\d{1,2})([x-])(\\d{1,2})\\)?$");
            Matcher matcher = pattern.matcher(historique.pop());

            if (matcher.find()) {
                int origine = Integer.parseInt(matcher.group(1));
                boolean estUnePrise = Objects.equals(matcher.group(2), "x");
                int destination = Integer.parseInt(matcher.group(3));

                if (estUnePrise) {
                    Mort mort = morts.pop();
                    cases[mort.getDernierePosition() - 1] = mort.getPion();
                }

                cases[origine - 1] = cases[destination - 1];

                if (isPromotionDame.pop()) {
                    cases[origine - 1] = new Pion(cases[origine - 1].getCouleur());
                }

                cases[destination - 1] = null;
            }
        }
    }

    private int getPositionCible(int origine, int destination, Cibles cibles) {
        List<Integer>[] directions = deplacementsPossibleSansLimite(origine);
        int positionCible = -1;

        for (int i = 0; i < directions.length; i++) {
            List<Integer> direction = directions[i];

            if (direction.contains(destination)) {
                positionCible = cibles.getDirections()[i];
                break;
            }
        }

        return positionCible;
    }

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
     * Vérifie si il y a un pion à la position donné.
     *
     * @param position Position entre 1 et 50.
     */
    public void verifiePositionPion(int position) {
        if (position < 1 || position > 50) {
            throw new ArrayIndexOutOfBoundsException("Cette position n'est pas valide.");
        }
        if (cases[position - 1] == null) {
            throw new NullPointerException("Il n'y a aucun pion à cette case.");
        }
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
        for (int i = 20; i < 30; i++) {
            cases[i] = null;
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

    private List<Integer>[] deplacementsPossibleSansLimite(int position) {
        verifiePositionPion(position);

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

    private List<Integer> deplacementsSansPrise(int position) {
        verifiePositionPion(position);

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
            for (int i = 0; i <= 3; i++) {
                if (!deplacementsPossibles[i].isEmpty()) {
                    Integer premierePosition = deplacementsPossibles[i].get(0);

                    if (getPion(premierePosition) != null) {
                        continue;
                    }
                }

                for (Integer deplacement : deplacementsPossibles[i]) {
                    if (getPion(deplacement) == null && getPion(position).getCouleur() == Pion.Couleur.BLANC && i < 2) {
                        deplacements.add(deplacement);
                        break;
                    } else if (getPion(deplacement) == null && getPion(position).getCouleur() == Pion.Couleur.NOIR &&
                            i > 1) {
                        deplacements.add(deplacement);
                        break;
                    }
                }
            }
        }
        return deplacements;
    }

    /**
     * Méthode retournant la liste des déplacements valides avec prise.
     *
     * @param position La position de la pièce.
     * @param cibles Les cibles du pion.
     * @return Liste des déplacements valides.
     */
    public List<Integer> deplacementAvecPrise(int position, Cibles cibles) {
        verifiePositionPion(position);

        Pion pion = getPion(position);
        List<Integer> deplacementsAvecPrises = new ArrayList<>();

        List<Integer>[] deplacementsPossibles = deplacementsPossibleSansLimite(position);
        verifierPrises(deplacementsPossibles, pion, deplacementsAvecPrises, cibles);

        return deplacementsAvecPrises;
    }

    /**
     * Retourne les positions possibles où le pion à la position donné peut se déplacer.
     *
     * @param position Position d'une qui contient un pion.
     * @return Les positions où le pion peut se déplacer. Si il est possible de faire une prise le tableau
     *      contiendra une seule position.
     */
    public List<Integer> deplacements(int position) {
        verifiePositionPion(position);

        List<Integer> mouvementsPossibles = deplacementAvecPrise(position, new Cibles());

        if (mouvementsPossibles.isEmpty()) {
            mouvementsPossibles = deplacementsSansPrise(position);
        }

        return mouvementsPossibles;
    }

    /**
     * Déplace un pion à une position donnée à sa destination. La destination doit faire partie du return
     * de la méthode deplacements avec positionPion donnée en argument.
     *
     * @param positionPion Une position qui contient un pion.
     * @param destination Une destination qui fait parti des déplacements possibles du pion.
     * @return Si le deplacement contient une prise ou non.
     */
    public boolean deplacerPion(int positionPion, int destination) {
        if (!deplacements(positionPion).contains(destination)) {
            throw new IllegalArgumentException("La destination de fait pas parti des déplacements possible du pion.");
        }

        Cibles cibles = new Cibles();
        boolean estUnePrise = !deplacementAvecPrise(positionPion, cibles).isEmpty();

        if (estUnePrise) {
            int cible = getPositionCible(positionPion, destination, cibles);
            morts.push(new Mort(cases[cible - 1], cible));
            cases[cible - 1] = null;
        }

        cases[destination - 1] = cases[positionPion - 1];
        cases[positionPion - 1] = null;

        promotionDame(destination);

        boolean inclureParanthese = getPion(destination).getCouleur() == Pion.Couleur.NOIR;
        char typeMouvement = estUnePrise ? 'x' : '-';
        StringBuilder stringBuilder = new StringBuilder();

        if (inclureParanthese) {
            stringBuilder.append("(");
        }

        stringBuilder.append(positionPion).append(typeMouvement).append(destination);

        if (inclureParanthese) {
            stringBuilder.append(")");
        }

        historique.push(stringBuilder.toString());

        return estUnePrise;
    }

    private void promotionDame(int position) {
        Pion pion = getPion(position);

        if ((pion.getCouleur() == Pion.Couleur.BLANC && position <= 5) ||
                (pion.getCouleur() == Pion.Couleur.NOIR && position >= 46)) {
            cases[position - 1] = new Dame(pion.getCouleur());
            isPromotionDame.push(true);
        } else {
            isPromotionDame.push(false);
        }
    }

    private void verifierPrises(List<Integer>[] deplacementsPossibles, Pion pion,
                                         List<Integer> deplacementsAvecPrises, Cibles cibles) {

        for (int i = 0; i < deplacementsPossibles.length; i++) {
            List<Integer> deplacement = deplacementsPossibles[i];

            if (pion instanceof Dame) {
                for (int j = 0; j < deplacement.size() - 1; j++) {
                    int cible = deplacement.get(j);
                    int caseSuivante = deplacement.get(j + 1);

                    if (getPion(caseSuivante) != null) {
                        if (getPion(cible) != null) {
                            break;
                        } else {
                            continue;
                        }
                    }

                    if (getPion(cible) != null) {
                        if (getPion(cible).getCouleur() != pion.getCouleur()) {
                            cibles.getDirections()[i] = cible;

                            for (int k = j + 1; k < deplacement.size(); k++) {
                                if (getPion(deplacement.get(k)) == null) {
                                    deplacementsAvecPrises.add(deplacement.get(k));
                                } else {
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            } else {
                if (deplacement.size() < 2) {
                    continue;
                }

                int cible = deplacement.get(0);
                int caseSuivante = deplacement.get(1);

                if (getPion(caseSuivante) != null) {
                    continue;
                }

                if (getPion(cible) != null) {
                    if (getPion(cible).getCouleur() != pion.getCouleur()) {
                        deplacementsAvecPrises.add(caseSuivante);
                        cibles.getDirections()[i] = cible;
                    }
                }
            }
        }
    }

    /**
     * Constructeur du damier.
     */
    public Damier() {
        cases = new Pion[50];
        historique = new Stack<>();
        morts = new Stack<>();
        isPromotionDame = new Stack<>();
    }
}
