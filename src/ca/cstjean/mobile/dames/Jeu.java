package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;

public class Jeu {
    private Damier damier;
    private boolean tourJoueur1 = true;

    public boolean damierEstValide() {
        return damier.getNbPions() == 40;
    }

    private boolean pionEstAdequat(int position, Pion.Couleur couleur) {
        Pion pion = damier.getPion(position);

        if (pion != null) {
            return pion.getCouleur() == couleur;
        } else {
            return false;
        }
    }

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

    public void commencer() {
        damier.initialiser();
    }

    public Jeu(Damier damier) {
        this.damier = damier;
    }

    public void tourDuJoueur() {
        tourJoueur1 = !tourJoueur1;
    }

    public boolean getTourJoueur() {
        return tourJoueur1;
    }
}
