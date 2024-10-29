package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;

import java.util.Stack;

/**
 * Représente un jeu de dame incluant les règles.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Jeu {
    /**
     * Le damier.
     */
    private Damier damier;

    /**
     * Le tour du joueur.
     */
    private boolean tourJoueur1;

    /**
     * Historique du jeu.
     */
    private Stack<String> historique;

    /**
     * Méthode qui vérifie si le pion est adéquat.
     *
     * @param position Position du pion.
     * @param couleur Couleur du pion.
     * @return True si le pion est adéquat, false sinon
     */
    private boolean pionEstAdequat(int position, Pion.Couleur couleur) {
        Pion pion = damier.getPion(position);

        if (pion != null) {
            return pion.getCouleur() == couleur;
        } else {
            return false;
        }
    }

    /**
     * Méthode qui vérifie si le damier est adéquat.
     *
     * @return Trye si le damier est adéquat, false sinon.
     */
    public boolean damierEstAdequat() {
        for (int i = 0; i < 20; i++) {
            if (!pionEstAdequat(i + 1, Pion.Couleur.NOIR)) {
                return false;
            }
        }

        for (int i = 20; i < 30; i++) {
            if (damier.getPion(i + 1) != null) {
                return false;
            }
        }

        for (int i = 30; i < 50; i++) {
            if (!pionEstAdequat(i + 1, Pion.Couleur.BLANC)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Méthode pour commencer l'initialisation du damier.
     */
    public void commencer() {
        damier.initialiser();
    }

    public void deplacerPion(int origine, int destination) {
        Pion.Couleur couleurQuiJoue = tourJoueur1 ? Pion.Couleur.BLANC : Pion.Couleur.NOIR;
        Pion pion = damier.getPion(origine);

        if (pion == null) {
            throw new NullPointerException("La position d'origine ne contient pas de pion.");
        }
        if (pion.getCouleur() != couleurQuiJoue) {
            throw new IllegalArgumentException("Le pion choisi n'est pas de la bonne couleur.");
        }


    }

    public void retournerEnArriere() {

    }

    public Pion[] getInfoDamier() {
        return damier.getCases();
    }

    /**
     * Méthode pour vérifier si la partie est terminée ou non.
     *
     * @return True si la partie est terminée, false sinon.
     */
    public boolean estTerminee() {
        if (damier.getNbPions() == 1) {
            return true;
        }

        for (int caseDamier = 1; caseDamier <= 50; caseDamier++) {
            Pion pion = damier.getPion(caseDamier);
            if (pion != null && !damier.deplacements(caseDamier).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Constructeur.
     *
     * @param damier Le damier.
     */
    public Jeu(Damier damier) {
        this.damier = damier;
        tourJoueur1 = true;
        historique = new Stack<>();
    }
}
