package cstjean.mobile.tpdame;

import cstjean.mobile.tpdame.pions.Pion;
import java.io.Serializable;
import java.util.Stack;

/**
 * Représente un jeu de dame incluant les règles.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Jeu implements Serializable {
    /**
     * Le damier.
     */
    private final Damier damier;

    /**
     * Si c'est le tour du premier joueur.
     */
    private boolean tourJoueur1;

    /**
     * Si la partie est en cours ou non.
     */
    private boolean enCours;

    /**
     * Si le jeu est en mode debug (Peut commencer une partie avec une configuration non valide).
     */
    private final boolean debug;

    /**
     * Historique du tour des joueurs.
     */
    private final Stack<Boolean> historiqueToursJoueurs;

    public Damier getDamier() {
        return damier;
    }

    /**
     * Méthode qui vérifie si le pion est adéquat.
     *
     * @param position Position du pion.
     * @param couleur Couleur du pion.
     * @return True si le pion est adéquat, false sinon
     */
    private boolean pionEstPasAdequat(int position, Pion.Couleur couleur) {
        Pion pion = damier.getPion(position);

        if (pion != null) {
            return pion.getCouleur() != couleur;
        } else {
            return true;
        }
    }

    /**
     * Méthode qui vérifie si le damier est adéquat.
     *
     * @return True si le damier est adéquat, false sinon.
     */
    public boolean damierEstAdequat() {
        for (int i = 0; i < 20; i++) {
            if (pionEstPasAdequat(i + 1, Pion.Couleur.NOIR)) {
                return false;
            }
        }

        for (int i = 20; i < 30; i++) {
            if (damier.getPion(i + 1) != null) {
                return false;
            }
        }

        for (int i = 30; i < 50; i++) {
            if (pionEstPasAdequat(i + 1, Pion.Couleur.BLANC)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Commencer la partie.
     */
    public void commencer() {
        if (!enCours && (damierEstAdequat() || debug)) {
            enCours = true;
            tourJoueur1 = true;
        }
    }

    public boolean isEnCours() {
        return enCours;
    }

    public boolean getTourJoueur1() {
        return tourJoueur1;
    }

    /**
     * Méthode pour déplacer un pion ou une dame.
     *
     * @param origine Position d'origine du pion ou de la dame.
     * @param destination Destination du pion ou de la dame.
     */
    public void deplacerPion(int origine, int destination) {
        if (enCours) {
            Pion.Couleur couleurQuiJoue = tourJoueur1 ? Pion.Couleur.BLANC : Pion.Couleur.NOIR;
            Pion pion = damier.getPion(origine);

            if (pion == null) {
                throw new NullPointerException("La position d'origine ne contient pas de pion.");
            }
            if (pion.getCouleur() != couleurQuiJoue) {
                throw new IllegalArgumentException("Le pion choisi n'est pas de la bonne couleur.");
            }

            boolean estUnePrise = damier.deplacerPion(origine, destination);

            if (damier.deplacementAvecPrise(destination, new Cibles()).isEmpty() || !estUnePrise) {
                historiqueToursJoueurs.push(tourJoueur1);
                tourJoueur1 = !tourJoueur1;
            }

            if (estTerminee() != null) {
                enCours = false;
            }
        }
    }

    /**
     * Vérifier si la partie est terminée ou non.
     *
     * @return La couleur gagnante si la partie est terminé, ou sinon null.
     */
    public Pion.Couleur estTerminee() {
        enCours = false;

        boolean uneSeulCouleurPresente = true;
        Pion.Couleur premiereCouleurTrouve = null;
        boolean blancsPeuventSeDeplacer = false;
        boolean noirsPeuventSeDeplacer = false;

        for (int caseDamier = 1; caseDamier <= 50; caseDamier++) {
            Pion pion = damier.getPion(caseDamier);

            if (pion != null) {
                if (uneSeulCouleurPresente) {
                    if (premiereCouleurTrouve == null) {
                        premiereCouleurTrouve = pion.getCouleur();
                    } else {
                        if (premiereCouleurTrouve != pion.getCouleur()) {
                            uneSeulCouleurPresente = false;
                        }
                    }
                }
                if (!blancsPeuventSeDeplacer &&
                        pion.getCouleur() == Pion.Couleur.BLANC &&
                        !damier.deplacements(caseDamier).isEmpty()) {
                    blancsPeuventSeDeplacer = true;
                }
                if (!noirsPeuventSeDeplacer &&
                    pion.getCouleur() == Pion.Couleur.NOIR &&
                    !damier.deplacements(caseDamier).isEmpty()) {
                    noirsPeuventSeDeplacer = true;
                }
            }
        }

        if (uneSeulCouleurPresente) {
            return premiereCouleurTrouve;
        } else if (!blancsPeuventSeDeplacer && tourJoueur1) {
            return Pion.Couleur.NOIR;
        } else if (!noirsPeuventSeDeplacer && !tourJoueur1) {
            return Pion.Couleur.BLANC;
        }

        enCours = true;

        return null;
    }

    /**
     * Reculer dans le temps.
     */
    public void retournerEnArriere() {
        enCours = true;
        damier.retournerEnArriere();
        if (!historiqueToursJoueurs.isEmpty()) {
            tourJoueur1 = historiqueToursJoueurs.pop();
        }
    }

    /**
     * Constructeur.
     *
     * @param damier Le damier.
     * @param debug Si on est en debugage.
     */
    public Jeu(Damier damier, boolean debug) {
        this.damier = damier;
        tourJoueur1 = true;
        enCours = false;
        historiqueToursJoueurs = new Stack<>();
        this.debug = debug;
    }
}
