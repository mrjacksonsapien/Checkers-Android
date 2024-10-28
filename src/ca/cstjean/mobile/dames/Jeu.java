package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;

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

    /**
     * Méthode pour changer le tour du joueur.
     */
    public void changerTour() {
        tourJoueur1 = !tourJoueur1;
    }

    public boolean getTourJoueur() {
        return tourJoueur1;
    }

    /**
     * Constructeur.
     *
     * @param damier Le damier.
     */
    public Jeu(Damier damier) {
        this.damier = damier;
        tourJoueur1 = true;
    }
}
